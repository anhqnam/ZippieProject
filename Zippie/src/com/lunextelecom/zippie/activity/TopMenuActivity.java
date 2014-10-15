/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.IntroduceFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class TopMenuActivity.
 */
public class TopMenuActivity extends ActionBarActivity{

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_top_menu);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IntroduceFragment introduceFragment = new IntroduceFragment();
        fragmentTransaction.add(R.id.top_menu_fragment_contain, introduceFragment);
        fragmentTransaction.commit();
    }

}
