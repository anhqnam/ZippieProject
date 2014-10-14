package com.arisvn.arissmarthiddenbox.media;

import java.io.File;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.listener.FileItemChangeObserver;
import com.arisvn.arissmarthiddenbox.media.adapter.GalleryAdapter;
import com.arisvn.arissmarthiddenbox.util.Utils;

@SuppressWarnings("deprecation")
public abstract class BaseMediaDetailFragment extends Fragment implements
		OnPageChangeListener, OnItemClickListener {

	protected List<FileItem> fileItems;
	protected int currentPosition;
	protected ViewPager viewPager;
	protected Gallery photoGallery;
	protected int type;
	protected GalleryAdapter galleryAdapter;
	protected boolean isGalleryItemClicked;
	protected TextView photoName;
	protected TextView photoSize;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			currentPosition = bundle.getInt(Utils.FILE_POSITION, 0);
			type = bundle.getInt(Utils.TYPE, Utils.TYPE_PHOTO);
			fileItems = App.getDB().getAllFile(type);
		}
		isGalleryItemClicked = false;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		currentPosition = arg0;
		updateSelectedItem();
		FileItemChangeObserver.getInsatance().notifyFileItemChange(
				fileItems.get(currentPosition));
		galleryAdapter.setSelectedPosition(currentPosition);
		galleryAdapter.notifyDataSetChanged();
		if (!isGalleryItemClicked) {
			photoGallery.setSelection(currentPosition, true);
		} else {
			isGalleryItemClicked=false;
		}
	}

	public abstract void updateSelectedItem();

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.e("Tamle", "Photo gallery selected: " + arg2);
		if (arg0 == photoGallery) {
			if (viewPager.getCurrentItem() != arg2) {
				isGalleryItemClicked = true;
				viewPager.setCurrentItem(arg2);
			}
		}

	}

	public void encryptSelectedItem() {
		Log.d("Tamle", getClass().getName()+" encryptSelectedItem");
		// TODO Auto-generated method stub
		FileItem fileItem=fileItems.get(currentPosition);
		String filePath=fileItem.getPathNew();
		if (filePath != null && filePath.trim().length() > 0) {
			if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
				// this is encrypted file
				String decryptePath = "";
				if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
					decryptePath = filePath.substring(0,
							filePath.lastIndexOf(Utils.ENCRYPT_EXTENSION));
				} else {
					decryptePath = filePath;
				}
				if (!filePath.equals(decryptePath)) {
					File file = new File(decryptePath);
					if (file.exists()) {
						boolean rename = file.renameTo(new File(filePath));
						if (rename) {
							int byte_to_encrypte = 0;

							if (filePath
									.startsWith(Utils.FOLDER + Utils._AUDIO)) {
								byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;

							} else if (filePath.startsWith(Utils.FOLDER
									+ Utils._PHOTO)) {
								byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;

							} else if (filePath.startsWith(Utils.FOLDER
									+ Utils._VIDEO)) {
								byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;

							}
							Utils.encrypt(getActivity(), filePath,
									byte_to_encrypte);
						}
					}

				}

			}

		}
		
	}
}
