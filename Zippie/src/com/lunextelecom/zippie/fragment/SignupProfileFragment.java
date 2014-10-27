/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignupCreateProfileFragment.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateProfileFragment.
 */
public class SignupProfileFragment extends Fragment implements OnClickListener{

    private SignUpActivity mSignUpActivity;

	/* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //get view
    	View view = inflater.inflate(R.layout.signup_create_profile_lay, null);
    	initView(view);
        return view;
    }
    
    private void initView(View view)
    {
        TextView createProfileTitleTv = (TextView)view.findViewById(R.id.signup_header_text_id);
        createProfileTitleTv.setText(getResources().getString(R.string.createprofile_action_bar_title_str));
        View createProfileButtonFaceView = view.findViewById(R.id.createprofile_button_face_id);
        TextView createProfileLogoFaceTextTv = (TextView)view.findViewById(R.id.createprofile_logo_face_text_id);
        TextView createProfileTextTaceTv = (TextView)view.findViewById(R.id.createprofile_text_face_id);
        TextView createProfileOrTv = (TextView)view.findViewById(R.id.createprofile_or_id);
        TextView createProfileAddPhotoTv = (TextView)view.findViewById(R.id.createprofile_add_photo_id);
        EditText createProfileNameEt = (EditText)view.findViewById(R.id.createprofile_name_id);
        EditText createProfileEmailEt = (EditText)view.findViewById(R.id.createprofile_email_id);
        Button createProfileFinishBtn = (Button)view.findViewById(R.id.createprofile_finish_id);

        //set font
        Utils.setTypeface(getActivity(), Utils.FONT_FACEBOLF_NAME, createProfileLogoFaceTextTv);
        Utils.setTypefaceRoboto(getActivity(), createProfileTextTaceTv, createProfileOrTv, createProfileAddPhotoTv,
                createProfileNameEt, createProfileEmailEt, createProfileFinishBtn, createProfileTitleTv);

        //set on click listener
        createProfileButtonFaceView.setOnClickListener(this);
        createProfileAddPhotoTv.setOnClickListener(this);
        createProfileFinishBtn.setOnClickListener(this);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            //on click button facebook
            case R.id.createprofile_button_face_id:
                break;
                //on click link add photo
            case R.id.createprofile_add_photo_id:
                break;
                //on click button finish
            case R.id.createprofile_finish_id:
            {
            	if(mSignUpActivity != null)
            	{
            		mSignUpActivity.startIntroduceFragment();
            	}
                break;
            }
            default:
                break;
        }
    }
    
	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mSignUpActivity = (SignUpActivity) activity;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Zippie", getClass().getName()
					+ " must implement mSignUpActivity.startSplashFragment();");
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		mSignUpActivity = null;
		super.onDetach();
	}
}
