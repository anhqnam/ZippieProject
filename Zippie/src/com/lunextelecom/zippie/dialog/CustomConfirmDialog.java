/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhHa
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */
package com.lunextelecom.zippie.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomConfirmDialog.
 */
public class CustomConfirmDialog extends DialogFragment implements OnClickListener {
	
	/** The m title text view. */
	private TextView mTitleTextView;
	
	/** The m content text view. */
	private TextView mContentTextView;
	
	/** The m yes button. */
	private Button mYesButton;
	
	/** The m no button. */
	private Button mNoButton;
	
	/** The m on click confirm dialog listener. */
	private ConfirmDialogClickListener mOnClickConfirmDialogListener;
	
	/**
	 * The listener interface for receiving confirmDialogClick events.
	 * The class that is interested in processing a confirmDialogClick
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addConfirmDialogClickListener<code> method. When
	 * the confirmDialogClick event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ConfirmDialogClickEvent
	 */
	public interface ConfirmDialogClickListener{
		
		/**
		 * On click confirm dialog listener.
		 *
		 * @param confirm the confirm
		 */
		public void onClickConfirmDialogListener(boolean confirm);
	};
	
	/**
	 * Instantiates a new custom confirm dialog.
	 */
	public CustomConfirmDialog(){
		
	}
	
	/**
	 * New instance.
	 *
	 * @param title the title
	 * @param content the content
	 * @return the custom confirm dialog
	 */
	public static CustomConfirmDialog newInstance(String title, String content){
		CustomConfirmDialog frag = new CustomConfirmDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("content", content);
		frag.setArguments(args);
		return frag;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.customdialog_confirm_lay, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		String title = getArguments().getString("title");
		String content = getArguments().getString("content");
		mTitleTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_title_id);
		mContentTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_content_id);
		mTitleTextView.setText(title);
		mContentTextView.setText(content);
		mYesButton = (Button)v.findViewById(R.id.custom_dialog_alert_button_yes_id);
		mNoButton = (Button)v.findViewById(R.id.custom_dialog_alert_button_no_id);
		mYesButton.setOnClickListener(this);
		mNoButton.setOnClickListener(this);
		return v;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.custom_dialog_alert_button_yes_id:
			if(mOnClickConfirmDialogListener != null){
				mOnClickConfirmDialogListener.onClickConfirmDialogListener(true);
			}
			break;
		case R.id.custom_dialog_alert_button_no_id:
			if(mOnClickConfirmDialogListener != null){
				mOnClickConfirmDialogListener.onClickConfirmDialogListener(false);
			}
			break;
		}
	}

	/**
	 * Gets the on click confirm dialog listener.
	 *
	 * @return the on click confirm dialog listener
	 */
	public ConfirmDialogClickListener getOnClickConfirmDialogListener() {
		return mOnClickConfirmDialogListener;
	}

	/**
	 * Sets the on click confirm dialog listener.
	 *
	 * @param onClickConfirmDialogListener the new on click confirm dialog listener
	 */
	public void setOnClickConfirmDialogListener(
			ConfirmDialogClickListener onClickConfirmDialogListener) {
		this.mOnClickConfirmDialogListener = onClickConfirmDialogListener;
	}
	
	
}
