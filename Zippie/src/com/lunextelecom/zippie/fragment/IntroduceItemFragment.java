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
import com.lunextelecom.zippie.fragment.IntroduceFragment.IntroduceFrom;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceItemFragment.
 */
public class IntroduceItemFragment extends Fragment{

    /** The m image. */
    private Object mImage;

    /** The m mode type. */
    private IntroduceFrom mModeType;

    /**
     * Instantiates a new introduce item fragment.
     * 
     * @param image the image
     */
    public IntroduceItemFragment(Object image, IntroduceFrom modeType) {
        // TODO Auto-generated constructor stub
        mImage = image;
        mModeType = modeType;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams") @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.signup_introduce_item_lay, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_intro);
        if(mModeType == IntroduceFrom.LOCAL){
            imageView.setImageResource((Integer)mImage);
        }else{
            //TODO load bitmap from server set to imageview
        }
        return view;
    }
}
