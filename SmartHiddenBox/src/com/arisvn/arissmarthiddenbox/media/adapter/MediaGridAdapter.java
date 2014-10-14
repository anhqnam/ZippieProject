package com.arisvn.arissmarthiddenbox.media.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.util.SaveData;

public class MediaGridAdapter extends MediaBaseAdapter {

	public MediaGridAdapter(Activity activity,
			ArrayList<? extends BaseItemEntity> fileItems, int type) {
		super(activity, fileItems, type);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder view;
		if (convertView == null) {
			view = new ViewHolder();
			// imageLoader.memoryCache.clearCache();
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.gridview_row, null);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.imageView1);
			view.image_selected = (ImageView) convertView
					.findViewById(R.id.image_selected);
			LayoutParams layoutParams = new LayoutParams(SaveData.getInstance(
					activity).getDeviceWidth()
					/ SaveData.getInstance(activity).getGridColumn(), SaveData
					.getInstance(activity).getDeviceWidth()
					/ SaveData.getInstance(activity).getGridColumn());
			view.imgViewFlag.setLayoutParams(layoutParams);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		return super.getView(position, convertView, parent);
	}
}
