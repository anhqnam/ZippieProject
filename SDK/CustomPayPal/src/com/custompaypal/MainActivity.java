/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * 
 */

package com.custompaypal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener,
OnMultiChoiceClickListener, OnDismissListener {

    /** The Constant PERSION. */
    final static public int PERSION = 0;

    /** The Constant GROUP. */
    final static public int GROUP = 1;

    /** The Constant COMPANY. */
    final static public int COMPANY = 2;

    /** The Constant METHOD_PICKUP. */
    final static public int METHOD_PICKUP = 0;

    /** The m type. */
    private int mType = PERSION;

    /** The _number. */
    private int mNumber = 1;

    /** The _method. */
    private int mMethod = METHOD_PICKUP;

    // Local references to our amounts and description
    /** The _the subtotal. */
    public double mSubtotal;

    /** The _tax amount. */
    private double mTaxAmount;

    /** The _pizza description. */
    private String _mDescription;

    /** The _size str. */
    private String _titlestr;

    /** The _price. */
    String mPrice;

    // All the booleans we will use for toppings
    /** The _cheese. */
    private boolean mPen = false;

    /** The _pepperoni. */
    private boolean mPencil = false;

    /** The _mushrooms. */
    private boolean mRuler = false;

    /** The _onions. */
    private boolean mHeadphone = false;

    // Reference to our number formatter (used for to format the amounts)
    /** The _df. */
    private NumberFormat _df;

    /*
     * PayPal library related fields
     */
    /** The launch pay pal button. */
    private CheckoutButton launchPayPalButton;

    /** The Constant PAYPAL_BUTTON_ID. */
    final static public int PAYPAL_BUTTON_ID = 10001;

    /** The Constant REQUEST_PAYPAL_CHECKOUT. */
    private static final int REQUEST_PAYPAL_CHECKOUT = 2;

    /** The m progress dialog. */
    private ProgressDialog mProgressDialog;

    /** The m paypal library init. */
    private boolean mPaypalLibraryInit = false;

    /** The m progress dialog running. */
    private boolean mProgressDialogRunning = false;

    /**
     * Checks if is online.
     * 
     * @return true, if is online
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.isOnline()) {
            Thread libraryInitializationThread = new Thread() {
                @Override
                public void run() {
                    initLibrary();
                }
            };
            libraryInitializationThread.start();
        }
        loadMainPage();
    }

    private void initLibrary() {
        PayPal pp = PayPal.getInstance();
        if (pp == null) {
            pp = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_SANDBOX);
            pp.setLanguage("en_US");
            pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
            pp.setShippingEnabled(true);
            pp.setDynamicAmountCalculationEnabled(false);
            mPaypalLibraryInit = true;
        }
    }

    /**
     * Load main page.
     */
    private void loadMainPage() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_Buy_id).setOnClickListener(this);
    }

    /**
     * Load customize page.
     */
    private void loadCustomizePage() {
        setContentView(R.layout.customize);
        Spinner sizeSpinner = (Spinner)findViewById(R.id.cus_spr01_id);
        sizeSpinner.setPrompt("USER");
        ArrayAdapter<CharSequence> sizeAdapter;
        sizeAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.add("PERSION ($7.99)");
        sizeAdapter.add("GROUP ($9.99)");
        sizeAdapter.add("COMPANY ($11.99)");
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(this);
        sizeSpinner.setSelection(mType);

        findViewById(R.id.cus_btn_choose_id).setOnClickListener(this);
        findViewById(R.id.cus_Continue_id).setOnClickListener(this);
    }

    /**
     * Load finalize page.
     */
    private void loadFinalizePage() {
        setContentView(R.layout.finalize);

        // set Spinner number
        Spinner numberSpinner = (Spinner)findViewById(R.id.fin_spr01_id);
        numberSpinner.setPrompt("SELECT NUMBER SOFTWARE");
        ArrayAdapter<CharSequence> numberAdapter;
        numberAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberAdapter.add("One");
        numberAdapter.add("Two");
        numberAdapter.add("Three");
        numberSpinner.setAdapter(numberAdapter);
        numberSpinner.setOnItemSelectedListener(this);
        numberSpinner.setSelection(mNumber - 1);

        // set Spinner MeThod
        Spinner methodSpinner = (Spinner)findViewById(R.id.fin_spr02_id);
        methodSpinner.setPrompt("SELECT METHOD");
        ArrayAdapter<CharSequence> methodAdapter;
        methodAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        methodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        methodAdapter.add("Pickup");
        methodSpinner.setAdapter(methodAdapter);
        methodSpinner.setOnItemSelectedListener(this);
        methodSpinner.setSelection(mMethod);

        findViewById(R.id.fin_ReviewOrder_id).setOnClickListener(this);
    }

    /**
     * Load review page.
     */
    private void loadReviewPage() {
        setContentView(R.layout.review);
        _df = DecimalFormat.getCurrencyInstance(Locale.ENGLISH);
        _df.setCurrency(Currency.getInstance("USD"));
        _titlestr = "Small";
        switch (mType) {
            case PERSION:
                _titlestr = "PERSION";
                break;
            case GROUP:
                _titlestr = "GROUP";
                break;
            case COMPANY:
                _titlestr = "COMPANY";
                break;
        }
        ((TextView)findViewById(R.id.review_pricename_id)).setText(_titlestr);
        _mDescription = mNumber + " " + _titlestr + " Software";
        if (mNumber == 1) {
            _mDescription = mNumber + " " + _titlestr + " Software";
        }
        ((TextView)findViewById(R.id.review_pricenumber_id)).setText(_mDescription);
        double priceAmount = 7.99;
        mPrice = "7.99";
        if (mNumber == 1) {
            if (mType == PERSION) {
                mPrice = "7.99";
                priceAmount = 7.99;
            } else if (mType == GROUP) {
                mPrice = "9.99";
                priceAmount = 9.99;
            } else {
                mPrice = "11.99";
                priceAmount = 11.99;
            }
        }
        if (mNumber == 2) {
            if (mType == PERSION) {
                mPrice = "9.99";
                priceAmount = 9.99;
            } else if (mType == GROUP) {
                mPrice = "15.99";
                priceAmount = 15.99;
            } else {
                mPrice = "19.99";
                priceAmount = 19.99;
            }
        } else if (mNumber == 3) {
            if (mType == PERSION) {
                mPrice = "11.99";
                priceAmount = 11.99;
            } else if (mType == GROUP) {
                mPrice = "20.99";
                priceAmount = 20.99;
            } else {
                mPrice = "25.99";
                priceAmount = 25.99;
            }
        }
        ((TextView)findViewById(R.id.review_pricesubtotal_id)).setText("$" + mPrice);

        if (mPen) {
            priceAmount += 2.00;
        }
        if (mPencil) {
            priceAmount += 2.00;
        }
        if (mRuler) {
            priceAmount += 2.00;
        }
        if (mHeadphone) {
            priceAmount += 200.00;
        }

        mSubtotal = priceAmount;
        ((TextView)findViewById(R.id.review_pricesubtotal_id)).setText(_df.format(priceAmount));

        mTaxAmount = priceAmount * .08;
        priceAmount += mTaxAmount;
        ((TextView)findViewById(R.id.review_pricetax_id)).setText(_df.format(mTaxAmount));
        ((TextView)findViewById(R.id.review_price_total_id)).setText(_df.format(priceAmount));
        if (mPaypalLibraryInit ==  true) {
            showPayPalButton();
        } else {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Loading PayPal Payment Library");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            mProgressDialogRunning = true;
            Thread newThread = new Thread(checkforPayPalInitRunnable);
            newThread.start();
        }

    }

    /** The checkfor pay pal init runnable. */
    final Runnable checkforPayPalInitRunnable = new Runnable() {
        @Override
        public void run() {
            checkForPayPalLibraryInit();
        }
    };

    /**
     * Check for pay pal library init.
     */
    private void checkForPayPalLibraryInit() {
        while (mPaypalLibraryInit == false) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Error initializing PayPal Library").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        runOnUiThread(showPayPalButtonRunnable);
    }

    final Runnable showPayPalButtonRunnable = new Runnable() {
        @Override
        public void run() {
            showPayPalButton();
        }
    };

    /**
     * Pay pal button click.
     * 
     * @param arg0 the arg0
     */
    private void PayPalButtonClick(View arg0) {
        PayPalPayment payment = new PayPalPayment();
        payment.setCurrencyType("USD");
        payment.setRecipient("hoangnam.hl2014@gmail.com");
        BigDecimal st = new BigDecimal(mSubtotal);
        st = st.setScale(2, RoundingMode.HALF_UP);
        payment.setSubtotal(st);
        payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

        PayPalInvoiceData invoice = new PayPalInvoiceData();
        BigDecimal tax = new BigDecimal(mTaxAmount);
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        invoice.setTax(tax);
        if (mMethod == METHOD_PICKUP) {
            invoice.setShipping(new BigDecimal("2.00"));
            PayPal.getInstance().setShippingEnabled(true);
        }

        PayPalInvoiceItem item1 = new PayPalInvoiceItem();
        item1.setName("Product");
        item1.setID("1234");
        item1.setTotalPrice(new BigDecimal(mPrice));
        item1.setUnitPrice(new BigDecimal(mPrice));
        item1.setQuantity(mNumber);

        invoice.getInvoiceItems().add(item1);
        if (mPen) {
            PayPalInvoiceItem item2 = new PayPalInvoiceItem();
            item2.setName("mPen");
            item2.setID("2345");
            item2.setTotalPrice(new BigDecimal("2.00"));
            item2.setUnitPrice(new BigDecimal("2.00"));
            item2.setQuantity(1);
            invoice.getInvoiceItems().add(item2);
        }
        if (mPencil) {
            PayPalInvoiceItem item2 = new PayPalInvoiceItem();
            item2.setName("Pencil");
            item2.setID("3456");
            item2.setTotalPrice(new BigDecimal("2.00"));
            item2.setUnitPrice(new BigDecimal("2.00"));
            item2.setQuantity(1);
            invoice.getInvoiceItems().add(item2);
        }
        if (mRuler) {
            PayPalInvoiceItem item2 = new PayPalInvoiceItem();
            item2.setName("Ruler");
            item2.setID("4567");
            item2.setTotalPrice(new BigDecimal("2.00"));
            item2.setUnitPrice(new BigDecimal("2.00"));
            item2.setQuantity(1);
            invoice.getInvoiceItems().add(item2);
        }
        if (mHeadphone) {
            PayPalInvoiceItem item2 = new PayPalInvoiceItem();
            item2.setName("HeadPhone");
            item2.setID("5678");
            item2.setTotalPrice(new BigDecimal("2.00"));
            item2.setUnitPrice(new BigDecimal("2.00"));
            item2.setQuantity(1);
            invoice.getInvoiceItems().add(item2);
        }

        payment.setInvoiceData(invoice);
        payment.setMerchantName("LUNEX TELECOM");

        Intent checkoutIntent = PayPal.getInstance().checkout(payment, this);
        startActivityForResult(checkoutIntent, REQUEST_PAYPAL_CHECKOUT);
    }

    /**
     * Show pay pal button.
     */
    private void showPayPalButton() {
        removePayPalButton();
        PayPal pp = PayPal.getInstance();
        launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43,
                CheckoutButton.TEXT_PAY);
        launchPayPalButton.setOnClickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;

        launchPayPalButton.setLayoutParams(params);
        launchPayPalButton.setId(PAYPAL_BUTTON_ID);

        ((LinearLayout)findViewById(R.id.LinearLayout1)).addView(launchPayPalButton);
        ((LinearLayout)findViewById(R.id.LinearLayout1)).setGravity(Gravity.CENTER_HORIZONTAL);
        if (mProgressDialogRunning) {
            mProgressDialog.dismiss();
            mProgressDialogRunning = false;
        }
    }

    /**
     * Removes the pay pal button.
     */
    private void removePayPalButton() {
        if (launchPayPalButton != null) {
            ((RelativeLayout)findViewById(R.id.LinearLayout1)).removeView(launchPayPalButton);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * android.content.DialogInterface.OnDismissListener#onDismiss(android.content
     * .DialogInterface)
     */
    @Override
    public void onDismiss(DialogInterface arg0) {
        findViewById(R.id.cus_Continue_id).setVisibility(View.VISIBLE);
    }

    /*
     * (non-Javadoc)
     * @see
     * android.content.DialogInterface.OnMultiChoiceClickListener#onClick(android
     * .content.DialogInterface, int, boolean)
     */
    @Override
    public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
        if (arg1 == 0) {
            mPen = arg2;
        } else if (arg1 == 1) {
            mPencil = arg2;
        } else if (arg1 == 2) {
            mRuler = arg2;
        } else {
            mHeadphone = arg2;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
     * .widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg0 == findViewById(R.id.cus_spr01_id)) {
            mType = arg2;
        } else if (arg0 == findViewById(R.id.fin_spr01_id)) {
            mNumber = arg2 + 1;
        } else if (arg0 == findViewById(R.id.fin_spr02_id)) {
            mMethod = arg2;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
     * .widget.AdapterView)
     */
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View arg0) {
        if (arg0 == (Button)findViewById(R.id.main_Buy_id)) {
            loadCustomizePage();
        } else if (arg0 == (Button)findViewById(R.id.cus_btn_choose_id)) {
            ((Button)findViewById(R.id.cus_Continue_id)).setVisibility(View.GONE);
            final CharSequence[] items = {
                    "Pen", "Pencil", "Ruler", "headphone"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Product follow");
            boolean[] mybools = {
                    mPen, mPencil, mRuler, mHeadphone
            };
            builder.setMultiChoiceItems(items, mybools, this);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.setOnDismissListener(this);
            alert.show();

        } else if (arg0 == (Button)findViewById(R.id.cus_Continue_id)) {
            loadFinalizePage();
        } else if (arg0 == (Button)findViewById(R.id.fin_ReviewOrder_id)) {
            loadReviewPage();
        } else if (arg0 == (Button)findViewById(R.id.result_BuyMore_id)) {
            loadMainPage();
        } else if (arg0 == (Button)findViewById(R.id.result_Done_id)) {
            finish();
        } else if (arg0 == (CheckoutButton)findViewById(PAYPAL_BUTTON_ID)) {
            PayPalButtonClick(arg0);
        }

    }

    public void PayPalActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                String payKey = intent.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                this.paymentSuccessed(payKey);
                break;
            case Activity.RESULT_CANCELED:
                this.paymentCanceled();
                break;
            case PayPalActivity.RESULT_FAILURE:
                String errorID = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                String errorMessage = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
                this.paymentFailed(errorID, errorMessage);
        }
    }

    /**
     * Load results page.
     */
    private void loadResultsPage() {
        setContentView(R.layout.results);
        findViewById(R.id.result_BuyMore_id).setOnClickListener(this);
        findViewById(R.id.result_Done_id).setOnClickListener(this);
    }

    private void paymentFailed(String errorID, String errorMessage) {
        loadResultsPage();
        ((TextView)findViewById(R.id.Result_Title_id)).setText("Failure!");
        ((TextView)findViewById(R.id.Result_Text1_id))
        .setText("We're sorry, but your payment failed.");
        ((TextView)findViewById(R.id.Result_Text2_id)).setText("Error: " + errorMessage);
        ((TextView)findViewById(R.id.Result_Text3_id)).setText("Error ID: " + errorID);
    }

    private void paymentCanceled() {
        loadResultsPage();
        ((TextView)findViewById(R.id.Result_Title_id)).setText("Canceled.");
        ((TextView)findViewById(R.id.Result_Text1_id)).setText("Your payment has been canceled.");
        ((TextView)findViewById(R.id.Result_Text2_id)).setText("Your account was not charged.");
        ((TextView)findViewById(R.id.Result_Text3_id)).setText("");
    }

    private void paymentSuccessed(String payKey) {
        loadResultsPage();
        ((TextView)findViewById(R.id.Result_Title_id)).setText("Success!");
        if (mMethod == METHOD_PICKUP) {
            ((TextView)findViewById(R.id.Result_Text1_id)).setText("Your order is being prepared!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == REQUEST_PAYPAL_CHECKOUT) {
            PayPalActivityResult(requestCode, resultCode, intent);
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
