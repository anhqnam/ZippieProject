package com.arisvn.arissmarthiddenbox.media.adapter;

import java.util.ArrayList;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.util.Utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MediaListAdapter extends MediaBaseAdapter {
    public MediaListAdapter(Activity activity, ArrayList<? extends BaseItemEntity> fileItems,
			int type) {
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
			convertView = inflator.inflate(R.layout.listview_row, null);
			view.txtViewTitle = (TextView) convertView
					.findViewById(R.id.text_title);
			view.txtSize = (TextView) convertView.findViewById(R.id.text_size);
			view.imgViewFlag = (ImageView) convertView
					.findViewById(R.id.imageView1);
			view.image_selected = (ImageView) convertView
					.findViewById(R.id.image_selected);
			Utils.setTypeface(activity, view.txtViewTitle, "Roboto-Light.ttf");
			Utils.setTypeface(activity, view.txtSize, "Roboto-Light.ttf");
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}
		if (position % 2 == 0) {
			convertView.setBackgroundColor(activity.getResources().getColor(
					R.color.list_1_color));
		} else {
			convertView.setBackgroundColor(activity.getResources().getColor(
					R.color.list_2_color));
		}
	
		return super.getView(position, convertView, parent);
	}
}
