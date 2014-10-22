/*
 * 
 */

package com.testpaypal;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity {

    /** The Constant TAG. */
    private static final String TAG = "TestPayPal";

    /** The m btn_buy. */
    private Button mBtn_buy;

    /** The m btn_payment_consent. */
    private Button mBtn_payment_consent;

    /** The m btn_payment_purchase. */
    private Button mBtn_payment_purchase;

    /** The m btn_profile_share. */
    private Button mBtn_profile_share;

    /** The Constant CONFIG_ENVIRONMENT. */
    private static final String mCONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    /** The Constant CONFIG_CLIENT_ID. */
    private static final String mCONFIG_CLIENT_ID = "credential from developer.paypal.com";

    /** The Constant REQUEST_CODE_PAYMENT. */
    private static final int mREQUEST_CODE_PAYMENT = 1;

    /** The Constant REQUEST_CODE_FUTURE_PAYMENT. */
    private static final int mREQUEST_CODE_FUTURE_PAYMENT = 2;

    /** The Constant REQUEST_CODE_PROFILE_SHARING. */
    private static final int mREQUEST_CODE_PROFILE_SHARING = 3;

    /** The config. */
    private static PayPalConfiguration mConfig = new PayPalConfiguration()
    .environment(mCONFIG_ENVIRONMENT)
    .clientId(mCONFIG_CLIENT_ID)
    // The following are only used in PayPalFuturePaymentActivity.
    .merchantName("MyStore")
    .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
    .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    /*
     * (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, mConfig);
        startService(intent);

        mBtn_buy = (Button)findViewById(R.id.main_btn_buything_id);
        mBtn_payment_consent = (Button)findViewById(R.id.main_future_payment_consent_id);
        mBtn_payment_purchase = (Button)findViewById(R.id.main_future_payment_purchase_id);
        mBtn_profile_share = (Button)findViewById(R.id.main_profile_share_id);

        // button buy
        mBtn_buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                PayPalPayment buything1 = getbuythings(PayPalPayment.PAYMENT_INTENT_SALE);
                PayPalPayment buything2 = getStufftobuy(PayPalPayment.PAYMENT_INTENT_SALE);
                addAppProvidedShippingAddress(buything2);
                enableShippingAddressRetrieval(buything2, true);
                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, buything1);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, buything2);
                startActivityForResult(intent, mREQUEST_CODE_PAYMENT);
            }
        });

        // button payment Consent
        mBtn_payment_consent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, PayPalFuturePaymentActivity.class);
                startActivityForResult(intent, mREQUEST_CODE_FUTURE_PAYMENT);
            }
        });

        // button payment Purchase
        mBtn_payment_purchase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String correlationId = PayPalConfiguration
                        .getApplicationCorrelationId(MainActivity.this);
                Log.i("FuturePaymentExample", "Application Correlation ID: " + correlationId);
                Toast.makeText(getApplicationContext(), correlationId, Toast.LENGTH_LONG).show();
            }
        });

        // button payment share profile
        mBtn_profile_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, PayPalProfileSharingActivity.class);
                intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES,
                        getOauthScopes());
                startActivityForResult(intent, mREQUEST_CODE_PROFILE_SHARING);
            }
        });

    }

    /**
     * Gets the buythings.
     * 
     * @param paymentIntent the payment intent
     * @return the buythings
     */
    private PayPalPayment getbuythings(String paymentIntent) {
        return new PayPalPayment(new BigDecimal("5.49"), "USD", "Watch", paymentIntent);
    }

    /**
     * Gets the stufftobuy.
     * 
     * @param paymentIntent the payment intent
     * @return the stufftobuy
     */
    private PayPalPayment getStufftobuy(String paymentIntent) {

        PayPalItem[] items = {
                new PayPalItem("item 1", 1, new BigDecimal("1.75"), "USD", "123"),
                new PayPalItem("item 2", 1, new BigDecimal("3.99"), "USD", "456"),
                new PayPalItem("item 2", 1, new BigDecimal("2.99"), "USD", "789")
        };
        BigDecimal subtotal = PayPalItem.getItemTotal(items);
        BigDecimal shipping = new BigDecimal("7.21");
        BigDecimal tax = new BigDecimal("4.67");
        PayPalPaymentDetails details = new PayPalPaymentDetails(shipping, subtotal, tax);
        BigDecimal amount = subtotal.add(shipping).add(tax);

        PayPalPayment payment = new PayPalPayment(amount, "USD", "Product", paymentIntent);
        payment.items(items).paymentDetails(details);

        // --- set other optional fields like invoice_number, custom field, and
        // soft_descriptor
        payment.custom("This is text that will be associated with the payment that the app can use.");
        return payment;
    }

    /**
     * Gets the oauth scopes.
     * 
     * @return the oauth scopes
     */
    private PayPalOAuthScopes getOauthScopes() {
        Set<String> scopes = new HashSet<String>(Arrays.asList(
                PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS));
        return new PayPalOAuthScopes(scopes);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == mREQUEST_CODE_PAYMENT) {
            if (resultcode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
                        Toast.makeText(getApplicationContext(),
                                "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultcode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultcode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestcode == mREQUEST_CODE_FUTURE_PAYMENT) {
            if (resultcode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultcode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultcode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestcode == mREQUEST_CODE_PROFILE_SHARING) {
            if (resultcode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Profile Sharing code received from PayPal", Toast.LENGTH_LONG)
                                .show();

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ",
                                e);
                    }
                }
            } else if (resultcode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultcode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }

        super.onActivityResult(requestcode, resultcode, data);
    }

    /**
     * Enable shipping address retrieval.
     * 
     * @param paypalPayment the paypal payment
     * @param enable the enable
     */

    private void enableShippingAddressRetrieval(PayPalPayment paypalPayment, boolean enable) {
        paypalPayment.enablePayPalShippingAddressesRetrieval(enable);
    }

    /**
     * Adds the app provided shipping address.
     * 
     * @param paypalPayment the paypal payment
     */

    private void addAppProvidedShippingAddress(PayPalPayment paypalPayment) {
        ShippingAddress shippingAddress =
                new ShippingAddress().recipientName("Nam Nguyen").line1("5 Nguyen Giai Thieu st.")
                .city("HCM").state("HCM").postalCode("70000").countryCode("VN");


        paypalPayment.providedShippingAddress(shippingAddress);
    }
    /**
     * Send authorization to server.
     * 
     * @param authorization the authorization
     */
    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
        Log.d(TAG, "sendAuthorizationToServer");
    }
}
