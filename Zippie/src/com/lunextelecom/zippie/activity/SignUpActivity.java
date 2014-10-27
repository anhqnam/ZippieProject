/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.SignupProfileFragment;
import com.lunextelecom.zippie.fragment.SignupIntroduceFragment;
import com.lunextelecom.zippie.fragment.SignupAutoVerifyCodeFragment;
import com.lunextelecom.zippie.fragment.SignupInputPhoneNumberFragment;
import com.lunextelecom.zippie.fragment.SignupVerifyCodeFragment;
import com.lunextelecom.zippie.fragment.SignupSplashScreenFragment;
import com.lunextelecom.zippie.fragment.SignupWelcomeScreenFragment;
import com.lunextelecom.zippie.utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class SignUpActivity.
 */
public class SignUpActivity extends Activity {

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        startSplashFragment();
        
    }
    
    /**
     * Start fragment.
     *
     * @param iFragment the i fragment
     * @param tag the tag
     * @param isAddBackStack the is add back stack
     */
    private void startFragment(Fragment iFragment,String tag, boolean isAddBackStack)
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, iFragment,
        		tag);
        if(isAddBackStack)
        {
        	transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
    /**
     * Start splash fragment.
     */
    public void startSplashFragment()
    {
    	startFragment(new SignupSplashScreenFragment(), Constants.TAG_SIGNUP_SPLASHSCREEN, false);
    }
    
    /**
     * Start welcome fragment.
     */
    public void startWelcomeFragment()
    {
    	startFragment(new SignupWelcomeScreenFragment(), Constants.TAG_SIGNUP_WELCOMESCREEN, false);
    }
    
    /**
     * Start input phone number fragment.
     */
    public void startInputPhoneNumberFragment()
    {
    	startFragment(new SignupInputPhoneNumberFragment(), Constants.TAG_SIGNUP_INPUTPHONENUMBERSCREEN, true);
    }
    
    /**
     * Start verify code fragment.
     *
     * @param phoneNumber the phone number
     */
    public void startVerifyCodeFragment(String phoneNumber)
    {
    	SignupVerifyCodeFragment fragment = new SignupVerifyCodeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("phonenumber", phoneNumber);
		fragment.setArguments(bundle);
		startFragment(fragment, Constants.TAG_SIGNUP_VERIFYCODESCREEN, true);
    }
    
    /**
     * Start auto profile fragment.
     */
    public void startProfileFragment()
    {
    	startFragment(new SignupProfileFragment(), Constants.TAG_SIGNUP_AUTOVERIFYCODESCREEN, true);
    }
    
    /**
     * Start profile fragment.
     */
    public void startAutoVerifyCodeFragment()
    {
    	startFragment(new SignupAutoVerifyCodeFragment(), Constants.TAG_SIGNUP_PROFILESCREEN, true);
    }
    
    /**
     * Start introduce fragment.
     */
    public void startIntroduceFragment()
    {
    	startFragment(new SignupIntroduceFragment(), Constants.TAG_SIGNUP_INTRODUCESCREEN, true);
    }
    
    /**
     * Back fragment.
     */
    public void backFragment()
    {
    	FragmentManager manager = getFragmentManager();
    	manager.popBackStack();
    }
    
    /**
     * Start new activity.
     *
     * @param intent the intent
     */
    public void startNewActivity(Intent intent)
    {
    	startActivity(intent);
    }
}
