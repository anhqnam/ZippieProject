/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.calllog.adapter - CallLogBaseAdapter.java
 * Date create: 5:32:30 PM - Feb 26, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.calllog.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CallLogBaseAdapter.
 */
public class CallLogBaseAdapter extends HiddenBaseAdapter {

    /**
     * Instantiates a new call log base adapter.
     *
     * @param activity the activity
     * @param fileItems the file items
     * @param type the type
     */
    public CallLogBaseAdapter(Activity activity, ArrayList<? extends BaseItemEntity> fileItems,
            int type) {
        super(activity, fileItems, type);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view = (ViewHolder)convertView.getTag();
        CallLogEntry obj = (CallLogEntry)objs.get(position);

        if (objs.get(position).isCheck()) {
            view.image_selected.setVisibility(View.VISIBLE);
        } else {
            view.image_selected.setVisibility(View.GONE);
        }

        if (isLoadThumbnail) {
            // load avarta
            if (obj.getNumber() != null) {
            	String contactName="";
            	if (obj.getName()!=null&&obj.getName().trim().length()>0) {
					contactName=obj.getName();
				} else {
					contactName=Utils.getContactName(activity, obj.getNumber());
					obj.setName(contactName);
				}
            	
            	if (contactName!=null&&contactName.trim().length()>0) {
                    view.txtViewTitle.setText(contactName);
				} else {
                view.txtViewTitle.setText(obj.getNumber());
				}
                Utils.setTypeface(activity, view.txtViewTitle,"Roboto-Light.ttf");
                Utils.setTypeface(activity, view.txtSize,"Roboto-Light.ttf");
                imageLoader.displayImage(obj, view.imgViewFlag);
            }
            String time = obj.getDate();
            String date ="";
            if (obj.getDate() != null && !obj.getDate().equals("")) {
                date = Utils.getDatetime(Long.parseLong(time));
            }
            view.txtSize.setText(date);
        } else {
            view.txtViewTitle.setText(null);
            view.txtSize.setText(null);
            view.imgViewFlag.setImageBitmap(null);
        }
        return convertView;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter#getSelectedItem()
     */
    @Override
    public ArrayList<CallLogEntry> getSelectedItem() {
        // TODO Auto-generated method stub
        ArrayList<CallLogEntry> callLogEntries = new ArrayList<CallLogEntry>();
        if (objs != null && objs.size() > 0) {
            for (int i = 0; i < objs.size(); i++) {
                CallLogEntry callLogEntry = (CallLogEntry)objs.get(i);
                if (callLogEntry.isCheck()) {
                    callLogEntries.add(callLogEntry);
                }
            }
        }
        return callLogEntries;
    }

}
