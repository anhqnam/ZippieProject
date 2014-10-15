package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashScreenFragment.
 */
public class SplashScreenFragment extends Fragment {

    /** The m action bar. */
    private ActionBar mActionBar;

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
	    @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	mActionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
	mActionBar.hide();
	View view = inflater.inflate(R.layout.splash_screen, null);
	int secondsDelayed = 3000;
	new Handler().postDelayed(new Runnable() {
	    @Override
	    public void run() {
		mActionBar.show();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment_container, new WelcomeScreenFragment());
		transaction.addToBackStack(null);
		transaction.commit();
	    }
	}, secondsDelayed);
	return view;
    }
}
