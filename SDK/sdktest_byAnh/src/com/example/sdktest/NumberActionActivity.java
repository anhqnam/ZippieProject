package com.example.sdktest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class NumberActionActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
        
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.fragment_container, new NumberActionFragment()).commit();
		}
	}
}
