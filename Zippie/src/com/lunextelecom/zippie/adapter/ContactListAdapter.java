
package com.lunextelecom.zippie.adapter;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.ContactObject;
import com.lunextelecom.zippie.view.ImageLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactListAdapter.
 */
public class ContactListAdapter extends android.widget.BaseAdapter implements SectionIndexer,OnItemClickListener,OnClickListener{

    /** The Constant CONTACT_ID. */
    public static final String CONTACT_ID = "contactId";

    /** The m context. */
    private Context mContext;

    //private ImageLoader mImageLoader;
    /** The m search term. */
    private String mSearchTerm ; // Stores the current search query term

    /** The m sections. */
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";

    /** The m sms. */
    //private Integer mSms =1;

    /** The m call. */
    //private Integer mCall =2;

    /** The m invite. */
    //private Integer mInvite =3;

    /** The m invite. */
    private Integer mContactDetail =4;
    /** The m contact id. */
    //private Integer mContactId;

    /**
     * The Class Row.
     */
    public static abstract class Row {
    }

    /**
     * The Class Section.
     */
    public static final class Section extends Row {

        /** The text. */
        public final String text;

        /**
         * Instantiates a new section.
         *
         * @param text the text
         */
        public Section(String text) {
            this.text = text;
        }
    }

    /**
     * The Class Item.
     */
    public static final class Item extends Row {

        /** The m contact. */
        public final ContactObject mContact;

        /**
         * Instantiates a new item.
         *
         * @param contact the contact
         */
        public Item(ContactObject contact) {
            this.mContact = contact;
        }
    }

    /** The m rows. */
    private List<Row> mRows;

    /**
     * Instantiates a new contact list adapter.
     *
     * @param context the context
     * @param objects the objects
     * @param imageLoader the image loader
     * @param search the search
     */
    public ContactListAdapter(Context context, List<Row> objects, ImageLoader imageLoader,String search) {
        // TODO Auto-generated constructor stub
        mRows = objects;
        mContext = context;
        //mImageLoader = imageLoader;
        mSearchTerm =search;
    }

    /**
     * Sets the rows.
     *
     * @param rows the new rows
     */
    public void setRows(List<Row> rows) {
        this.mRows = rows;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mRows.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Row getItem(int position) {
        return mRows.get(position);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* (non-Javadoc)
     * @see android.widget.BaseAdapter#getViewTypeCount()
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /* (non-Javadoc)
     * @see android.widget.BaseAdapter#getItemViewType(int)
     */
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (getItemViewType(position) == 0) { // Item
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.contact_rowdetail_contactlist_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvContactName = (TextView)convertView.findViewById(R.id.contact_rowlist_name_tv_id);
                //viewHolder.ivAvatar = (ImageView)convertView.findViewById(R.id.contact_rowlist_avartar_iv_id);
                viewHolder.ivStatus = (ImageView)convertView.findViewById(R.id.contact_rowlist_status_iv_id);
                viewHolder.ivSmsFree = (ImageView)convertView.findViewById(R.id.contact_rowlist_sms_iv_id);
                viewHolder.ivCallFree = (ImageView)convertView.findViewById(R.id.contact_rowlist_call_iv_id);
                viewHolder.ivContactInvite = (Button)convertView.findViewById(R.id.contact_rowlist_invite_bt_id);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            ContactObject contact = ((Item)getItem(position)).mContact;
            final int startIndex = indexOfSearchQuery(contact.getcName());
            if(startIndex >-1){
                final SpannableString highlightedName = new SpannableString(contact.getcName());
                highlightedName.setSpan(new ForegroundColorSpan(Color.CYAN), startIndex,startIndex + mSearchTerm.length(), 0);
                viewHolder.tvContactName.setText(highlightedName);
            }else{
                viewHolder.tvContactName.setText(contact.getcName());
            }
            //mImageLoader.loadImage(contact.getcAvartar(), viewHolder.ivAvatar);
            if (contact.getcStatus() == true) {
                viewHolder.ivStatus.setVisibility(View.VISIBLE);
            }
            if (contact.getVippeFreeId() != "") {
                viewHolder.ivSmsFree.setVisibility(View.VISIBLE);
                viewHolder.ivCallFree.setVisibility(View.VISIBLE);
                viewHolder.ivContactInvite.setVisibility(View.GONE);
                viewHolder.ivSmsFree.setOnClickListener(this);
                viewHolder.ivCallFree.setOnClickListener(this);

            } else if (contact.getVippeFreeId() == "")  {
                viewHolder.ivContactInvite.setVisibility(View.VISIBLE);
                viewHolder.ivSmsFree.setVisibility(View.GONE);
                viewHolder.ivCallFree.setVisibility(View.GONE);
                viewHolder.ivContactInvite.setOnClickListener(this);
            }
        }else { // Section
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.contact_rowsection_contactlist_layout, parent,
                        false);
            }

            Section section = (Section)getItem(position);
            TextView textView = (TextView)convertView.findViewById(R.id.contact_rowlist_section_tv_id);
            textView.setText(section.text);
        }
        return convertView;
    }

    /**
     * The Class ViewHolder.
     */
    private class ViewHolder {

        /** The tv contact name. */
        TextView tvContactName;

        /** The iv call free. */
        ImageView ivStatus, ivSmsFree, ivCallFree;

        /** The iv contact invite. */
        Button ivContactInvite;
    }

    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getPositionForSection(int)
     */
    @Override
    public int getPositionForSection(int section) {
        // TODO Auto-generated method stub
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (getItemViewType(j) == 1) {
                    Section s = (Section)getItem(j);
                    if(s.text.equals(String.valueOf(mSections.charAt(i)))){
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getSectionForPosition(int)
     */
    @Override
    public int getSectionForPosition(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.SectionIndexer#getSections()
     */
    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.contact_rowlist_sms_iv_id:
                if(mContactAdapterCallBack != null){
                    //mContactAdapterCallBack.callbackQuestionDialog(mContactId,mSms);
                }
                break;
            case R.id.contact_rowlist_call_iv_id:
                if(mContactAdapterCallBack != null){
                    //mContactAdapterCallBack.callbackQuestionDialog(mContactId,mCall);
                }
                break;
            case R.id.contact_rowlist_invite_bt_id:
                if(mContactAdapterCallBack != null){
                    //mContactAdapterCallBack.callbackQuestionDialog(mContactId,mInvite);
                }
                break;
            default:
                break;
        }
    }
    /** The m custom layout call back. */
    private ContactAdapterListener mContactAdapterCallBack;

    /**
     * The listener interface for receiving contactAdapter events.
     * The class that is interested in processing a contactAdapter
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addContactAdapterListener<code> method. When
     * the contactAdapter event occurs, that object's appropriate
     * method is invoked.
     *
     * @see ContactAdapterEvent
     */
    public interface ContactAdapterListener {
        // you can define any parameter as per your requirement
        /**
         * Callback question dialog.
         *
         * @param result the result
         * @param method the method
         */
        public void callbackAdapter(ContactObject result,Integer method);
    }

    /**
     * Sets the on click button.
     *
     * @param listener the new on click button
     */
    public void setOnClickButton(ContactAdapterListener listener){
        this.mContactAdapterCallBack = listener;
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        if(getItemViewType(arg2) == 0){
            Item r = (Item)getItem(arg2);
            ContactObject contact =r.mContact;
            if(mContactAdapterCallBack != null){
                mContactAdapterCallBack.callbackAdapter(contact,mContactDetail);
            }
        }
    }

    /**
     * Index of search query.
     *
     * @param displayName the display name
     * @return the int
     */
    private int indexOfSearchQuery(String displayName) {
        if (!TextUtils.isEmpty(mSearchTerm)) {
            return displayName.toLowerCase(Locale.getDefault()).indexOf(
                    mSearchTerm.toLowerCase(Locale.getDefault()));
        }
        return -1;
    }
}
