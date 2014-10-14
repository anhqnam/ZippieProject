/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.dialog - CameraDialogFragment.java
 * Date create: 1:40:31 PM - Feb 24, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.listener.OnCameraSelectedListener;
import com.arisvn.arissmarthiddenbox.listener.OnCameraSelectedListener.CAMERA_ITEM;
// TODO: Auto-generated Javadoc
/**
 * The Class CameraDialogFragment.
 */
@SuppressLint("ValidFragment")
public class CameraDialogFragment extends DialogFragment implements OnClickListener {
    /** The Constant SELECT_FILE_DIALOG. */
    public static final String TAG = "CameraDialogFragment";

    /**
     * Instantiates a new camera dialog fragment.
     * 
     * @param listener the listener
     */
    public CameraDialogFragment(OnCameraSelectedListener listener) {
        super();
        setCancelable(true);
        this.listener = listener;
    }

    /** The listener. */
    private OnCameraSelectedListener listener;

    /**
     * New instance.
     * 
     * @param listener the listener
     * @return the camera dialog fragment
     */
	public static CameraDialogFragment newInstance(
            OnCameraSelectedListener listener) {
        CameraDialogFragment f = new CameraDialogFragment(listener);
        return f;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.camera_layout, container);
        view.findViewById(R.id.take_photo).setOnClickListener(this);
        view.findViewById(R.id.record_video).setOnClickListener(this);
        view.findViewById(R.id.record_audio).setOnClickListener(this);
        return view;
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        dismiss();
        switch (arg0.getId()) {
            case R.id.take_photo:
                if (listener != null) {
                    listener.onCameraSelected(CAMERA_ITEM.TAKE_PICTURE);
                }

                break;
            case R.id.record_audio:
                if (listener != null) {
                    listener.onCameraSelected(CAMERA_ITEM.RECORD_AUDIO);
                }
                break;
            case R.id.record_video:
                if (listener != null) {
                    listener.onCameraSelected(CAMERA_ITEM.RECORD_VIDEO);
                }
                break;

            default:
                break;
        }

    }
}
