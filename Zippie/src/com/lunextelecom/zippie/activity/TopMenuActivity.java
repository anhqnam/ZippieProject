/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - TopMenuActivity.java
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
import com.lunextelecom.zippie.fragment.TopMenuFragment;
import com.lunextelecom.zippie.utils.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class TopMenuActivity.
 */
public class TopMenuActivity extends Activity{

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_top_menu);
        startTopMenuFragment();
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
        transaction.replace(R.id.top_menu_fragment_contain, iFragment,
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
    public void startTopMenuFragment()
    {
    	startFragment(new TopMenuFragment(), Constants.TAG_TOPMENU_TOPMENUFRAGMENT, false);
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
