/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lunextelecom.zippie.fragment.IntroduceItemFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceAdapter.
 */
public class IntroduceAdapter extends FragmentStatePagerAdapter{

    /** The m list data. */
    private List<IntroduceItemFragment> mListData;

    /**
     * Instantiates a new introduce adapter.
     * 
     * @param fm the fm
     * @param list the list
     */
    public IntroduceAdapter(FragmentManager fm, List<IntroduceItemFragment> list) {
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
