/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox - SplashActivity.java
 * Date create: 2:49:15 PM - Nov 21, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashActivity. This class is shown every time this application is opened.
 * This class is for starting first time
 */
public class SplashActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        new InitData().execute();
        SaveData saveData = SaveData.getInstance(this);
        ConnectivityManager conn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            saveData.setNetworkStatus(true);
        } else {
            saveData.setNetworkStatus(false);
        }
    }

    private class InitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
            	if (SaveData.getInstance(SplashActivity.this).getDeviceWidth()<0||SaveData.getInstance(SplashActivity.this).getGridColumn()<0) {
					Utils.initDeviceWidth(SplashActivity.this);
				}
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            if (SaveData.getInstance(SplashActivity.this).isFirstTime()) {
                Intent intent = new Intent(SplashActivity.this, PINSetupActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            finish();
            super.onPostExecute(result);
        }
    }


    public void setInfo(String str)
    {
        
    }
}
