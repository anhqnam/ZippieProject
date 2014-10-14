package com.example.sdktest;

import android.app.Application;

public class TestApplication extends Application{
	
	private static DialerApplication dialerApp;

	@Override
	public void onCreate() {
		super.onCreate();
		dialerApp = new DialerApplication(this);
		dialerApp.onCreate();
		
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		dialerApp.onLowMemory();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		dialerApp.onTerminate();
	}
}
