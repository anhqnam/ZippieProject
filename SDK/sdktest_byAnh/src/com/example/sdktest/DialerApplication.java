package com.example.sdktest;

import android.app.Activity;
import android.app.Application;
import unique.packagename.VippieApplication;

public class DialerApplication extends VippieApplication{

	public DialerApplication(Application application) {
		super(application);
	}
	
	@Override
	public Class<? extends Activity> getMainActivity() {
		return MainActivity.class;
	}
	
}
