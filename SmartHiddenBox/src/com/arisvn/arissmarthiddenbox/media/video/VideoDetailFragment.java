package com.arisvn.arissmarthiddenbox.media.video;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.TextView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.adapter.ViewPagerAdapter;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.media.BaseMediaDetailFragment;
import com.arisvn.arissmarthiddenbox.media.adapter.GalleryAdapter;
import com.arisvn.arissmarthiddenbox.util.Utils;

@SuppressWarnings("deprecation")
public class VideoDetailFragment extends BaseMediaDetailFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_video_detail, container,
				false);
		init(view);
		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		photoGallery = (Gallery) view.findViewById(R.id.imageGallery);
		viewPager = (ViewPager) view.findViewById(R.id.photo_detail_pager);
		photoName = (TextView) view.findViewById(R.id.imageName);
		photoSize = (TextView) view.findViewById(R.id.imageSize);
		Utils.setTypeface(getActivity(), photoName, "Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(), photoSize, "Roboto-Medium.ttf");
		viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fileItems, fileItems
				.get(currentPosition)));
		galleryAdapter = new GalleryAdapter(fileItems, getActivity());
		galleryAdapter.setSelectedPosition(currentPosition);
		photoGallery.setAdapter(galleryAdapter);
		photoGallery.setUnselectedAlpha(1.0f);
		if (currentPosition >= 0 && currentPosition < fileItems.size()) {
			viewPager.setCurrentItem(currentPosition);
			photoGallery.setSelection(currentPosition, true);
			updateSelectedItem();
		}
		viewPager.setOnPageChangeListener(this);
		photoGallery.setOnItemClickListener(this);
		TextView title=(TextView) view.findViewById(R.id.video_detail_title_tv);
		if (type==Utils.TYPE_AUDIO) {
			title.setText(getString(R.string.audio_player));
		} else if (type==Utils.TYPE_VIDEO) {
			title.setText(getString(R.string.video_player));
		}
	}

	@Override
	public void updateSelectedItem() {
		// TODO Auto-generated method stub
		FileItem fileItem = fileItems.get(currentPosition);
		if (fileItem != null) {
			photoName.setText(fileItem.getName());
			photoName.setSelected(true);
			photoSize.setText(Formatter.formatFileSize(getActivity(),
					fileItem.getSize()));
		}

	}

}
