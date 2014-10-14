package com.arisvn.arissmarthiddenbox.app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;

public class APPGridAdapter extends APPBaseAdapter {

	public APPGridAdapter(Activity activity,
			ArrayList<? extends BaseItemEntity> fileItems, int type) {
		super(activity, fileItems, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ViewHolder view;
		if (convertView == null) {
			view = new ViewHolder();
			// imageLoader.memoryCache.clearCache();
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.app_grid_cell, null);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.imageView1);
			view.image_selected = (ImageView) convertView
					.findViewById(R.id.image_selected);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		return super.getView(position, convertView, parent);
	}

}
