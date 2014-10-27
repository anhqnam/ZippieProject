/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - IntroduceAdapter.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.adapter;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.lunextelecom.zippie.fragment.SignupIntroduceItemFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceAdapter.
 */
public class IntroduceAdapter extends FragmentStatePagerAdapter{

    /** The m list data. */
    private List<SignupIntroduceItemFragment> mListData;

    /**
     * Instantiates a new introduce adapter.
     * 
     * @param fm the fm
     * @param list the list
     */
    public IntroduceAdapter(FragmentManager fm, List<SignupIntroduceItemFragment> list) {
        super(fm);
        // TODO Auto-generated constructor stub
        mListData = list;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return mListData.get(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListData.size();
    }

}
