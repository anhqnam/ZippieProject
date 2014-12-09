package com.example.sdktest;

import unique.packagename.sdkwrapper.sip.ISipListener;
import unique.packagename.sdkwrapper.sip.SipWrapper;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SipActivity extends Activity implements OnClickListener, ISipListener{
	private Button mRegister;
	private Button mUnregister;
	private Button mClose;
	private SipWrapper mSipWrapper;
	private TextView mState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sip);
		setupUi();
		setupListeners();
		
		mSipWrapper = new SipWrapper();
		mSipWrapper.registerListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSipWrapper.unregisterListener(this);
	}
	
	private void setupUi() {
		mRegister = (Button) findViewById(R.id.register);
		mUnregister = (Button) findViewById(R.id.unregister);
		mClose = (Button) findViewById(R.id.close);
		
		mState = (TextView) findViewById(R.id.state);
	}

	private void setupListeners() {
		mRegister.setOnClickListener(this);
		mUnregister.setOnClickListener(this);
		mClose.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			mSipWrapper.register();
			break;
		case R.id.unregister:
			mSipWrapper.unregister();
			break;
		case R.id.close:
			DialerApplication.exitApp();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRegistrationResult(final int arg0, final int arg1, String arg2) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mState.setText("state: " + arg0 + " was: " + arg1);			
			}
		});
	}
	
	
}
