package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashScreenFragment.
 */
public class SignupSplashScreenFragment extends Fragment {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams") 
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.signup_splash_screen_lay, null);
		int secondsDelayed = 1000;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container,
						new SignupWelcomeScreenFragment());
				// transaction.addToBackStack(null);
				transaction.commit();
			}
		}, secondsDelayed);
		return view;
	}
}
