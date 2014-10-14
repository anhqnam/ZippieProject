/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.dialog - UserGuideDialog.java
 * Date create: 2:55:32 PM - Nov 21, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.dialog;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.app.adapter.UserGuideAdapter;
import com.arisvn.arissmarthiddenbox.indicator.CirclePageIndicator;

// TODO: Auto-generated Javadoc
/**
 * The Class UserGuideDialog. This class is used to shown user guide which is
 * going to guide users how to use this application.
 */
public class UserGuideDialog extends Dialog {

    /** The view pager. */
    private ViewPager viewPager;

    /** The list. */
    private List<Integer> list;

    /** The circle page indicator. */
    private CirclePageIndicator circlePageIndicator;

    /**
     * Instantiates a new user guide dialog.
     *
     * @param context the context
     * @param theme the theme
     */
    public UserGuideDialog(Context context, int theme, List<Integer> list) {
        super(context, theme);
        this.list = list;
    }

    /* (non-Javadoc)
     * @see android.app.Dialog#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_guide_viewpager);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.paging_idicator);
        if (list != null && list.size() > 0) {
            UserGuideAdapter adapter = new UserGuideAdapter(getContext(), list);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            circlePageIndicator.setViewPager(viewPager);
            circlePageIndicator.setCurrentItem(viewPager.getCurrentItem());
            circlePageIndicator.invalidate();
        }

    }

}
