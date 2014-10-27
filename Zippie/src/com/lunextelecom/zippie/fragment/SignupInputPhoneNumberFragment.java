/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhHa
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.CountryObject;
import com.lunextelecom.zippie.dialog.CustomCountryDialog;
import com.lunextelecom.zippie.dialog.CustomCountryDialog.CountryDialogListener;
import com.lunextelecom.zippie.utils.CountryHelper;
import com.lunextelecom.zippie.utils.CountryHelper.ConnectionGPSSuccess;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputPhoneNumberFragment.
 */
public class SignupInputPhoneNumberFragment extends Fragment implements
		ConnectionGPSSuccess, CountryDialogListener {

	/** The m show text view. */
	private TextView mShowTextView;

	/** The m country text view. */
	private TextView mCountryTextView;

	/** The m flag image view. */
	private ImageView mFlagImageView;

	/** The m number edit text. */
	private EditText mNumberEditText;

	/** The m continue button. */
	private Button mContinueButton;

	/** The m title text view. */
	private TextView mTitleTextView;

	/** The m country helper. */
	private CountryHelper mCountryHelper;

	/** The m list country. */
	private List<CountryObject> mListCountry;


	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCountryHelper = new CountryHelper(getActivity(), this);
		mListCountry = CountryHelper.getListCountryFromJsonFile(getActivity()
				.getAssets());
	}

	/**
	 * Get ISO 3166-1 alpha-2 country code for this device (or null if not available).
	 *
	 * @param context Context reference to get the TelephonyManager instance from
	 * @return country code or null
	 */
	public static String getUserCountry(Context context) {
	    try {
	        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	        final String simCountry = tm.getSimCountryIso();
	        if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
	            return simCountry.toLowerCase(Locale.US);
	        }
	        else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
	            String networkCountry = tm.getNetworkCountryIso();
	            if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
	                return networkCountry.toLowerCase(Locale.US);
	            }
	        }
	    }
	    catch (Exception e) { }
	    return null;
	}


	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.signup_input_phone_number, null);

		mTitleTextView = (TextView) view.findViewById(R.id.txtTitle);
		mTitleTextView.setText(getUserCountry(getActivity()));
		//mTitleTextView.setText(R.string.signup_title_input_number_str);
		mShowTextView = (TextView) view.findViewById(R.id.signup_textshow_id);
		mCountryTextView = (TextView) view.findViewById(R.id.signup_country_name_textview_id);
		mCountryTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getFragmentManager();
				ArrayList<String> mlistItemFragment = getListCountry(mListCountry);
				CustomCountryDialog mFragmentDialog = CustomCountryDialog
						.newInstance("Select Country", mlistItemFragment);
				mFragmentDialog.setOnItemCountry(SignupInputPhoneNumberFragment.this);
				mFragmentDialog.show(fm, "fragment_edit_name");
			}
		});
		mFlagImageView = (ImageView) view.findViewById(R.id.signup_image_flag_id);
		mNumberEditText = (EditText) view.findViewById(R.id.signup_phone_number_edittext_id);
		mContinueButton = (Button) view.findViewById(R.id.signup_button_continue_id);
		mContinueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				String phone_number = mNumberEditText.getText().toString();
				if(phone_number.trim().length() == 0)
				{
					Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_LONG).show();
				}else{
					SignupVerifyCodeFragment fragment = new SignupVerifyCodeFragment();
					Bundle bundle = new Bundle();
					bundle.putString("phonenumber", phone_number);
					fragment.setArguments(bundle);
					transaction.replace(R.id.fragment_container,fragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}
			}
		});
		Utils.setTypefaceRoboto(getActivity(),mTitleTextView, mShowTextView, mCountryTextView,
				mNumberEditText, mFlagImageView, mContinueButton);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lunextelecom.zippie.utils.CountryHelper.ConnectionGPSSuccess#
	 * connectSuccess(java.lang.String)
	 */
	@Override
	public void connectSuccess(String countryCode) {
		// TODO Auto-generated method stub
		CountryObject country = CountryHelper.getCountryObject(countryCode,
				mListCountry);
		getInfoCountry(country);
	}

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
		mCountryTextView.setText(object.getmName());
		mFlagImageView.setImageDrawable(CountryHelper.getDrawableFlagCountry(
				getActivity().getAssets(), object.getmCode()));
		mNumberEditText.setText(object.getmDialCode());
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
		CountryObject country = CountryHelper.getCountryObjectByDialCode(dialCode, mListCountry);
		getInfoCountry(country);
	}
}
