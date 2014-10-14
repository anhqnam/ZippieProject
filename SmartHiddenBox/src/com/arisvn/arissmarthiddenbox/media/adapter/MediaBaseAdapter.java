/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.entity - GridviewAdapter.java
 * Date create: 2:57:16 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.media.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class GridviewAdapter.
 */
public class MediaBaseAdapter extends HiddenBaseAdapter {
    public MediaBaseAdapter(Activity activity, ArrayList<? extends BaseItemEntity> fileItems,
            int type) {
        super(activity, fileItems, type);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder view = (ViewHolder) convertView.getTag();
        FileItem obj = (FileItem) objs.get(position);

		if(objs.get(position).isCheck()){
			view.image_selected.setVisibility(View.VISIBLE);
		}else{
			view.image_selected.setVisibility(View.GONE);
		}

		if (isLoadThumbnail) {
            if (obj.getThumbnail() == null) {
                // just load thumbnail when there is no thumbnail
                imageLoader.displayImage(obj, view.imgViewFlag);
            } else {
                // set bitmap for item
                view.imgViewFlag.setImageBitmap(BitmapFactory.decodeByteArray(obj.getThumbnail(),
                        0, obj.getThumbnail().length));
            }
				if (obj.getName() != null) {
				if (view.txtViewTitle != null) {
					view.txtViewTitle.setText(obj.getName());
					Utils.setTypeface(activity, view.txtViewTitle,"Roboto-Light.ttf");
				}

			}
			if (view.txtSize != null) {
					Utils.setTypeface(activity, view.txtSize,"Roboto-Light.ttf");
				view.txtSize.setText(Formatter.formatFileSize(activity,
						obj.getSize()));
			}

		} else {
			if (view.txtViewTitle!=null) {
			view.txtViewTitle.setText(null);
			}
			if (view.txtSize!=null) {
			view.txtSize.setText(null);
			}
			view.imgViewFlag.setImageBitmap(null);
		}
		return convertView;
	}

    @Override
	public ArrayList<FileItem> getSelectedItem() {
        // TODO Auto-generated method stub
		ArrayList<FileItem> fileItems = new ArrayList<FileItem>();
        if (objs != null && objs.size() > 0) {
            for (BaseItemEntity baseItemEntity : objs) {
                if (baseItemEntity.isCheck()) {
                    fileItems.add((FileItem) baseItemEntity);
				}
			}
		}
		return fileItems;
	}
}
