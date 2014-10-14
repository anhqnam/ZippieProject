/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.app.adapter - UserGuideAdapter.java
 * Date create: 2:48:13 PM - Mar 17, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.app.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.arisvn.arissmarthiddenbox.R;

// TODO: Auto-generated Javadoc
/**
 * The Class UserGuideAdapter.
 */
public class UserGuideAdapter extends PagerAdapter {

    /** The list sms datas. */
    private List<Integer> listData;

    /** The m context. */
    private Context mContext;

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (listData != null && listData.size() > 0) {
            return listData.size();
        }
        return 0;
    }

    /**
     * Instantiates a new sMS detail adapter.
     * 
     * @param context the context
     * @param list the list
     */
    public UserGuideAdapter(Context context, List<Integer> list) {
        listData = list;
        mContext = context;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.PagerAdapter#instantiateItem(android.view.View,
     * int)
     */
    @Override
    public Object instantiateItem(View container, int position) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_user_guide, null);
        if (listData != null) {
            if (listData.get(position) > 0) {
                ((ImageView)view.findViewById(R.id.user_guide_image)).setImageResource(listData.get(position));
            }
        }
        ((ViewPager)container).addView(view, 0);
        return view;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View,
     * int, java.lang.Object)
     */
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager)arg0).removeView((View)arg2);
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

}
