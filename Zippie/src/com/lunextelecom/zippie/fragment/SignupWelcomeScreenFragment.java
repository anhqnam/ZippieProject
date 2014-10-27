/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupWelcomeScreenFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class WelcomeScreenFragment.
 */
public class SignupWelcomeScreenFragment extends Fragment implements OnClickListener {

	/** The m sign up activity. */
	private SignUpActivity mSignUpActivity;
	/** The m image view. */
	private ImageView mImageView;

	/** The m button start. */
	private ImageView mButtonStart;

	/** The m slogan text view. */
	private TextView mSloganTextView;

	/** The m start text view. */
	private TextView mStartTextView;

	/** The m privacy text view. */
	private TextView mTextView, mPrivacyTextView;

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
		View view = inflater.inflate(R.layout.sigup_welcome_screen_lay, null);
		initView(view);
		return view;
	}
	
	/**
	 * Inits the view.
	 *
	 * @param view the view
	 */
	private void initView(View view)
	{
		mSloganTextView = (TextView) view.findViewById(R.id.signup_welcome_slogan_id);
		mStartTextView = (TextView) view.findViewById(R.id.signup_welcome_text_start_id);
		mTextView = (TextView) view.findViewById(R.id.signup_welcome_text_id);
		mPrivacyTextView = (TextView) view.findViewById(R.id.signup_welcome_text_privacy_id);
		mButtonStart = (ImageView) view.findViewById(R.id.signup_welcome_button_start_id);
		mImageView = (ImageView) view.findViewById(R.id.signup_welcome_background_start_id);
		
		String htmlString=" &nbsp; <b><u>Privacy Policy</u></b>";
		mPrivacyTextView.setText(Html.fromHtml(htmlString));
		Utils.setTypefaceRoboto(getActivity(), mSloganTextView, mStartTextView,mTextView,
				mPrivacyTextView);

		mButtonStart.setOnClickListener(this);
		mPrivacyTextView.setOnClickListener(this);
		startAnimationView(mImageView);
	}
	
	/**
	 * Start animation view.
	 *
	 * @param v the v
	 */
	private void startAnimationView(View v)
	{
		// Step1 : create the RotateAnimation object
		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// Step 2: Set the Animation properties
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(100000);

		// Step 3: Start animating the image
		v.startAnimation(anim);
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

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.signup_welcome_button_start_id:
			{
				if(mSignUpActivity != null)
				{
					mSignUpActivity.startInputPhoneNumberFragment();
				}
				break;
			}
			case R.id.signup_welcome_text_privacy_id:
			{
				//show dialog about privacy
				break;
			}
			default:
				break;
		}
	}
}
