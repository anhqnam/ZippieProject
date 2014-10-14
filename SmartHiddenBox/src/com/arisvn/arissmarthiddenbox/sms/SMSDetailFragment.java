/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.sms - SMSDetailFragment.java
 * Date create: 1:13:08 PM - Mar 21, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.sms;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.sms.adapter.SMSDetailListAdapter;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSDetailFragment.
 */
@SuppressLint("ValidFragment")
public class SMSDetailFragment extends Fragment {

    /** The list sms datas. */
    private List<SMSData> listSmsDatas;

    /** The list view. */
    private ListView listView;

    /** The number. */
    private String number;

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.layout_sms_listview, null);
        Bundle args = getArguments();
        if (args != null) {
            number = args.getString(Utils.NUMBER_SMS);
        }
        if (number != null && number.length() > 0) {
            listSmsDatas = App.getDB().getAllSMSByNumber(number);
        }
        listView = (ListView)view.findViewById(R.id.listview);
        SMSDetailListAdapter adapter = new SMSDetailListAdapter(getActivity(), listSmsDatas, number);
        listView.setAdapter(adapter);
        return view;
    }
}
