package com.lunextelecom.zippie.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.SignupSplashScreenFragment;

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
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new SignupSplashScreenFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
