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

public class CustomConfirmDialog extends DialogFragment implements OnClickListener {
	private TextView mTitleTextView;
	private TextView mContentTextView;
	private Button mYesButton;
	private Button mNoButton;
	private ConfirmDialogClickListener mOnClickConfirmDialogListener;
	
	public interface ConfirmDialogClickListener{
		public void onClickConfirmDialogListener(boolean confirm);
	};
	
	public CustomConfirmDialog(){
		
	}
	
	public static CustomConfirmDialog newInstance(String title, String content){
		CustomConfirmDialog frag = new CustomConfirmDialog();
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

	public ConfirmDialogClickListener getOnClickConfirmDialogListener() {
		return mOnClickConfirmDialogListener;
	}

	public void setOnClickConfirmDialogListener(
			ConfirmDialogClickListener onClickConfirmDialogListener) {
		this.mOnClickConfirmDialogListener = onClickConfirmDialogListener;
	}
	
	
}