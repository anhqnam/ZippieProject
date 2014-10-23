/*
 * 
 */

package com.androidpaypal;

import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalPayment;

/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

    public static String resultTitle;

    public static String resultInfo;

    public static String resultExtra;

    public static boolean back;

    /** The _paypal library init. */
    public Boolean _paypalLibraryInit;

    /*
     * (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLibraryPayPal();
        showPayPalButton();
    }

    /**
     * Inits the library pay pal.
     */
    public void initLibraryPayPal() {
        PayPal pp = PayPal.getInstance();

        if (pp == null) {
            pp = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_NONE);
            pp.setLanguage("en_US");
            pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
            pp.setShippingEnabled(true);
            _paypalLibraryInit = true;
        }
    }

    /**
     * Show pay pal button.
     */
    @SuppressLint("InlinedApi")
    public void showPayPalButton() {
        PayPal pp = PayPal.getInstance();
        CheckoutButton checkbtn = pp.getCheckoutButton(this, PayPal.BUTTON_278x43,
                CheckoutButton.TEXT_PAY);
        checkbtn.setOnClickListener(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;
        checkbtn.setLayoutParams(params);
        checkbtn.setId(1);
        ((RelativeLayout)findViewById(R.id.RelativeLayout01)).addView(checkbtn);
        ((RelativeLayout)findViewById(R.id.RelativeLayout01)).setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onClick(View v) {
        PayPalPayment payment = new PayPalPayment();
        // DVT
        payment.setCurrencyType("VND");
        // to Email
        payment.setRecipient("hoangnam.hl2014@gmail.com");
        // Total
        payment.setSubtotal(new BigDecimal("20.00"));
        // set paytype
        payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

        PayPalInvoiceData invoice = new PayPalInvoiceData();
        // set tax
        invoice.setTax(new BigDecimal("5.00"));
        // set shipping
        invoice.setShipping(new BigDecimal("1.00"));

        Intent checkoutIntent = PayPal.getInstance().checkout(payment, this, new ResultDelegate());
        startActivityForResult(checkoutIntent, 1);
    }

    public void PayPalActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode) {
            // The payment succeeded
            case Activity.RESULT_OK:
                String payKey = intent.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
                this.paymentSucceeded(payKey);
                break;

                // The payment was canceled
            case Activity.RESULT_CANCELED:
                this.paymentCanceled();
                break;

                // The payment failed, get the error from the EXTRA_ERROR_ID and
                // EXTRA_ERROR_MESSAGE
            case PayPalActivity.RESULT_FAILURE:
                String errorID = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
                String errorMessage = intent.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
                this.paymentFailed(errorID, errorMessage);
        }
    }

    private void paymentFailed(String errorID, String errorMessage) {
        Toast.makeText(getApplicationContext(), errorID + " : " + errorMessage , Toast.LENGTH_LONG).show();
    }

    private void paymentCanceled() {
        Toast.makeText(getApplicationContext(), "paymentCanceled", Toast.LENGTH_LONG).show();
    }

    private void paymentSucceeded(String payKey) {
        Toast.makeText(getApplicationContext(), payKey, Toast.LENGTH_LONG).show();
    }
}
