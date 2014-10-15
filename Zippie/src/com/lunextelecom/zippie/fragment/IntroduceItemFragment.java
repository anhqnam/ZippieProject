/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceItemFragment.
 */
public class IntroduceItemFragment extends Fragment{

    /** The m image id. */
    private int mImageId;

    /**
     * Instantiates a new introduce item fragment.
     * 
     * @param imageId the image id
     */
    public IntroduceItemFragment(int imageId) {
        // TODO Auto-generated constructor stub
        mImageId = imageId;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams") @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.layout_introduce_item, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_intro);
        imageView.setImageResource(mImageId);
        return view;
    }
}
