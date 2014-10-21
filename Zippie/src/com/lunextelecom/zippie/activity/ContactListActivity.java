
package com.lunextelecom.zippie.activity;

import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.ContactListAdapter.ContactAdapterListener;
import com.lunextelecom.zippie.database.ContactDatabaseHelper;
import com.lunextelecom.zippie.fragment.ContactListFragment;
import com.lunextelecom.zippie.sdk.ContactAPIHelper;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactActivity.
 */
@SuppressLint("InlinedApi")
public class ContactListActivity extends FragmentActivity implements OnClickListener,
ContactAdapterListener {

    /** The Constant NUM_ITEMS. */
    static final int NUM_ITEMS = 4;

    /** The im contact search. */
    private ImageSwitcher imContactVippe, imContactAll, imContactFavorites, imContactSearch;

    /** The iv delete search contact. */
    private ImageView ivSearchContact,ivDeleteSearchContact;

    /** The et search box. */
    private EditText etSearchBox;

    /** The m contact api. */
    private ContactAPIHelper mContactAPI;

    /** The m contact db. */
    private ContactDatabaseHelper mContactDb;

    /** The m search. */
    private String mSearch = "";

    /** The btn contact search. */
    private Button btnContactVippe, btnContactAll, btnContactFavorites, btnContactSearch;

    /** The m adapter. */
    MyAdapter mAdapter;

    /** The m pager. */
    ViewPager mPager;

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mContactAPI = ContactAPIHelper.getInstance(this);
        // final ProgressBar pbHeaderProgress = (ProgressBar)
        // findViewById(R.id.pbContactLoad);
        // pbHeaderProgress.setVisibility(View.VISIBLE);
        getListContactAsync();
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
        findViewById(R.id.btnBackContact).setOnClickListener(this);
        ivSearchContact = (ImageView) findViewById(R.id.ivSearchContact);
        ivSearchContact.setOnClickListener(this);
        imContactVippe = (ImageSwitcher)findViewById(R.id.imContactVippe);
        imContactAll = (ImageSwitcher)findViewById(R.id.imContactAll);
        imContactFavorites = (ImageSwitcher)findViewById(R.id.imContactFavorite);
        imContactSearch = (ImageSwitcher)findViewById(R.id.imContactSearch);
        etSearchBox =(EditText) findViewById(R.id.etSearchContact);
        ivDeleteSearchContact = (ImageView) findViewById(R.id.ivDeleteSearchContact);
        ivDeleteSearchContact.setOnClickListener(this);
        etSearchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String inputSearch = etSearchBox.getText().toString().toLowerCase(Locale.getDefault());
                if(inputSearch.length()>0){
                    ivDeleteSearchContact.setVisibility(View.VISIBLE);
                }else{
                    ivDeleteSearchContact.setVisibility(View.GONE);
                }
                onInit(Utils.FRAGMENT_CONTACT_SEARCH, inputSearch);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * The Class MyAdapter.
     */
    public static class MyAdapter extends FragmentStatePagerAdapter {

        /** The m list fragment. */
        private List<Fragment> mListFragment;

        /**
         * Instantiates a new my adapter.
         *
         * @param fm the fm
         * @param list the list
         */
        public MyAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            mListFragment = list;
        }

        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        /* (non-Javadoc)
         * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int position) {
            return mListFragment.get(position);
        }
    }

    /*private List<Fragment> getListFragment(){
        List<Fragment> list = new ArrayList<Fragment>();
        //list.add(ContactListFragment.newInstance("1"));
        //list.add(ContactListFragment.newInstance("2"));
        //list.add(ContactListFragment.newInstance("3"));
        //list.add(ContactListFragment.newInstance("4"));
        //list.add(ContactListFragment.newInstance("5"));
        return list;
    }*/

    /**
     * On init.
     *
     * @param status the status
     * @param search the search
     */
    private void onInit(String status, String search) {
        // TODO Auto-generated method stub
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ContactListFragment contactfragment =ContactListFragment.newInstanceFragment(status,search);
        transaction.replace(R.id.flContact, contactfragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Show picture.
     *
     * @param status the status
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
            RelativeLayout llHeaderContact = (RelativeLayout)findViewById(R.id.llHeaderContact);
            llHeaderContact.setVisibility(View.VISIBLE);
            RelativeLayout llSearchContact = (RelativeLayout)findViewById(R.id.llSearchContact);
            llSearchContact.setVisibility(View.GONE);
            btnContactAll.setBackgroundResource(R.drawable.ic_buttom_contact_2);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact);
            btnContactFavorites.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactSearch.setBackgroundResource(R.drawable.contact_white_rotation);

        } else if (status == Utils.FRAGMENT_CONTACT_VIPPE) {
            RelativeLayout llHeaderContact = (RelativeLayout)findViewById(R.id.llHeaderContact);
            llHeaderContact.setVisibility(View.VISIBLE);
            RelativeLayout llSearchContact = (RelativeLayout)findViewById(R.id.llSearchContact);
            llSearchContact.setVisibility(View.GONE);
            btnContactAll.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact_1);
            btnContactFavorites.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactSearch.setBackgroundResource(R.drawable.contact_white_rotation);

        } else if (status == Utils.FRAGMENT_CONTACT_FAVORITES) {
            RelativeLayout llHeaderContact = (RelativeLayout)findViewById(R.id.llHeaderContact);
            llHeaderContact.setVisibility(View.VISIBLE);
            RelativeLayout llSearchContact = (RelativeLayout)findViewById(R.id.llSearchContact);
            llSearchContact.setVisibility(View.GONE);
            btnContactAll.setBackgroundResource(R.drawable.ic_button_contact_3);
            btnContactVippe.setBackgroundResource(R.drawable.ic_button_contact);
            btnContactFavorites.setBackgroundResource(R.drawable.ic_buttom_contact_2);
            btnContactSearch.setBackgroundResource(R.drawable.contact_white_rotation);
        } else if (status == Utils.FRAGMENT_CONTACT_SEARCH) {
            RelativeLayout llHeaderContact = (RelativeLayout)findViewById(R.id.llHeaderContact);
            llHeaderContact.setVisibility(View.GONE);
            RelativeLayout llSearchContact = (RelativeLayout)findViewById(R.id.llSearchContact);
            llSearchContact.setVisibility(View.VISIBLE);

        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Fragment fragment =getSupportFragmentManager().findFragmentById(R.id.flContact);
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
                //mPager.setCurrentItem(0);
                onInit(Utils.FRAGMENT_CONTACT_VIPPE, mSearch);
                showPicture(Utils.FRAGMENT_CONTACT_VIPPE);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_VIPPE);
                break;
            case R.id.btnContactAll:
                //mPager.setCurrentItem(1);
                onInit(Utils.FRAGMENT_CONTACT_ALL, mSearch);
                showPicture(Utils.FRAGMENT_CONTACT_ALL);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);

                break;

            case R.id.btnContactFavorite:
                //mPager.setCurrentItem(2);
                onInit(Utils.FRAGMENT_CONTACT_FAVORITES, mSearch);
                showPicture(Utils.FRAGMENT_CONTACT_FAVORITES);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_FAVORITES);

                break;
            case R.id.btnContactSearch:
                //mPager.setCurrentItem(3);
                onInit(Utils.FRAGMENT_CONTACT_SEARCH, mSearch);
                showPicture(Utils.FRAGMENT_CONTACT_SEARCH);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_SEARCH);
                break;
            case R.id.btnContactAdd:
            case R.id.btnBackContact:
                onInit(Utils.FRAGMENT_CONTACT_ALL, mSearch);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
                showPicture(Utils.FRAGMENT_CONTACT_ALL);
                etSearchBox.setVisibility(View.GONE);
                ivSearchContact.setVisibility(View.VISIBLE);
                ivDeleteSearchContact.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearchBox.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                break;
            case R.id.ivSearchContact:
                etSearchBox.setVisibility(View.VISIBLE);
                ivSearchContact.setVisibility(View.GONE);
            case R.id.ivDeleteSearchContact:
                etSearchBox.setText("");
            default:
                break;
        }
    }

    /* (non-Javadoc)
     * @see com.lunextelecom.zippie.adapter.ContactListAdapter.ContactAdapterListener#callbackQuestionDialog(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void callbackQuestionDialog(Integer result, Integer method) {
        // TODO Auto-generated method stub

    }

    /**
     * Gets the list contact async.
     *
     * @return the list contact async
     */
    private void getListContactAsync() {
        final ProgressBar pbHeaderProgress = (ProgressBar)findViewById(R.id.pbContactLoad);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                pbHeaderProgress.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                    mContactAPI.GetContactAll(true);
                    mContactAPI.GetContactVippie(true);
                    mContactDb.GetContactFavorite(true);
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pbHeaderProgress.setVisibility(View.GONE);
                //mPager.setCurrentItem(1);
                onInit(Utils.FRAGMENT_CONTACT_ALL, mSearch);
                showPicture(Utils.FRAGMENT_CONTACT_ALL);
                changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
                /*mAdapter = new MyAdapter(getSupportFragmentManager(),getListFragment());
                mPager = (ViewPager)findViewById(R.id.vpContact);
                mPager.setAdapter(mAdapter);
                mPager.setOnPageChangeListener(new OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        // TODO Auto-generated method stub
                        if (position == 0) {
                            showPicture(Utils.FRAGMENT_CONTACT_VIPPE);
                            changeBackGroupIcon(Utils.FRAGMENT_CONTACT_VIPPE);
                            getListFragment().get(position);
                            Log.d("", "0");
                        } else if (position == 1) {
                            showPicture(Utils.FRAGMENT_CONTACT_ALL);
                            changeBackGroupIcon(Utils.FRAGMENT_CONTACT_ALL);
                            Log.d("", "1");
                        } else if (position == 2) {
                            showPicture(Utils.FRAGMENT_CONTACT_FAVORITES);
                            changeBackGroupIcon(Utils.FRAGMENT_CONTACT_FAVORITES);
                            Log.d("", "2");
                        } else if (position == 3) {
                            showPicture(Utils.FRAGMENT_CONTACT_SEARCH);
                            changeBackGroupIcon(Utils.FRAGMENT_CONTACT_SEARCH);
                            Log.d("", "3");
                        }
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                        // TODO Auto-generated method stub

                    }
                });*/
            }
        }.execute();
    }
}
