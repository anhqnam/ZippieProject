/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupAutoVerifyCodeFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SignupAutoVerifyCodeFragment.
 */
public class SignupAutoVerifyCodeFragment extends Fragment implements OnClickListener{

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams") @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.signup_auto_verify_code_lay, null);
        TextView signUpHeaderVerifyTitleTv = (TextView)view.findViewById(R.id.signup_header_verify_title_id);
        signUpHeaderVerifyTitleTv.setText(getResources().getString(R.string.signup_auto_verify_header_title_str));
        ImageView signUpHeaderVerifyBackIv = (ImageView)view.findViewById(R.id.signup_header_verify_back_id);
        ImageView signUpAutoVerifyCodeRuningIv = (ImageView)view.findViewById(R.id.signup_auto_verify_code_runing_id);
        TextView signUpAutoVerifyNotifyTv = (TextView)view.findViewById(R.id.signup_auto_verify_notify_id);
        TextView signUpAutoVerifyStartOverTv = (TextView)view.findViewById(R.id.signup_auto_verify_code_start_over_id);

        signUpHeaderVerifyBackIv.setOnClickListener(this);
        signUpAutoVerifyStartOverTv.setOnClickListener(this);

        Utils.setTypefaceRoboto(getActivity(), signUpHeaderVerifyTitleTv, signUpAutoVerifyNotifyTv, signUpAutoVerifyStartOverTv);

        signUpAutoVerifyCodeRuningIv.setBackgroundResource(R.drawable.signup_auto_verify_code_running);
        AnimationDrawable frameAnimation = (AnimationDrawable) signUpAutoVerifyCodeRuningIv.getBackground();
        frameAnimation.start();

        return view;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.signup_header_verify_back_id: //on click back on header

                break;
            case R.id.signup_auto_verify_code_start_over_id: //on click start over

                break;
            default:
                break;
        }
    }
}
