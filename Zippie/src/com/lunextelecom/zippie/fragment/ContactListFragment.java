/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.ContactListAdapter;
import com.lunextelecom.zippie.adapter.ContactListAdapter.ContactListAdapterListener;
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
public class ContactListFragment extends Fragment implements
		ContactListAdapterListener, OnClickListener {
	/** The im contact search. */
	private ImageView ivContactVippe, ivContactAll, ivContactFavorites,
			ivContactSearch, ivSearchContact, ivDeleteSearchContact;

	/** The et search box. */
	private EditText etSearchBox;

	/** The btn contact search. */
	private Button btnContactVippe, btnContactAll, btnContactFavorites,
			btnContactSearch;

	/** The m list contact. */
	private List<ContactObject> mListContact;

	/** The m contact api. */
	private ContactAPIHelper mContactAPI;

	/** The m contact db. */
	private ContactDatabaseHelper mContactDb;

	/** The m list view contact. */
	private ListView mListViewContact;

	/** The m image loader. */
	private ImageLoader mImageLoader;

	/** The m adapter. */
	private ContactListAdapter mAdapter;

	private View view;
	private ProgressBar pbHeaderProgress;
	private List<Row> rows;
	/**
	 * New instance.
	 * 
	 * @param position
	 *            the position
	 * @return the contact list fragment
	 */
	public static ContactListFragment newContactListFragment() {
		ContactListFragment frag = new ContactListFragment();
		Bundle args = new Bundle();
		frag.setArguments(args);
		return frag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.contact_fragment_contactlist_layout,container, false);
		view.findViewById(R.id.contact_home_iv_id).setOnClickListener(this);
		btnContactVippe = (Button) view.findViewById(R.id.contact_vippie_bt_id);
		btnContactVippe.setOnClickListener(this);
		btnContactAll = (Button) view.findViewById(R.id.contact_all_bt_id);
		btnContactAll.setOnClickListener(this);
		btnContactFavorites = (Button) view
				.findViewById(R.id.contact_farvorite_bt_id);
		btnContactFavorites.setOnClickListener(this);
		btnContactSearch = (Button) view
				.findViewById(R.id.contact_seacrch_bt_id);
		btnContactSearch.setOnClickListener(this);
		view.findViewById(R.id.contact_add_iv_id).setOnClickListener(this);
		view.findViewById(R.id.contact_searchdetail_back_iv_id)
				.setOnClickListener(this);
		ivSearchContact = (ImageView) view
				.findViewById(R.id.contact_searchdetail_search_iv_id);
		ivSearchContact.setOnClickListener(this);
		ivContactVippe = (ImageView) view
				.findViewById(R.id.contact_vippie_iv_id);
		ivContactAll = (ImageView) view.findViewById(R.id.contact_all_iv_id);
		ivContactFavorites = (ImageView) view
				.findViewById(R.id.contact_farvorite_iv_id);
		ivContactSearch = (ImageView) view
				.findViewById(R.id.contact_seacrch_iv_id);
		etSearchBox = (EditText) view
				.findViewById(R.id.contact_searchdetail_search_et_id);
		ivDeleteSearchContact = (ImageView) view
				.findViewById(R.id.contact_searchdetail_clear_iv_id);
		ivDeleteSearchContact.setOnClickListener(this);
		mContactAPI = ContactAPIHelper.getInstance(getActivity());
		mContactDb = ContactDatabaseHelper.getInstance(getActivity());
		mListViewContact = (ListView) view
				.findViewById(R.id.contact_list_lv_id);
		pbHeaderProgress = (ProgressBar) view.findViewById(R.id.contact_refresh_iv_id);
		pbHeaderProgress.setVisibility(View.VISIBLE);
		getListContactAsync();
		etSearchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String inputSearch = etSearchBox.getText().toString()
                        .toLowerCase(Locale.getDefault());
                if (inputSearch.length() > 0) {
                    ivDeleteSearchContact.setVisibility(View.VISIBLE);
                } else {
                    ivDeleteSearchContact.setVisibility(View.GONE);
                }
                mListContact=mContactAPI.getListContactByName(mContactAPI.GetContactAll(false),inputSearch);         
    			rows = getListRow(mListContact);
    			mAdapter.setRows(rows);
    			mAdapter.setSearch(inputSearch);
    			mAdapter.notifyDataSetChanged();
                }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
		return view;
	}

	/**
	 * Gets the list row.
	 * 
	 * @param mList
	 *            the m list
	 * @return the list row
	 */
	private List<Row> getListRow(List<ContactObject> mList) {
		// TODO Auto-generated method stub
		List<Row> rows = new ArrayList<Row>();
		String previousLetter = null;
		Pattern specialPattern = Pattern.compile("[^A-Za-z0-9]");
		Pattern numberPattern = Pattern.compile("[0-9]");
		if (mList != null) {
			for (ContactObject contact : mList) {
				String firstLetter = contact.getcName().substring(0, 1);
				String tmp = firstLetter;
				// Group numbers together in the scroller
				if (numberPattern.matcher(firstLetter).matches()) {
					firstLetter = "#";
				}
				// Check if we need to add a header row
				if (!specialPattern.matcher(tmp).matches()
						&& !firstLetter.equalsIgnoreCase(previousLetter)) {
					rows.add(new Section(firstLetter.toUpperCase()));
					previousLetter = firstLetter;
				}
				// Add the contact to the list
				rows.add(new Item(contact));
			}
		}
		return rows;
	}

	/**
	 * Show picture.
	 * 
	 * @param status
	 *            the status
	 */
	private void showPicture(String status) {
		if (status == Utils.FRAGMENT_CONTACT_ALL) {
			ivContactVippe.setVisibility(View.GONE);
			ivContactAll.setVisibility(View.VISIBLE);
			ivContactFavorites.setVisibility(View.GONE);
			ivContactSearch.setVisibility(View.GONE);

		} else if (status == Utils.FRAGMENT_CONTACT_VIPPE) {
			ivContactVippe.setVisibility(View.VISIBLE);
			ivContactAll.setVisibility(View.GONE);
			ivContactFavorites.setVisibility(View.GONE);
			ivContactSearch.setVisibility(View.GONE);
		} else if (status == Utils.FRAGMENT_CONTACT_FAVORITES) {
			ivContactVippe.setVisibility(View.GONE);
			ivContactAll.setVisibility(View.GONE);
			ivContactFavorites.setVisibility(View.VISIBLE);
			ivContactSearch.setVisibility(View.GONE);

		} else if (status == Utils.FRAGMENT_CONTACT_SEARCH) {
			ivContactVippe.setVisibility(View.GONE);
			ivContactAll.setVisibility(View.GONE);
			ivContactFavorites.setVisibility(View.GONE);
			ivContactSearch.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * Change back group icon.
	 * 
	 * @param status
	 *            the status
	 */
	private void changeBackGroupIcon(String status) {
		if (status == Utils.FRAGMENT_CONTACT_ALL) {
			RelativeLayout llHeaderContact = (RelativeLayout) view
					.findViewById(R.id.contact_header_rl_id);
			llHeaderContact.setVisibility(View.VISIBLE);
			RelativeLayout llSearchContact = (RelativeLayout) view
					.findViewById(R.id.contact_search_detail_rl_id);
			llSearchContact.setVisibility(View.GONE);
			btnContactAll
					.setBackgroundResource(R.drawable.contact_buttonall_background_dra);
			btnContactVippe
					.setBackgroundResource(R.drawable.contact_buttonvippie_hide_background_dra);
			btnContactFavorites
					.setBackgroundResource(R.drawable.contact_buttonall_hide_background_dra);
			btnContactSearch
					.setBackgroundResource(R.drawable.contact_buttonfavorite_hide_background_dra);

		} else if (status == Utils.FRAGMENT_CONTACT_VIPPE) {
			RelativeLayout llHeaderContact = (RelativeLayout) view
					.findViewById(R.id.contact_header_rl_id);
			llHeaderContact.setVisibility(View.VISIBLE);
			RelativeLayout llSearchContact = (RelativeLayout) view
					.findViewById(R.id.contact_search_detail_rl_id);
			llSearchContact.setVisibility(View.GONE);
			btnContactAll
					.setBackgroundResource(R.drawable.contact_buttonall_hide_background_dra);
			btnContactVippe
					.setBackgroundResource(R.drawable.contact_buttonvippie_background_dra);
			btnContactFavorites
					.setBackgroundResource(R.drawable.contact_buttonall_hide_background_dra);
			btnContactSearch
					.setBackgroundResource(R.drawable.contact_buttonfavorite_hide_background_dra);

		} else if (status == Utils.FRAGMENT_CONTACT_FAVORITES) {
			RelativeLayout llHeaderContact = (RelativeLayout) view
					.findViewById(R.id.contact_header_rl_id);
			llHeaderContact.setVisibility(View.VISIBLE);
			RelativeLayout llSearchContact = (RelativeLayout) view
					.findViewById(R.id.contact_search_detail_rl_id);
			llSearchContact.setVisibility(View.GONE);
			btnContactAll
					.setBackgroundResource(R.drawable.contact_buttonall_hide_background_dra);
			btnContactVippe
					.setBackgroundResource(R.drawable.contact_buttonvippie_hide_background_dra);
			btnContactFavorites
					.setBackgroundResource(R.drawable.contact_buttonall_background_dra);
			btnContactSearch
					.setBackgroundResource(R.drawable.contact_buttonfavorite_hide_background_dra);
		} else if (status == Utils.FRAGMENT_CONTACT_SEARCH) {
			RelativeLayout llHeaderContact = (RelativeLayout) view
					.findViewById(R.id.contact_header_rl_id);
			llHeaderContact.setVisibility(View.GONE);
			RelativeLayout llSearchContact = (RelativeLayout) view
					.findViewById(R.id.contact_search_detail_rl_id);
			llSearchContact.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * Gets the list contact async.
	 * 
	 * @return the list contact async
	 */
	private void getListContactAsync() {
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pbHeaderProgress.setVisibility(View.VISIBLE);
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(1000);
					mContactAPI.GetContactAll(true);
					mContactAPI.GetContactVippie(true);
					mContactDb.GetContactFavoriteList(true);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Void result) {
				pbHeaderProgress.setVisibility(View.GONE);
				// mPager.setCurrentItem(1);
				mListContact = mContactAPI.GetContactAll(false);
				rows = getListRow(mListContact);
				mAdapter = new ContactListAdapter(getActivity(), rows, mImageLoader);
				mAdapter.setOnClickContactListAdapter(ContactListFragment.this);
				mListViewContact.setAdapter(mAdapter);
				mListViewContact.setOnItemClickListener(mAdapter);
				showPicture(Utils.FRAGMENT_CONTACT_ALL);
				changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
			}
		}.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lunextelecom.zippie.adapter.ContactListAdapter.ContactAdapterListener
	 * #callbackAdapter(com.lunextelecom.zippie.bean.ContactObject,
	 * java.lang.Integer)
	 */
	@Override
	public void callbackContactListAdapter(ContactObject result, Integer method) {
		// TODO Auto-generated method stub
		if (mContactListCallBack != null) {
			mContactListCallBack.callbackContactList(result, method);
			closeKeyboard(getActivity(), etSearchBox.getWindowToken());
		}
	}

	/** The m custom layout call back. */
	private ContactListListener mContactListCallBack;

	/**
	 * The listener interface for receiving contactFragment events. The class
	 * that is interested in processing a contactFragment event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addContactFragmentListener<code> method. When
	 * the contactFragment event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see ContactFragmentEvent
	 */
	public interface ContactListListener {

		/**
		 * Callbac fragment.
		 * 
		 * @param result
		 *            the result
		 * @param method
		 *            the method
		 */
		public void callbackContactList(ContactObject result, Integer method);
	}

	/**
	 * Sets the on click button.
	 * 
	 * @param listener
	 *            the new on click button
	 */
	public void setOnClickContactList(ContactListListener listener) {
		this.mContactListCallBack = listener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contact_home_iv_id:
			if (mContactListCallBack != null) {
				ContactObject result = new ContactObject();
				int mBackHome = 5;
				mContactListCallBack.callbackContactList(result, mBackHome);
			}
			break;

		case R.id.contact_vippie_bt_id:
			mListContact= mContactAPI.GetContactVippie(false);
			rows = getListRow(mListContact);
			mAdapter.setRows(rows);
			mAdapter.notifyDataSetChanged();
			showPicture(Utils.FRAGMENT_CONTACT_VIPPE);
			changeBackGroupIcon(Utils.FRAGMENT_CONTACT_VIPPE);
			break;
		case R.id.contact_all_bt_id:
			mListContact= mContactAPI.GetContactAll(false);
			rows = getListRow(mListContact);
			mAdapter.setRows(rows);
			mAdapter.notifyDataSetChanged();
			showPicture(Utils.FRAGMENT_CONTACT_ALL);
			changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
			break;

		case R.id.contact_farvorite_bt_id:
			mListContact= mContactDb.GetContactFavoriteList(false);
			rows = getListRow(mListContact);
			mAdapter.setRows(rows);
			mAdapter.notifyDataSetChanged();
			showPicture(Utils.FRAGMENT_CONTACT_FAVORITES);
			changeBackGroupIcon(Utils.FRAGMENT_CONTACT_FAVORITES);

			break;
		case R.id.contact_seacrch_bt_id:
			mListContact= mContactAPI.getListContactByName(mContactAPI.GetContactAll(false),"");
			rows = getListRow(mListContact);
			mAdapter.setRows(rows);
			mAdapter.notifyDataSetChanged();
			showPicture(Utils.FRAGMENT_CONTACT_SEARCH);
			changeBackGroupIcon(Utils.FRAGMENT_CONTACT_SEARCH);
			break;
		case R.id.contact_add_iv_id:
			if (mContactListCallBack != null) {
				ContactObject result = new ContactObject();
				int mAddContact = 0;
				mContactListCallBack.callbackContactList(result, mAddContact);
			}
		case R.id.contact_searchdetail_back_iv_id:
			changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
			showPicture(Utils.FRAGMENT_CONTACT_ALL);
			etSearchBox.setVisibility(View.GONE);
			ivSearchContact.setVisibility(View.VISIBLE);
			ivDeleteSearchContact.setVisibility(View.GONE);
			mListContact= mContactAPI.GetContactAll(false);
			rows = getListRow(mListContact);
			mAdapter.setSearch("");
			mAdapter.setRows(rows);
			mAdapter.notifyDataSetChanged();
			closeKeyboard(getActivity(), etSearchBox.getWindowToken());
			break;
		case R.id.contact_searchdetail_search_iv_id:
			etSearchBox.setVisibility(View.VISIBLE);
			ivSearchContact.setVisibility(View.GONE);
		case R.id.contact_searchdetail_clear_iv_id:
			etSearchBox.setText("");
		default:
			break;
		}
	}
	public static void closeKeyboard(Context c, IBinder windowToken) {
	    InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
	    mgr.hideSoftInputFromWindow(windowToken, 0);
	}

}
