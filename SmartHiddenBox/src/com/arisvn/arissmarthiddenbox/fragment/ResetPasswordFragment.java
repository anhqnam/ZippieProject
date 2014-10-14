/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - ResetPasswordFragment.java
 * Date create: 11:51:16 AM - Mar 13, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.dialog.PrivacyDialog;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.listener.DialogListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ResetPasswordFragment.
 */
public class ResetPasswordFragment extends Fragment implements OnClickListener {

    /** The edit_current_pass. */
    private EditText edit_current_pass;

    /** The edit_new_pass. */
    private EditText edit_new_pass;

    /** The text_current_pass. */
    private TextView text_current_pass;

    /** The text_new_pass. */
    private TextView text_new_pass;

    /** The text_title. */
    private TextView text_title;
    
    private PrivacyDialog resetConfirmDialog;

    /** The go listener. */

    /**
     * The listener interface for receiving go events.
     * The class that is interested in processing a go
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addGoListener<code> method. When
     * the go event occurs, that object's appropriate
     * method is invoked.
     *
     * @see GoEvent
     */



    /**
     * Instantiates a new password dialog.
     *
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.layout_reset_password, container, false);
        init(view);
        return view;
    }

    /**
     * Inits the.
     *
     * @param view the view
     */
    private void init(View view) {
        edit_current_pass = (EditText)view.findViewById(R.id.edit_current_pass);
        edit_new_pass = (EditText)view.findViewById(R.id.edit_new_pass);
        text_current_pass = (TextView)view.findViewById(R.id.text_current_pass);
        text_new_pass = (TextView)view.findViewById(R.id.text_new_pass);
        text_title = (TextView)view.findViewById(R.id.text_title);
        edit_current_pass.requestFocus();
        TextView text0 = (TextView)view.findViewById(R.id.key_button_0);
        TextView text1 = (TextView)view.findViewById(R.id.key_button_1);
        TextView text2 = (TextView)view.findViewById(R.id.key_button_2);
        TextView text3 = (TextView)view.findViewById(R.id.key_button_3);
        TextView text4 = (TextView)view.findViewById(R.id.key_button_4);
        TextView text5 = (TextView)view.findViewById(R.id.key_button_5);
        TextView text6 = (TextView)view.findViewById(R.id.key_button_6);
        TextView text7 = (TextView)view.findViewById(R.id.key_button_7);
        TextView text8 = (TextView)view.findViewById(R.id.key_button_8);
        TextView text9 = (TextView)view.findViewById(R.id.key_button_9);
        ImageView clear = (ImageView)view.findViewById(R.id.key_button_clear);
        Button ok = (Button)view.findViewById(R.id.key_button_go);

        // set font for view
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Medium.ttf");
        text_new_pass.setTypeface(font);
        text_current_pass.setTypeface(font);
        text_title.setTypeface(font);
        text0.setTypeface(font);
        text1.setTypeface(font);
        text2.setTypeface(font);
        text3.setTypeface(font);
        text4.setTypeface(font);
        text5.setTypeface(font);
        text6.setTypeface(font);
        text7.setTypeface(font);
        text8.setTypeface(font);
        text9.setTypeface(font);

        // set clicklistener for view
        text0.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
        text7.setOnClickListener(this);
        text8.setOnClickListener(this);
        text9.setOnClickListener(this);
        clear.setOnClickListener(this);
        ok.setOnClickListener(this);

        // hide keyboard when forcus edittext
        edit_current_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        edit_new_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    /**
     * On click key.
     *
     * @param tag the tag
     */
    public void onClickKey(String tag) {
        int value = Integer.parseInt(tag);
        String textCurrent = "";
        String textNew = "";
        if (edit_current_pass.isFocused()) {
            textCurrent = edit_current_pass.getText().toString();
        }
        if (edit_new_pass.isFocused()) {
            textNew = edit_new_pass.getText().toString();
        }

        switch (value) {
            case -1:
                // Clear text
                int length = 0;
                if (edit_current_pass.isFocused()) {
                    length = textCurrent.length();
                }
                if (edit_new_pass.isFocused()) {
                    length = textNew.length();
                }
                if (length > 0) {
                    if (edit_current_pass.isFocused()) {
                        edit_current_pass.setText("");
                        edit_current_pass.append(textCurrent.substring(0, length - 1));
                    }
                    if (edit_new_pass.isFocused()) {
                        edit_new_pass.setText("");
                        edit_new_pass.append(textNew.substring(0, length - 1));
                    }
                }
                break;
            case -2:
                //
            	showResetConfirmDialog();
                break;

            default:
                // Input text
                if (edit_current_pass.isFocused()) {
                    edit_current_pass.append(tag);
                }
                if (edit_new_pass.isFocused()) {
                    edit_new_pass.append(tag);
                }
                break;
        }
    }

    /**
     * Checks if is match pass.
     *
     * @return true, if is match pass
     */
    public boolean isMatchPass(String password) {
        String currentPassText = getcurrentPassText();

        if (currentPassText.equals("") || password.equals(""))
            return false;
        String encryptedString;
        try {
            encryptedString = Encryption.encrypt(getActivity().getPackageName(), currentPassText);

            if (encryptedString.equals(password)) {
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(Utils.TAG, "Encryption fail due to: " + e.toString());
            return false;
        }
    }

    /**
     * Gets the current pass text.
     * 
     * @return the current pass text
     */
    public String getcurrentPassText() {
        return edit_current_pass.getText().toString();
    }

    /**
     * Gets the new pass text.
     * 
     * @return the new pass text
     */
    public String getnewPassText() {
        return edit_new_pass.getText().toString();
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        onClickKey(v.getTag().toString());
    }
    protected void showResetConfirmDialog() {
    	resetConfirmDialog = new PrivacyDialog(getActivity(), new DialogListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                resetConfirmDialog.dismiss();
            }

            @Override
            public void onAccept() {
                // TODO Auto-generated method stub
            	resetConfirmDialog.dismiss();
                 String textNew = "";
                 if (edit_new_pass.isFocused()) {
                     textNew = edit_new_pass.getText().toString();
                 }
            	if (isMatchPass(SaveData.getInstance(getActivity()).getPassword())) {
                    if (textNew != null && !textNew.equals("")) {
                    // reset pass success save new password
                    String encryptedString;
                    try {
                        encryptedString = Encryption.encrypt(getActivity().getPackageName(),
                                getnewPassText());
                        SaveData.getInstance(getActivity()).setPassword(encryptedString);
                        Toast.makeText(getActivity(), getString(R.string.saved_new_password),
                                Toast.LENGTH_SHORT).show();
                        // finish
                        getActivity().getSupportFragmentManager().popBackStack();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Log.e(Utils.TAG, "Encryption fail due to: " + e.toString());
                    }

                    } else {
                        Toast.makeText(getActivity(),
                                getString(R.string.please_input_password), Toast.LENGTH_SHORT)
                                .show();

                    }
                } else {
                        Toast.makeText(getActivity(), getString(R.string.current_pass_invalid),
                                Toast.LENGTH_SHORT).show();
                        edit_current_pass.setText("");
                }

            }
        },getString(R.string.confirmation),getString(R.string.reset_pass_confirmation),getString(R.string.cancel),getString(R.string.ok));
        resetConfirmDialog.show();
    }

}
