
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.ContactListAdapter;
import com.lunextelecom.zippie.adapter.ContactListAdapter.Item;
import com.lunextelecom.zippie.adapter.ContactListAdapter.Row;
import com.lunextelecom.zippie.adapter.ContactListAdapter.Section;
import com.lunextelecom.zippie.bean.ContactObject;
import com.lunextelecom.zippie.database.ContactDatabaseHelper;
import com.lunextelecom.zippie.sdk.ContactAPIHelper;
import com.lunextelecom.zippie.utils.Utils;
import com.lunextelecom.zippie.view.ImageLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactListFragment.
 */
public class ContactListFragment extends Fragment {

    /** The m list contact. */
    private List<ContactObject> mListContact ;

    /** The m contact api. */
    private ContactAPIHelper mContactAPI;

    /** The m contact db. */
    private ContactDatabaseHelper mContactDb;

    /** The mfilter. */
    private String mFilter;
    private String mSearch;

    /** The m list view contact. */
    private ListView mListViewContact;

    /** The m image loader. */
    private ImageLoader mImageLoader;

    /** The m adapter. */
    private ContactListAdapter mAdapter;

    /**
     * New instance.
     *
     * @param title the title
     * @return the contact list fragment
     */
    public static ContactListFragment newInstancePager(String position) {
        ContactListFragment frag = new ContactListFragment();
        Bundle args = new Bundle();
        Log.d("Frag", " "+position);
        //args.putString("filter", title);
        //args.putString("search",search);
        frag.setArguments(args);
        return frag;
    }
    public static ContactListFragment newInstanceFragment(String title,String search) {
        ContactListFragment frag = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString("filter", title);
        args.putString("search",search);
        frag.setArguments(args);
        return frag;
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.layout_contact, container, false);
        mFilter = getArguments().getString("filter", "Enter Name");
        mSearch = getArguments().getString("search", "Enter Name");
        mContactAPI =  ContactAPIHelper.getInstance(getActivity());
        mContactDb = ContactDatabaseHelper.getInstance(getActivity());
        mListViewContact = (ListView) view.findViewById(R.id.lvContactFragment);
        if(mFilter == Utils.FRAGMENT_CONTACT_ALL){
            mListContact = mContactAPI.GetContactAll(false);
        }else if(mFilter == Utils.FRAGMENT_CONTACT_VIPPE){
            mListContact = mContactAPI.GetContactVippie(false);
        }else if(mFilter == Utils.FRAGMENT_CONTACT_FAVORITES){
            mListContact = mContactDb.GetContactFavorite(false);
        }else if(mFilter == Utils.FRAGMENT_CONTACT_SEARCH){
            mListContact = mContactAPI.getListContactByName(mContactAPI.GetContactAll(false), mSearch);
        }
        final List<Row> rows = getListRow(mListContact);
        mAdapter = new ContactListAdapter(getActivity(), rows, mImageLoader,mSearch);
        mListViewContact.setAdapter(mAdapter);
        mListViewContact.setOnItemClickListener(mAdapter);
        return view;
    }

    /**
     * Gets the list row.
     *
     * @param mList the m list
     * @return the list row
     */
    private List<Row> getListRow(List<ContactObject> mList) {
        // TODO Auto-generated method stub
        List<Row> rows = new ArrayList<Row>();
        String previousLetter = null;
        Pattern specialPattern = Pattern.compile("[^A-Za-z0-9]");
        Pattern numberPattern = Pattern.compile("[0-9]");
        if(mList != null){
            for (ContactObject contact : mList) {
                String firstLetter = contact.getcName().substring(0, 1);
                String tmp = firstLetter;
                // Group numbers together in the scroller
                if (numberPattern.matcher(firstLetter).matches()) {
                    firstLetter = "#";
                }
                // Check if we need to add a header row
                if (!specialPattern.matcher(tmp).matches() && !firstLetter.equalsIgnoreCase(previousLetter)) {
                    rows.add(new Section(firstLetter.toUpperCase()));
                    previousLetter = firstLetter;
                }
                // Add the contact to the list
                rows.add(new Item(contact));
            }
        }
        return rows;
    }
}
