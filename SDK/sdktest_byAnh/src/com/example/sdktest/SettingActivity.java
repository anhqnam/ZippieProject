package com.example.sdktest;

import unique.packagename.sdkwrapper.settings.SettingsWrapper;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends Activity {

	private Button mBtn_getcountrycode;
	private Button mBtn_getdisplayname;
	private Button mBtn_getRegistrationNumber;
	private Button mBtn_isRegister;
	private TextView mtv_result;

	private SettingsWrapper settingwraper = new SettingsWrapper();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		mBtn_getcountrycode = (Button) findViewById(R.id.getcountrycode);
		mBtn_getdisplayname = (Button) findViewById(R.id.getdisplayname);
		mBtn_getRegistrationNumber = (Button) findViewById(R.id.getRegistrationNumber);
		mBtn_isRegister = (Button) findViewById(R.id.isRegistered);
		mtv_result = (TextView) findViewById(R.id.setting_result);

		mBtn_getcountrycode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String countrycode = settingwraper.getCoutryCode();
				mtv_result.setText(countrycode);
			}
		});

		mBtn_getdisplayname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = settingwraper.getDisplayName();
				mtv_result.setText(name);
			}
		});
		mBtn_getRegistrationNumber.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String registration = settingwraper.getRegistrationNumber();
				mtv_result.setText(registration);
			}
		});
		mBtn_isRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Boolean b = settingwraper.isRegistered();
				mtv_result.setText(b.toString());
			}
		});
	}

}
