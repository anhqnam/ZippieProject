package com.lunextelecom.zippie.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.SplashScreenFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class SignUpActivity.
 */
public class SignUpActivity extends ActionBarActivity {

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new SplashScreenFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
