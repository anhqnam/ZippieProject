package com.example.sdktest;

import unique.packagename.features.profile.UserProfile;
import unique.packagename.sdkwrapper.profile.ProfileWrapper;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends ActionBarActivity {

	Button getProfile;
	Button setEmail;
	Button setUserProfile;
	TextView showProfile;

	ProfileWrapper mPW = new ProfileWrapper();
	
	UserProfile mProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		getProfile = (Button) findViewById(R.id.button1);
		setEmail = (Button) findViewById(R.id.button2);
		setUserProfile = (Button)findViewById(R.id.button3);
		
		showProfile = (TextView) findViewById(R.id.textView1);
		

		
	/*	final UserProfile iUserProfile = new UserProfile(p);
		iUserProfile.setCity("HN");
		iUserProfile.setCountry("VN");
		iUserProfile.setEmail("HN@gmail.com");
		iUserProfile.setFirstName("Nguyen");
		iUserProfile.setLastname("Nam");
		iUserProfile.setPhoneNumber("123456789");
		iUserProfile.setSex("Male");
		iUserProfile.setSharePhone(true);
		iUserProfile.setUsername("Hoalong");
		iUserProfile.setTimezone(8);
		java.util.Date mDate = new java.util.Date();
		iUserProfile.setBirthday(mDate);*/

		getProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						mProfile = mPW.getProfile();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (mProfile != null) {
									showProfile.setText(mProfile.toString());
								}
							}
						});

					}
				}).start();
			}
		});

		setEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String Emailstr = "NamDr@gmail.com";
						final boolean a = mPW.setEmail(Emailstr);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), a + "",
										Toast.LENGTH_SHORT).show();
								Log.d("SetEmail: ", a+"");
							}
						});

					}
				}).start();
			}
		});
		
		setUserProfile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						UserProfile p = mProfile;
						p.setUsername("NamTest");
						p.setCity("DaNang");
						p.setEmail("anhvtex@abc.com");
						p.setFirstName("NamNguyen");
						p.setLastname("HaiLe");
						final int number = mPW.setUserProfile(p, getApplicationContext());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), number + "",
										Toast.LENGTH_SHORT).show();
								Log.d("SetEmail: ", number+"");
							}
						});

					}
				}).start();
			}
		});
	}
}
