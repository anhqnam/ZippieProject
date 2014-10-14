/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - CategoryFragment.java
 * Date create: 2:57:36 PM - Nov 21, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.app.APPFragment;
import com.arisvn.arissmarthiddenbox.calllog.CallLogFragment;
import com.arisvn.arissmarthiddenbox.dialog.CameraDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.UserGuideDialog;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.listener.OnCameraSelectedListener;
import com.arisvn.arissmarthiddenbox.media.audio.AudioBoxFragment;
import com.arisvn.arissmarthiddenbox.media.photo.PhotoBoxFragment;
import com.arisvn.arissmarthiddenbox.media.video.VideoBoxFragment;
import com.arisvn.arissmarthiddenbox.sms.SMSFragment;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryFragment.
 */
public class CategoryFragment extends Fragment implements
		OnCameraSelectedListener, OnClickListener {

    /** The Constant REQUEST_IMAGE. */
    public static final int REQUEST_IMAGE = 1;

    /** The Constant REQUEST_VIDEO. */
    public static final int REQUEST_VIDEO = 2;

    /** The Constant REQUEST_AUDIO. */
    public static final int REQUEST_AUDIO = 3;
    /** The user guide dialog. */
    private UserGuideDialog userGuideDialog;
    /** The password_dialog. */
    /*
     * (non-Javadoc)
     * 
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main, container, false);
		TextView title=(TextView) view.findViewById(R.id.title_topbar);
		Utils.setTypeface(getActivity(),title,"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_SMS),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_CallLog),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_Audio),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_Video),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_Camera),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_Photo),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_App),"Roboto-Medium.ttf");
		Utils.setTypeface(getActivity(),(TextView) view.findViewById(R.id.tv_resetpw),"Roboto-Medium.ttf");
		view.findViewById(R.id.userguide).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showUserGuide();

					}
				});
		view.findViewById(R.id.sms_item).setOnClickListener(this);
		view.findViewById(R.id.calllog_item).setOnClickListener(this);
		view.findViewById(R.id.camera_item).setOnClickListener(this);
		view.findViewById(R.id.audio_item).setOnClickListener(this);
		view.findViewById(R.id.video_item).setOnClickListener(this);
		view.findViewById(R.id.photo_item).setOnClickListener(this);
		view.findViewById(R.id.app_item).setOnClickListener(this);
		view.findViewById(R.id.reset_pw_item).setOnClickListener(this);

        return view;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

	}

	/*
	 * (non-Javadoc)
	 * 
     * @see com.arisvn.arissmarthiddenbox.listener.OnCameraSelectedListener#
     * onCameraSelected
     * (com.arisvn.arissmarthiddenbox.listener.OnCameraSelectedListener
     * .CAMERA_ITEM)
     */
    @Override
    public void onCameraSelected(CAMERA_ITEM item) {
        // TODO Auto-generated method stub
        Log.d(Utils.TAG, getClass().getName() + "::onCameraSelected()::" + item.toString());
        switch (item) {
            case TAKE_PICTURE:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // start the image capture Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case RECORD_AUDIO:
                intent = new Intent(
                        MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_AUDIO);

                break;
            case RECORD_VIDEO:
                intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivityForResult(intent, REQUEST_VIDEO);
                break;

            default:
                break;
        }

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(Utils.TAG, getClass().getName() + "::onActivityResult::"
                + (resultCode == Activity.RESULT_OK ? "RESULT_OK" : "RESULT_CANCEL"));
        App.needShowLogin = false;
        final String[] columns = {
                Utils._DATA, Utils._DISPLAY_NAME, Utils.MEDIA_ID, Utils._SIZE, Utils._MIME_TYPE
        };
        if (requestCode == REQUEST_IMAGE
                && resultCode == Activity.RESULT_OK && null != data) {
            Uri imageURI = data.getData();
            Bitmap bitmap = null;
            if (imageURI == null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                String url = MediaStore.Images.Media.insertImage(
                        getActivity().getContentResolver(), bitmap, null, null);
                imageURI = Uri.parse(url);
            }

            if (imageURI != null) {
                Cursor cursor = getActivity().getContentResolver().query(
                        imageURI, columns, null, null, null);
                FileItem fileItem = getFileItem(cursor, Utils.TYPE_PHOTO);
                Utils.processEncrypt(getActivity(), fileItem);
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_AUDIO) {
            Uri audioFileUri = data.getData();
            if (audioFileUri != null) {
                Cursor cursor = getActivity().getContentResolver().query(
                        audioFileUri, columns, null, null, null);
                FileItem fileItem = getFileItem(cursor, Utils.TYPE_AUDIO);
                Utils.processEncrypt(getActivity(), fileItem);
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO) {
            Uri videoFileUri = data.getData();
            if (videoFileUri != null) {
                Cursor cursor = getActivity().getContentResolver().query(
                        videoFileUri, columns, null, null, null);
                FileItem fileItem = getFileItem(cursor, Utils.TYPE_VIDEO);
                Utils.processEncrypt(getActivity(), fileItem);
            }
        }
    }

    /**
     * Gets the file item.
     * 
     * @param cursor the cursor
     * @return the file item
     */
    private FileItem getFileItem(Cursor cursor, int type) {
        FileItem item = null;
        if (cursor != null && cursor.moveToFirst()) {
            int ID_column = cursor.getColumnIndex(Utils.MEDIA_ID);
            do {
                item = new FileItem();
                String filePath = cursor.getString(cursor
                        .getColumnIndex(Utils._DATA));
                String externalStorageDirectory = Environment
                        .getExternalStorageDirectory().getAbsolutePath();
                if (filePath.startsWith(externalStorageDirectory)) {
                    int id = cursor.getInt(ID_column);
                    item.setPathFrom(filePath);
                    item.setName(cursor.getString(cursor
                            .getColumnIndex(Utils._DISPLAY_NAME)));
                    item.setId(id);
                    item.setType(type);
                    item.setSize(cursor.getLong(cursor
                            .getColumnIndex(Utils._SIZE)));
                }

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return item;
    }

    /**
     * Show user guide.
     */
    private void showUserGuide() {
        // TODO Auto-generated method stub
        List<Integer> list = Utils.initDataUserGuide();
        if (list != null && list.size() > 0) {
            userGuideDialog = new UserGuideDialog(getActivity(), R.style.CustomDialogTheme, list);
            userGuideDialog.show();
        }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		switch (v.getId()) {
		case R.id.photo_item:
			transaction.replace(R.id.fragment_container,
					new PhotoBoxFragment(), Utils.FRAGMENT_PHOTO);
			transaction.addToBackStack(Utils.FRAGMENT_PHOTO);
			break;
		case R.id.audio_item:
			transaction.replace(R.id.fragment_container,
					new AudioBoxFragment(), Utils.FRAGMENT_AUDIO);
			transaction.addToBackStack(Utils.FRAGMENT_AUDIO);
			break;

		case R.id.video_item:
			transaction.replace(R.id.fragment_container,
					new VideoBoxFragment(), Utils.FRAGMENT_VIDEO);
			transaction.addToBackStack(Utils.FRAGMENT_VIDEO);
			break;

		case R.id.sms_item:
			transaction.replace(R.id.fragment_container, new SMSFragment(),
					Utils.FRAGMENT_SMS);
			transaction.addToBackStack(Utils.FRAGMENT_SMS);

			break;
		case R.id.calllog_item:
			transaction.replace(R.id.fragment_container, new CallLogFragment(),
					Utils.FRAGMENT_CALL_LOG);
			transaction.addToBackStack(Utils.FRAGMENT_CALL_LOG);

			break;
		case R.id.camera_item:
			// show camera dialog here
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Fragment prev = getFragmentManager().findFragmentByTag(
					CameraDialogFragment.TAG);
			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);

			// Create and show the dialog.
			DialogFragment newFragment = CameraDialogFragment.newInstance(this);
			newFragment.show(ft, Utils.SELECT_FILE_DIALOG);

			break;
		case R.id.app_item:
			transaction.replace(R.id.fragment_container, new APPFragment(),
					Utils.FRAGMENT_APP);
			transaction.addToBackStack(Utils.FRAGMENT_APP);
			break;
            case R.id.reset_pw_item:
                transaction.replace(R.id.fragment_container, new ResetPasswordFragment(),
                        Utils.FRAGMENT_RESET_PASSWORD);
                transaction.addToBackStack(Utils.FRAGMENT_RESET_PASSWORD);

                break;

		default:
			break;
		}
		transaction.commit();

    }


    /**
     * Show dialog change password.
     */



}
