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

public class CustomAlertDialog extends DialogFragment {
	private TextView mTitleTextView;
	private TextView mContentTextView;
	private Button mYesButton;
	
	public CustomAlertDialog(){
		
	}
	
	public static CustomAlertDialog newInstance(String title, String content){
		CustomAlertDialog frag = new CustomAlertDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("content", content);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.customdialog_alert_lay, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		String title = getArguments().getString("title");
		String content = getArguments().getString("content");
		mTitleTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_title_id);
		mContentTextView = (TextView)v.findViewById(R.id.custom_dialog_alert_content_id);
		mTitleTextView.setText(title);
		mContentTextView.setText(content);
		mYesButton = (Button)v.findViewById(R.id.custom_dialog_alert_button_yes_id);
		mYesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		return v;
	}
}
