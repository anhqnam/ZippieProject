/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.sms.adapter - SMSDetailAdapter.java
 * Date create: 2:52:49 PM - Feb 27, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.sms.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.sms.SMSDetailFragment;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSDetailAdapter.
 */
public class SMSDetailAdapter extends FragmentStatePagerAdapter {

    private List<SMSData> list;


    public SMSDetailAdapter(FragmentManager fm, List<SMSData> smsDataList) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.list = smsDataList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        super.destroyItem(container, position, object);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putString(Utils.NUMBER_SMS, list.get(position).getNumber());
        SMSDetailFragment smsDetailListAdapter = new SMSDetailFragment();
        smsDetailListAdapter.setArguments(args);
        return smsDetailListAdapter;
    }

}
