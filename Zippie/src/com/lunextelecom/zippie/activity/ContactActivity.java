/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.activity;
import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.ContactObject;
import com.lunextelecom.zippie.fragment.ContactEditFragment;
import com.lunextelecom.zippie.fragment.ContactDetailFragment;
import com.lunextelecom.zippie.fragment.ContactNewFragment;
import com.lunextelecom.zippie.fragment.ContactDetailFragment.ContactDetailListener;
import com.lunextelecom.zippie.fragment.ContactListFragment;
import com.lunextelecom.zippie.fragment.ContactListFragment.ContactListListener;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactActivity.
 */
public class ContactActivity extends Activity implements
		ContactListListener, ContactDetailListener {
	
	/** The m contact list back. */
	private int mContactListAdd = 0, mContactListSms = 1, mContactListCall = 1,
			mContactListInvite = 2,mContactListItem = 4, mContactListBack = 5;
	
	/** The m cdetail back. */
	private int mCdetailCallVoice = 1, mCdetailCallVideo = 2, mCdetaiSms = 3,
			mCdetaiCallPre = 4, mCdetaiEgift = 5,mCdetaiMoney =6,mCdetaiPaybill =7, mCdetaiTopup = 8
			,mCdetailFavorite=9 ,mCdetailEdit=10,mCdetailBack=11;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.contact_activity_layout);
		createContactListFragment();
	}

	/**
	 * Creates the contact list fragment.
	 */
	private void createContactListFragment() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		ContactListFragment mContactListFrag = ContactListFragment
				.newContactListFragment();
		mContactListFrag.setOnClickContactList(ContactActivity.this);
		transaction.replace(R.id.contact_main_fl_id, mContactListFrag);
		transaction.commit();
	}

	/**
	 * Creates the contact detail fragment.
	 * 
	 * @param contact
	 *            the contact
	 */
	private void createContactDetailFragment(ContactObject contact) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		ContactDetailFragment mContactDetailFrag = ContactDetailFragment
				.newDetailFragment(contact);
		mContactDetailFrag.setOnClickEditContact(ContactActivity.this);
		transaction.replace(R.id.contact_main_fl_id, mContactDetailFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * Creates the contact add fragment.
	 */
	private void createContactAddFragment() {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		ContactNewFragment mContactAddFrag = ContactNewFragment.newContactAdd();
		transaction.replace(R.id.contact_main_fl_id, mContactAddFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/**
	 * Creates the contact edit fragment.
	 * 
	 * @param contact
	 *            the contact
	 */
	private void createContactEditFragment(ContactObject contact) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		ContactEditFragment mContactEditFrag = ContactEditFragment
				.newContactEdit(contact);
		transaction.replace(R.id.contact_main_fl_id, mContactEditFrag);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	/* (non-Javadoc)
	 * @see com.lunextelecom.zippie.fragment.ContactListFragment.ContactListListener#callbackContactList(com.lunextelecom.zippie.bean.ContactObject, java.lang.Integer)
	 */
	@Override
	public void callbackContactList(ContactObject result, Integer method) {
		// TODO Auto-generated method stub
		if (method == mContactListAdd) {
			createContactAddFragment();
		} else if (method == mContactListSms) {

		} else if (method == mContactListCall) {

		} else if (method == mContactListInvite) {

		} else if (method == mContactListItem) {
			createContactDetailFragment(result);
		} else if (method == mContactListBack) {

		}
	}

	/* (non-Javadoc)
	 * @see com.lunextelecom.zippie.fragment.ContactDetailFragment.ContactDetailListener#callbackEditContact(com.lunextelecom.zippie.bean.ContactObject, java.lang.Integer)
	 */
	@Override
	public void callbackEditContact(ContactObject result, Integer method) {
		// TODO Auto-generated method stub
		if (method == mCdetailCallVoice) {

		} else if (method == mCdetailCallVideo) {

		} else if (method == mCdetaiSms) {

		}else if (method == mCdetaiSms) {

		}else if (method == mCdetaiCallPre) {

		}else if (method == mCdetaiEgift) {

		}
		else if (method == mCdetaiMoney) {

		}else if (method == mCdetaiPaybill) {

		}else if (method == mCdetaiTopup) {

		}else if (method == mCdetailFavorite) {

		}else if (method == mCdetailEdit) {
			createContactEditFragment(result);
		}else if (method == mCdetailBack) {
			createContactListFragment();
		}
	}
}
