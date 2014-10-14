/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.sms - SMSDetailActivity.java
 * Date create: 4:36:45 PM - Feb 27, 2014 - 2014
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.sms;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.sms.adapter.SMSDetailAdapter;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSDetailActivity.
 */
public class SMSDetailActivity extends FragmentActivity implements OnPageChangeListener {
    /** The view pager. */
    private ViewPager viewPager;

    /** The position. */
    private int position;

    List<SMSData> smsDatas;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sms_pager);
        Intent intent = getIntent();
        smsDatas = App.getDB().getAllSMSGroupByPhoneNumber();
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        SMSDetailAdapter adapter = new SMSDetailAdapter(getSupportFragmentManager(), smsDatas);
        viewPager.setAdapter(adapter);
		String selectedNumber = intent.getStringExtra(Utils.CALL_LOG_NUMBER);
            for (int i = 0; i < smsDatas.size(); i++) {
			if (smsDatas.get(i).getNumber()
					.equals(selectedNumber)) {
                    position = i;
				break;
			}
		}
                    viewPager.setCurrentItem(position);
                    updateTitle();
                    viewPager.setOnPageChangeListener(this);

    }

    @SuppressWarnings("deprecation")
    private void updateTitle() {
        // TODO Auto-generated method stub
        SMSData smsData = smsDatas.get(position);
        if (smsData != null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.gravity =Gravity.CENTER;
            TextView textView = ((TextView)findViewById(R.id.title_topbar));
            textView.setGravity(Gravity.CENTER);
            String name = Utils.getContactName(this, smsData.getNumber());
            if (name != null && name.trim().length() > 0) {
                textView.setText(name);
            } else {
                textView.setText(smsData.getNumber());
            }
            textView.setSelected(true);

        }

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#
     * onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
     * (int, float, int)
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
     * (int)
     */
    @Override
    public void onPageSelected(int mPosition) {
        // TODO Auto-generated method stub
        position = mPosition;
        updateTitle();

    }
}
