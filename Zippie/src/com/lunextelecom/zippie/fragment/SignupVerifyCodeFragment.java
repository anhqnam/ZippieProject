/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupVerifyCodeFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;
import com.lunextelecom.zippie.utils.Utils;


// TODO: Auto-generated Javadoc
/**
 * The Class SignupVerifyCodeFragment.
 */
public class SignupVerifyCodeFragment extends Fragment implements OnClickListener {
	
	/** The m title text view. */
	private TextView mTitleTextView;
	
	/** The m text view. */
	private TextView mTextView;
	
	/** The m phone text view. */
	private TextView mPhoneTextView;
	
	/** The m code edit text. */
	private EditText mCodeEditText;
	
	/** The m verify button. */
	private Button mVerifyButton;
	
	/** The m resend code text view. */
	private TextView mResendCodeTextView;
	
	/** The m back layout. */
	private LinearLayout mBackLayout;

	/** The m sign up activity. */
	private SignUpActivity mSignUpActivity;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.signup_verify_code_lay, null);
		mBackLayout = (LinearLayout)view.findViewById(R.id.signup_back_fragment_id);
		mTitleTextView = (TextView)view.findViewById(R.id.signup_header_verify_title_id);
		mTitleTextView.setText(R.string.signup_title_verify_screen_str);
		mTextView      = (TextView)view.findViewById(R.id.signup_show_text_id);
		mPhoneTextView = (TextView)view.findViewById(R.id.signup_phone_verify_id);
		mCodeEditText  = (EditText)view.findViewById(R.id.signup_verify_code_id);
		mVerifyButton  = (Button)view.findViewById(R.id.signup_button_continue_id);
		mResendCodeTextView = (TextView)view.findViewById(R.id.signup_resend_code_text_id);
		String htmlString="<u>" + getString(R.string.signup_resend_code_str) + "</u>";
		mResendCodeTextView.setText(Html.fromHtml(htmlString));
		Utils.setTypefaceRoboto(getActivity(), mTitleTextView, mTextView, mPhoneTextView, mCodeEditText, mVerifyButton, mResendCodeTextView);
		Bundle args = getArguments();
		if(args != null){
			String phone_number = args.getString("phonenumber");
			mPhoneTextView.setText(phone_number);
		}
		
		mBackLayout.setOnClickListener(this);
		mVerifyButton.setOnClickListener(this);
		
		return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@SuppressLint("ResourceAsColor") @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.signup_back_fragment_id:
			if(mSignUpActivity != null)
			{
				mSignUpActivity.backFragment();
			}
			break;
		case R.id.signup_button_continue_id:
			if(mSignUpActivity != null)
			{
				mSignUpActivity.startProfileFragment();
			}
			break;
		}
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
}
