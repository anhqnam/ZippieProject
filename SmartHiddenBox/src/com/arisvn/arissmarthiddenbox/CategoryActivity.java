/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox - CategoryActivity.java
 * Date create: 2:39:22 PM - Nov 8, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.arisvn.arissmarthiddenbox.dialog.PrivacyDialog;
import com.arisvn.arissmarthiddenbox.dialog.UserGuideDialog;
import com.arisvn.arissmarthiddenbox.fragment.CategoryFragment;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment;
import com.arisvn.arissmarthiddenbox.listener.DialogListener;
import com.arisvn.arissmarthiddenbox.listener.OnSelectedItemChangeListener;
import com.arisvn.arissmarthiddenbox.media.BaseMediaDetailFragment;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdView;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryActivity.
 */
public class CategoryActivity extends FragmentActivity implements
		OnSelectedItemChangeListener {
    /** The privacy_dialog. */
    private PrivacyDialog privacy_dialog;
    private AdView adView;
    /*
     * (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Look up the AdView as a resource and load a request.
        adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        adView.setAdListener(new AdListener() {

            @Override
            public void onReceiveAd(Ad arg0) {
                // Animation showAnim =
                // AnimationUtils.loadAnimation(MovieActivity.this,
                // R.anim.admod_show);
                adView.setVisibility(View.VISIBLE);
                // adView.startAnimation(showAnim);
            }

            @Override
            public void onPresentScreen(Ad arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onLeaveApplication(Ad arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDismissScreen(Ad arg0) {
                // TODO Auto-generated method stub
            }
        });
        // Clear all back stack.
        int backStackCount = getSupportFragmentManager()
                .getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getSupportFragmentManager()
                    .getBackStackEntryAt(i).getId();

            getSupportFragmentManager().popBackStack(backStackId,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
        App.needShowLogin = false;
        onInit();
        if (SaveData.getInstance(this).isFirstTime()) {
            showPrivacy();
        }

    }

    /**
     * Show privacy.
     */
    private void showPrivacy() {
        privacy_dialog = new PrivacyDialog(CategoryActivity.this, new DialogListener() {

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public void onAccept() {
                // TODO Auto-generated method stub
                SaveData.getInstance(CategoryActivity.this).setFirstTime(false);
                privacy_dialog.dismiss();
                showUserGuide();

            }
        },"","",getString(R.string.refuse_privacy),getString(R.string.accept_privacy));
        privacy_dialog.setCancelable(false);
        privacy_dialog.show();
    }

    private void showUserGuide() {
        // TODO Auto-generated method stub
        if (SaveData.getInstance(getApplicationContext()).getFirstTimeCategory()) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(R.drawable.main1);
            list.add(R.drawable.main2);
            if (list != null && list.size() > 0) {
                UserGuideDialog userGuideDialog = new UserGuideDialog(this,
                        R.style.CustomDialogTheme, list);
                userGuideDialog.show();
                SaveData.getInstance(getApplicationContext()).setFirstTimeCategory(false);
            }
        }
    }

    /**
     * On init.
     */
    private void onInit() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, new CategoryFragment());
        transaction.addToBackStack(Utils.FRAGMENT_CATEGORY);
        transaction.commit();

    }

	/*
	 * (non-Javadoc)
	 * 
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (App.needShowLogin && !App.isShowing) {
            Intent intent = new Intent(CategoryActivity.this,
                    LoginActivity.class);
            intent.setAction(Utils.ACTION_CATEGORY);
            startActivity(intent);
            App.isShowing = true;
        } else {
            // the next onResume will show login if not call onCreate method
            App.needShowLogin = true;
        }
        if (SaveData.getInstance(this).getNetworkStatus()) {
            adView.setVisibility(View.VISIBLE);
            adView.loadAd(new AdRequest());
        } else {
            adView.setVisibility(View.GONE);
        }
    }

	/*
	 * (non-Javadoc)
	 * 
     * @see com.arisvn.arissmarthiddenbox.listener.OnSelectedItemChangeListener#
     * onSelectedItemChanged(int)
     */
    @Override
    public void onSelectedItemChanged(int selectedItem) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onFinishLoadItems() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onChangedData(boolean result, int action) {
        // TODO Auto-generated method stub
        String TAG = null;
        if (action == Utils.TYPE_PHOTO) {
            TAG = Utils.FRAGMENT_PHOTO;
        } else if (action == Utils.TYPE_VIDEO) {
            TAG = Utils.FRAGMENT_VIDEO;
        } else if (action == Utils.TYPE_AUDIO) {
            TAG = Utils.FRAGMENT_AUDIO;
        } else if (action == Utils.TYPE_MESSAGE) {
            TAG = Utils.FRAGMENT_SMS;
        }
        else if (action == Utils.TYPE_CALLLOG) {
            TAG = Utils.FRAGMENT_CALL_LOG;
        }
        ((HiddenBaseFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG)).flagChangeData = result;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof CategoryFragment) {
            finish();
        } else if (fragment instanceof BaseMediaDetailFragment) {
			((BaseMediaDetailFragment)fragment).encryptSelectedItem();
		}
        super.onBackPressed();
    }
}
