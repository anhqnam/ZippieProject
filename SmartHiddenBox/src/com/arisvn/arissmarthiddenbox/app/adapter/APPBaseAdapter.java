
package com.arisvn.arissmarthiddenbox.app.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter;
import com.arisvn.arissmarthiddenbox.entity.AppItem;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.util.Utils;

public class APPBaseAdapter extends HiddenBaseAdapter {

	public APPBaseAdapter(Activity activity,
			ArrayList<? extends BaseItemEntity> fileItems, int type) {
        super(activity, fileItems, type);
        // TODO Auto-generated constructor stub
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
		ViewHolder view = (ViewHolder) convertView.getTag();
		AppItem obj = (AppItem) objs.get(position);

		if (objs.get(position).isCheck()) {
			view.image_selected.setVisibility(View.VISIBLE);
		} else {
			view.image_selected.setVisibility(View.GONE);
		}
		// load avarta
		if (obj.getLabel() != null) {
			if (view.txtViewTitle!=null) {
			view.txtViewTitle.setText(obj.getLabel());
			Utils.setTypeface(activity, view.txtViewTitle,"Roboto-Light.ttf");
			}
			if (view.txtSize!=null) {
			Utils.setTypeface(activity, view.txtSize,"Roboto-Light.ttf");
		}
		}
		if (obj.getIcon() != null) {
			Bitmap bitmap=Utils.drawableToBitmap(obj.getIcon());
			view.imgViewFlag.setImageBitmap(bitmap);
		}

		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arisvn.arissmarthiddenbox.adapter.HiddenBaseAdapter#getSelectedItem()
	 */
    @Override
	public ArrayList<AppItem> getSelectedItem() {
        // TODO Auto-generated method stub
		ArrayList<AppItem> appItems = new ArrayList<AppItem>();
		if (objs != null && objs.size() > 0) {
			for (int i = 0; i < objs.size(); i++) {
				AppItem appItem = (AppItem) objs.get(i);
				if (appItem.isCheck()) {
					appItems.add(appItem);
				}
			}
		}
		return appItems;
	}
}
