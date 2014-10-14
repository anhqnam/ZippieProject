package com.arisvn.arissmarthiddenbox.media.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.util.Utils;

@SuppressWarnings("deprecation")
public class GalleryAdapter extends BaseAdapter {
	private int selectedPosition;

	/**
	 * @param fileItems
	 * @param context
	 */
	public GalleryAdapter(List<FileItem> fileItems, Context context) {
		super();
		this.fileItems = fileItems;
		this.context = context;
	}

	private List<FileItem> fileItems;
	private Context context;

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (fileItems != null) {
			return fileItems.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GalleryHolder galleryHolder;
		if (convertView == null) {
			galleryHolder = new GalleryHolder();
			// imageLoader.memoryCache.clearCache();
			LayoutInflater inflator = LayoutInflater.from(context);
			convertView = inflator.inflate(R.layout.gallery_item, null);
			galleryHolder.imageView = (ImageView) convertView
					.findViewById(R.id.gallery_item);
			galleryHolder.frame = (FrameLayout) convertView
					.findViewById(R.id.gallery_frame);
			convertView.setTag(galleryHolder);
		} else {
			galleryHolder = (GalleryHolder) convertView.getTag();
		}
		FileItem obj = fileItems.get(position);
		if (obj.getThumbnail()!=null) {
			galleryHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(
					obj.getThumbnail(), 0, obj.getThumbnail().length));
		} else {
			galleryHolder.imageView.setImageBitmap(Utils.getBitmapViaType(context, obj.getType()));
		}
		
		if (selectedPosition==position) {
			galleryHolder.frame.setLayoutParams(new Gallery.LayoutParams(
					(int) context.getResources().getDimension(
							R.dimen.icon_size_list), (int) ((int) context.getResources()
							.getDimension(R.dimen.icon_size_list)*1.1)));
			galleryHolder.imageView.setLayoutParams(new FrameLayout.LayoutParams(
					(int) context.getResources().getDimension(
							R.dimen.icon_size_list), (int) ((int) context.getResources()
							.getDimension(R.dimen.icon_size_list)*1.1)));
			
		} else {
			galleryHolder.frame.setLayoutParams(new Gallery.LayoutParams(
					(int) context.getResources().getDimension(
							R.dimen.icon_size_list), (int) context.getResources()
							.getDimension(R.dimen.icon_size_list)));
			galleryHolder.imageView.setLayoutParams(new FrameLayout.LayoutParams(
					(int) context.getResources().getDimension(
							R.dimen.icon_size_list), (int) context.getResources()
							.getDimension(R.dimen.icon_size_list)));
		}
		return convertView;

	}

	/**
	 * @return the selectedPosition
	 */
	public int getSelectedPosition() {
		return selectedPosition;
	}

	/**
	 * @param selectedPosition
	 *            the selectedPosition to set
	 */
	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	private class GalleryHolder {
		public ImageView imageView;
		public FrameLayout frame;
	}

}
