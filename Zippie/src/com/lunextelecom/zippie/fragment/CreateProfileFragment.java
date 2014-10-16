/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateProfileFragment.
 */
public class CreateProfileFragment extends Fragment implements OnClickListener{

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //Set tile action bar
        SpannableString s = new SpannableString(getResources().getString(R.string.create_profile_action_bar_title));
        s.setSpan(new TypefaceSpan("fonts/Roboto.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(s);

        //get view
        View view = inflater.inflate(R.layout.layout_create_profile, null);
        View btn_face = view.findViewById(R.id.create_profile_button_face);
        TextView tv_logo_face = (TextView)view.findViewById(R.id.create_profile_logo_face_text);
        TextView tv_text_face = (TextView)view.findViewById(R.id.create_profile_text_face);
        TextView tv_or = (TextView)view.findViewById(R.id.create_profile_or);
        TextView tv_add_photo = (TextView)view.findViewById(R.id.create_profile_add_photo);
        EditText et_name = (EditText)view.findViewById(R.id.create_profile_name);
        EditText et_email = (EditText)view.findViewById(R.id.create_profile_email);
        Button btn_finish = (Button)view.findViewById(R.id.create_profile_finish);

        //set font
        Utils.setTypeface(getActivity(), Utils.FONT_FACEBOLF_NAME, tv_logo_face);
        Utils.setTypefaceRoboto(getActivity(), tv_text_face, tv_or, tv_add_photo, et_name, et_email, btn_finish);

        //set on click listener
        btn_face.setOnClickListener(this);
        tv_add_photo.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
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
            //on click button facebook
            case R.id.create_profile_button_face:

                break;
                //on click link add photo
            case R.id.create_profile_add_photo:
                break;
                //on click button finish
            case R.id.create_profile_finish:
                break;
            default:
                break;
        }
    }
}
