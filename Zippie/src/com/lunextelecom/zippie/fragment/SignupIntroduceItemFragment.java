/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupIntroduceItemFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.SignupIntroduceFragment.IntroduceFrom;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceItemFragment.
 */
public class SignupIntroduceItemFragment extends Fragment{

    /** The m image. */
    private Object mImage;

    /** The m mode type. */
    private IntroduceFrom mModeType;

    /**
     * Instantiates a new introduce item fragment.
     *
     * @param image the image
     * @param modeType the mode type
     */
    public SignupIntroduceItemFragment(Object image, IntroduceFrom modeType) {
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
