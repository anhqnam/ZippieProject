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

import com.lunextelecom.zippie.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactNewFragment.
 */
public class ContactNewFragment extends Fragment  implements OnClickListener {
	//private ContactObject mContact;
	/** The m contact info. */
	private LinearLayout mContactInfo;
    
    /**
	 * New contact add.
	 * 
	 * @return the contact new fragment
	 */
    public static ContactNewFragment newContactAdd() {
    	ContactNewFragment frag = new ContactNewFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }
    
    /* (non-Javadoc)
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.contact_fragment_contact_add_edit_layout, container,
                false);
        Button mContactBack =(Button) view.findViewById(R.id.contactadd_back_bt_id);
        Button mContactDone =(Button) view.findViewById(R.id.contactadd_done_bt_id);
        Button mContactFavorite =(Button) view.findViewById(R.id.contactadd_addfavorites_bt_id);
        Button mContactAdd = (Button) view.findViewById(R.id.contactadd_addphone_bt_id);
        mContactInfo = (LinearLayout) view.findViewById(R.id.contactadd_edit_phone_ll_id);
        mContactBack.setOnClickListener(this);
        mContactDone.setOnClickListener(this);
        mContactFavorite.setOnClickListener(this);
        mContactAdd.setOnClickListener(this);
        return view;
    }
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactadd_addphone_bt_id:
			LinearLayout.LayoutParams paramsLl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams paramsIv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams paramsEt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			paramsLl.setMargins(20, 0, 20, 0);
			paramsEt.setMargins(10, 0, 0, 0);
			paramsIv.setMargins(0, 15, 0, 0);
			LinearLayout valueLL = new LinearLayout(getActivity());
			valueLL.setId(1);
			valueLL.setLayoutParams(paramsLl);
			valueLL.setOrientation(LinearLayout.HORIZONTAL);
			//add textView
			ImageView valueIV = new ImageView(getActivity());
			valueIV.setId(1);
			valueIV.setLayoutParams(paramsIv);
			valueIV.setBackgroundResource(R.drawable.contact_addedit_del_phone_icon_dra);
			
			EditText valueET = new EditText(getActivity());
			valueET.setId(1);
			valueET.setLayoutParams(paramsEt);
			valueLL.addView(valueIV);
			valueLL.addView(valueET);
			mContactInfo.addView(valueLL);
			break;

		default:
			break;
		}
	}
}
