/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupInputPhoneNumberFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;
import com.lunextelecom.zippie.bean.CountryObject;
import com.lunextelecom.zippie.dialog.CustomCountryDialog;
import com.lunextelecom.zippie.dialog.CustomCountryDialog.CountryDialogListener;
import com.lunextelecom.zippie.utils.CountryHelper;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputPhoneNumberFragment.
 */
public class SignupInputPhoneNumberFragment extends Fragment implements
		CountryDialogListener,OnClickListener {

	/** The m show text view. */
	private TextView mSignup_textshow_tv;

	/** The m country text view. */
	private TextView mSignup_country_name_tv;

	/** The m flag image view. */
	private ImageView mSignup_image_flag_iv;

	/** The m number edit text. */
	private EditText mSignup_phone_number_et;

	/** The m continue button. */
	private Button mSignup_continue_bt;

	/** The m title text view. */
	private TextView mSignup_header_text_tv;

	/** The m list country. */
	private List<CountryObject> mListCountry;

	/** The m sign up activity. */
	private SignUpActivity mSignUpActivity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
/*		mCountryHelper = new CountryHelper(getActivity(), this);
		mListCountry = CountryHelper.getListCountryFromJsonFile(getActivity()
				.getAssets());*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.signup_input_phone_number, null);
		initView(view);
		return view;
	}
	
	private void initView(View view)
	{
		mSignup_header_text_tv = (TextView) view.findViewById(R.id.signup_header_text_id);
		mSignup_header_text_tv.setText(R.string.signup_title_input_number_str);
		mSignup_textshow_tv = (TextView) view.findViewById(R.id.signup_textshow_id);
		mSignup_country_name_tv = (TextView) view.findViewById(R.id.signup_country_name_textview_id);
		mSignup_image_flag_iv = (ImageView) view.findViewById(R.id.signup_image_flag_id);
		mSignup_phone_number_et = (EditText) view.findViewById(R.id.signup_phone_number_edittext_id);
		mSignup_continue_bt = (Button) view.findViewById(R.id.signup_button_continue_id);
		
		Utils.setTypefaceRoboto(getActivity(),mSignup_header_text_tv, mSignup_textshow_tv, mSignup_country_name_tv,
				mSignup_phone_number_et, mSignup_image_flag_iv, mSignup_continue_bt);
		mSignup_country_name_tv.setOnClickListener(this);
		mSignup_continue_bt.setOnClickListener(this);
	}

/*	
	 * (non-Javadoc)
	 * 
	 * @see com.lunextelecom.zippie.utils.CountryHelper.ConnectionGPSSuccess#
	 * connectSuccess(java.lang.String)
	 
	@Override
	public void connectSuccess(String countryCode) {
		// TODO Auto-generated method stub
		CountryObject country = CountryHelper.getCountryObject(countryCode,
				mListCountry);
		getInfoCountry(country);
	}*/

	/**
	 * Gets the list country.
	 * 
	 * @param list
	 *            the list
	 * @return the list country
	 */
	public ArrayList<String> getListCountry(List<CountryObject> list) {
		ArrayList<String> results = new ArrayList<String>();
		if (list != null) {
			for (CountryObject ob : list) {
				String name = ob.getmName() + " (" + ob.getmDialCode() + ")";
				results.add(name);
			}
		}
		return results;
	}

	/**
	 * Gets the info country.
	 * 
	 * @param object
	 *            the object
	 * @return the info country
	 */
	public void getInfoCountry(CountryObject object) {
		mSignup_country_name_tv.setText(object.getmName());
		mSignup_image_flag_iv.setImageDrawable(CountryHelper.getDrawableFlagCountry(
				getActivity().getAssets(), object.getmCode()));
		mSignup_phone_number_et.setText(object.getmDialCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lunextelecom.zippie.dialog.CustomCountryDialog.CountryDialogListener
	 * #callbackCountryDialog(int, java.lang.String)
	 */
	@Override
	public void callbackCountryDialog(String dialCode) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), dialCode, Toast.LENGTH_LONG).show();
		CountryObject country = CountryHelper.getCountryObjectByDialCode(dialCode, mListCountry);
		getInfoCountry(country);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mSignUpActivity = (SignUpActivity) activity;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Zippie", getClass().getName()
					+ " must implement mSignUpActivity.startSplashFragment();");
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		mSignUpActivity = null;
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.signup_country_name_textview_id:
		{
			FragmentManager fm = getFragmentManager();
			ArrayList<String> mlistItemFragment = getListCountry(mListCountry);
			CustomCountryDialog mFragmentDialog = CustomCountryDialog
					.newInstance("Select Country", mlistItemFragment);
			mFragmentDialog.setOnItemCountry(SignupInputPhoneNumberFragment.this);
			mFragmentDialog.show(fm, "fragment_edit_name");
			break;
		}
		case R.id.signup_button_continue_id:
		{	
			String phone_number = mSignup_phone_number_et.getText().toString();
			if(phone_number.trim().length() == 0)
			{
				Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_LONG).show();
			}else{
				if(mSignUpActivity != null)
				{
					mSignUpActivity.startVerifyCodeFragment(phone_number);
				}
			}
			break;
		}
		default:
			break;
		}
	}
}
