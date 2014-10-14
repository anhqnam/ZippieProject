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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.util.Utils;
import com.arisvn.arissmarthiddenbox.util.lazyloading.ImageLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSDetailAdapter.
 */
public class SMSDetailListAdapter extends BaseAdapter {

    /** The list sms datas. */
    private List<SMSData> listSmsDatas;

    /** The m context. */
    private Context mContext;

    /** The m number. */
    private String mNumber;

    /** The image loader. */
    protected ImageLoader imageLoader;

    /**
     * The avatar.
     * 
     * @return the count
     */

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (listSmsDatas != null && listSmsDatas.size() > 0) {
            return listSmsDatas.size();
        }
        return 0;
    }

    /**
     * Instantiates a new sMS detail adapter.
     *
     * @param context the context
     * @param list the list
     * @param number the number
     */
    public SMSDetailListAdapter(Context context, List<SMSData> list, String number) {
        listSmsDatas = list;
        mContext = context;
        mNumber = number;
        imageLoader = new ImageLoader(mContext, Utils.TYPE_CONTACT);
        Log.e("", "number: " + number);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (listSmsDatas != null && listSmsDatas.size() > 0) {
            return listSmsDatas.get(position);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if (listSmsDatas != null && listSmsDatas.size() > 0) {
            return listSmsDatas.size();
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_sms_list_detail, null);
        SMSData sms = listSmsDatas.get(position);

        if (sms != null) {
            if (sms.getDateTime() != null && sms.getDateTime().length() > 0) {
                ((TextView)view.findViewById(R.id.sms_datetime)).setText(Utils.getDatetime(Long
                        .parseLong(sms.getDateTime())));
            }
            if (sms.getBody() != null && sms.getBody().length() > 0) {
                ((TextView)view.findViewById(R.id.sms_body)).setText(sms.getBody());
            }

            Utils.setTypeface(mContext,  ((TextView)view.findViewById(R.id.sms_body)), "Roboto-Light.ttf");
            Utils.setTypeface(mContext,  ((TextView)view.findViewById(R.id.sms_datetime)), "Roboto-Light.ttf");
            if (mNumber != null && mNumber.length() > 0) {
                if (sms.getType().equals(Utils.TYPE_SMS_INBOX)) {
                    ((LinearLayout)view.findViewById(R.id.layout_content))
                    .setBackgroundResource(R.drawable.bubble_friend);
                    ((ImageView)view.findViewById(R.id.avatar_friend)).setVisibility(View.VISIBLE);
                    ((ImageView)view.findViewById(R.id.avatar_user)).setVisibility(View.INVISIBLE);
                } else if (sms.getType().equals(Utils.TYPE_SMS_SENT)) {
                    ((LinearLayout)view.findViewById(R.id.layout_content))
                    .setBackgroundResource(R.drawable.bubble_speak);
                    ((ImageView)view.findViewById(R.id.avatar_friend))
                    .setVisibility(View.INVISIBLE);
                    ((ImageView)view.findViewById(R.id.avatar_user)).setVisibility(View.VISIBLE);
                }else  if (sms.getType().equals(Utils.TYPE_SMS_DRAFT)) {
                    ((LinearLayout)view.findViewById(R.id.layout_content))
                    .setBackgroundResource(R.drawable.bubble_draft);
                    ((ImageView)view.findViewById(R.id.avatar_friend))
                    .setVisibility(View.INVISIBLE);
                    ((ImageView)view.findViewById(R.id.avatar_user)).setVisibility(View.VISIBLE);
                }
            }
        }
        return view;
    }

}
