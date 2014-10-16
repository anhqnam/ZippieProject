
package com.lunextelecom.zippie.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.LinearLayout;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.fragment.ContactListFragment;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactActivity.
 */
@SuppressLint("InlinedApi") public class ContactActivity extends FragmentActivity implements OnClickListener {
    private ImageSwitcher imContactVippe, imContactAll, imContactFavorites, imContactSearch;

    private Button btnContactVippe, btnContactAll, btnContactFavorites, btnContactSearch;

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_contact);
        findViewById(R.id.btnContactHome).setOnClickListener(this);
        btnContactVippe = (Button)findViewById(R.id.btnContactVippe);
        btnContactVippe.setOnClickListener(this);
        btnContactAll = (Button)findViewById(R.id.btnContactAll);
        btnContactAll.setOnClickListener(this);
        btnContactFavorites = (Button)findViewById(R.id.btnContactFavorite);
        btnContactFavorites.setOnClickListener(this);
        btnContactSearch = (Button)findViewById(R.id.btnContactSearch);
        btnContactSearch.setOnClickListener(this);
        findViewById(R.id.btnContactAdd).setOnClickListener(this);
        imContactVippe = (ImageSwitcher)findViewById(R.id.imContactVippe);
        imContactAll = (ImageSwitcher)findViewById(R.id.imContactAll);
        imContactFavorites = (ImageSwitcher)findViewById(R.id.imContactFavorite);
        imContactSearch = (ImageSwitcher)findViewById(R.id.imContactSearch);
        onInit(Utils.FRAGMENT_CONTACT_VIPPE);
        showPicture(Utils.FRAGMENT_CONTACT_VIPPE);
        changeBackGroupIcon(Utils.FRAGMENT_CONTACT_VIPPE);
    }

    /**
     * On init.
     */
    private void onInit(String status) {
        // TODO Auto-generated method stub
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ContactListFragment contactfragment = ContactListFragment.newInstance(status);
        transaction.replace(R.id.flContact, contactfragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @param status
     */
    private void showPicture(String status) {
        if (status == Utils.FRAGMENT_CONTACT_ALL) {
            imContactVippe.setVisibility(View.GONE);
            imContactAll.setVisibility(View.VISIBLE);
            imContactFavorites.setVisibility(View.GONE);
            imContactSearch.setVisibility(View.GONE);

        } else if (status == Utils.FRAGMENT_CONTACT_VIPPE) {
            imContactVippe.setVisibility(View.VISIBLE);
            imContactAll.setVisibility(View.GONE);
            imContactFavorites.setVisibility(View.GONE);
            imContactSearch.setVisibility(View.GONE);
        } else if (status == Utils.FRAGMENT_CONTACT_FAVORITES) {
            imContactVippe.setVisibility(View.GONE);
            imContactAll.setVisibility(View.GONE);
            imContactFavorites.setVisibility(View.VISIBLE);
            imContactSearch.setVisibility(View.GONE);

        } else if (status == Utils.FRAGMENT_CONTACT_SEARCH) {
            imContactVippe.setVisibility(View.GONE);
            imContactAll.setVisibility(View.GONE);
            imContactFavorites.setVisibility(View.GONE);
            imContactSearch.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Change back group icon.
     *
     * @param status the status
     */
    private void changeBackGroupIcon(String status) {
        if (status == Utils.FRAGMENT_CONTACT_ALL) {
            btnContactAll.setBackgroundResource(R.drawable.ic_buttom_contact_2);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact);
            btnContactFavorites.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactSearch.setBackgroundResource(R.drawable.border_contact);

        } else if (status == Utils.FRAGMENT_CONTACT_VIPPE) {
            btnContactAll.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact_1);
            btnContactFavorites.setBackgroundResource( R.drawable.ic_button_contact_3);
            btnContactSearch.setBackgroundResource(R.drawable.border_contact);

        } else if (status == Utils.FRAGMENT_CONTACT_FAVORITES) {
            btnContactAll.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact);
            btnContactFavorites.setBackgroundResource(R.drawable.ic_buttom_contact_2);
            btnContactSearch.setBackgroundResource(R.drawable.border_contact);
        } else if (status == Utils.FRAGMENT_CONTACT_SEARCH) {
            LinearLayout llHeaderContact = (LinearLayout) findViewById(R.id.llHeaderContact);
            llHeaderContact.setVisibility(View.GONE);
            ActionBar myActionBar = getActionBar();
            myActionBar.show();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContact);
        if (fragment instanceof ContactListFragment) {
            finish();
        }
        super.onBackPressed();
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnContactHome:

                break;

            case R.id.btnContactVippe:
                onInit(Utils.FRAGMENT_CONTACT_VIPPE);
                showPicture(Utils.FRAGMENT_CONTACT_VIPPE);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_VIPPE);
                break;
            case R.id.btnContactAll:
                onInit(Utils.FRAGMENT_CONTACT_ALL);
                showPicture(Utils.FRAGMENT_CONTACT_ALL);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);

                break;

            case R.id.btnContactFavorite:
                onInit(Utils.FRAGMENT_CONTACT_FAVORITES);
                showPicture(Utils.FRAGMENT_CONTACT_FAVORITES);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_FAVORITES);

                break;
            case R.id.btnContactSearch:
                onInit(Utils.FRAGMENT_CONTACT_SEARCH);
                showPicture(Utils.FRAGMENT_CONTACT_SEARCH);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_SEARCH);
                break;
            case R.id.btnContactAdd:

                break;
            default:
                break;
        }
    }
}
