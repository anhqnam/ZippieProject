/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - CustomCountryDialog.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.dialog;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomDialog.
 */
@SuppressLint({ "NewApi", "CutPasteId" })
public class CustomCountryDialog extends DialogFragment implements
		OnClickListener {

	/** The m list. */
	private ArrayList<String> mList;
	/** The m title dialog. */
	private TextView mTitleDialog;

	/** The m search box. */
	private EditText mSearchBox;

	/** The m search icon. */
	private ImageView mSearchIcon;

	/** The m delete icon. */
	private ImageView mDeleteIcon;

	/** The m country dialog listener. */
	private CountryDialogListener mCountryDialogListener;

	/** The m line view. */
	private View mLineView;

	/** The m search layout. */
	private RelativeLayout mSearchLayout;

	/** The m adapter. */
	private CustomAdapter mAdapter;

	/**
	 * New instance.
	 * 
	 * @param title
	 *            the title
	 * @param listItem
	 *            the list item
	 * @return the custom country dialog
	 */
	public static CustomCountryDialog newInstance(String title,
			ArrayList<String> listItem) {
		CustomCountryDialog frag = new CustomCountryDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putStringArrayList("listItem", listItem);
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
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View dialogView = inflater.inflate(R.layout.signup_custom_country_dialog_lay,
				container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		String title = getArguments().getString("title", "Enter Name");
		mList = getArguments().getStringArrayList("listItem");
		TextView listText = (TextView) dialogView.findViewById(R.id.signup_country_title_dialog_id);
		listText.setText(title);
		ListView listview = (ListView) dialogView
				.findViewById(R.id.signup_list_country_id);
		listview.setTextFilterEnabled(true);
		mAdapter = new CustomAdapter(getActivity(), 0, mList);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mCountryDialogListener != null) {
					String strCountry = mList.get(position);
					int start = strCountry.lastIndexOf("(");
					int end = strCountry.lastIndexOf(")");
					String dialCode = strCountry.substring(start+1, end);
					mCountryDialogListener.callbackCountryDialog(dialCode);
				}
				dismiss();
			}
		});

		mSearchLayout = (RelativeLayout) dialogView
				.findViewById(R.id.signup_search_box_id);
		mSearchIcon = (ImageView) dialogView.findViewById(R.id.signup_icon_search_id);
		mTitleDialog = (TextView) dialogView.findViewById(R.id.signup_country_title_dialog_id);
		mSearchBox = (EditText) dialogView.findViewById(R.id.signup_search_edittext_id);
		mSearchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				mAdapter.filter(s.toString());
			}
		});
		mDeleteIcon = (ImageView) dialogView.findViewById(R.id.signup_icon_delete_id);
		mLineView = (View) dialogView.findViewById(R.id.signup_line_id);
		Utils.setTypefaceRoboto(getActivity(), mTitleDialog, mSearchBox);

		mSearchIcon.setOnClickListener(this);
		mDeleteIcon.setOnClickListener(this);

		return dialogView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.signup_icon_delete_id:
			if (mSearchBox.getText().toString() == null
					|| mSearchBox.getText().toString().trim().length() == 0) {
				mTitleDialog.setVisibility(View.VISIBLE);
				mSearchIcon.setVisibility(View.VISIBLE);
				mLineView.setVisibility(View.VISIBLE);
				mSearchLayout.setVisibility(View.GONE);
			} else {
				mSearchBox.setText("");
			}
			break;
		case R.id.signup_icon_search_id:
			mTitleDialog.setVisibility(View.GONE);
			mSearchIcon.setVisibility(View.GONE);
			mLineView.setVisibility(View.GONE);
			mSearchLayout.setVisibility(View.VISIBLE);
			mSearchBox.setFocusable(true);
			break;
		}
	}

	/**
	 * The Class CustomAdapter.
	 */
	@SuppressLint("InflateParams") private class CustomAdapter extends ArrayAdapter<String> {

		/** The m list. */
		private ArrayList<String> mList;
		
		/** The m list data static. */
		private ArrayList<String> mListDataStatic;

		/**
		 * Instantiates a new custom adapter.
		 * 
		 * @param context
		 *            the context
		 * @param resource
		 *            the resource
		 * @param objects
		 *            the objects
		 */
		public CustomAdapter(Context context, int resource,
				ArrayList<String> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.mList = objects;
			mListDataStatic = new ArrayList<String>();
			mListDataStatic.addAll(objects);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(
						R.layout.signup_custom_item_country_dialog_lay, null);
			}
			TextView itemTextView = (TextView) convertView
					.findViewById(R.id.signup_item_country_name_id);
			String item = mList.get(position);
			itemTextView.setText(item);
			Utils.setTypefaceRoboto(getContext(), itemTextView);
			return convertView;
		}
		
		/**
		 * Filter.
		 *
		 * @param charText the char text
		 */
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			mList.clear();
			if (charText.length() == 0) {
				mList.addAll(mListDataStatic);
			} 
			else 
			{
				for (String name : mListDataStatic) 
				{
					if (name.toLowerCase(Locale.getDefault()).contains(charText)) 
					{
						mList.add(name);
					}
				}
			}
			notifyDataSetChanged();
		}
	}

	/**
	 * The listener interface for receiving countryDialog events. The class that
	 * is interested in processing a countryDialog event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addCountryDialogListener<code> method. When
	 * the countryDialog event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see CountryDialogEvent
	 */
	public interface CountryDialogListener {

		/**
		 * Callback country dialog.
		 * 
		 * @param possition
		 *            the possition
		 * @param inputText
		 *            the input text
		 */
		void callbackCountryDialog(String dialCode);
	}

	/**
	 * Sets the on item country.
	 * 
	 * @param listener
	 *            the new on item country
	 */
	public void setOnItemCountry(CountryDialogListener listener) {
		this.mCountryDialogListener = listener;
	}
}
