/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupSplashScreenFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashScreenFragment.
 */
public class SignupSplashScreenFragment extends Fragment {

	/** The m sign up activity. */
	private SignUpActivity mSignUpActivity;
	
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
		View view = inflater.inflate(R.layout.signup_splash_screen_lay, null);
		int secondsDelayed = 2000;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if(mSignUpActivity != null)
				{
					mSignUpActivity.startWelcomeFragment();
				}
			}
		}, secondsDelayed);
		return view;
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
