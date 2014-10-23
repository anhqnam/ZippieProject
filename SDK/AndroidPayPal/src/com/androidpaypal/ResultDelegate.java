
package com.androidpaypal;

import java.io.Serializable;

import android.util.Log;

import com.paypal.android.MEP.PayPalResultDelegate;

public class ResultDelegate implements PayPalResultDelegate, Serializable {

    private static final long serialVersionUID = 10001L;

    @Override
    public void onPaymentCanceled(String arg0) {
        MainActivity.resultTitle = "CANCELED";
        MainActivity.resultInfo = "The transaction has been cancelled.";
        MainActivity.resultExtra = "";
        MainActivity.back = true;
    }

    @Override
    public void onPaymentFailed(String paymentStatus, String correlationID, String payKey, String errorID, String errorMessage) {
        MainActivity.resultTitle = "FAILURE";
        MainActivity.resultInfo = errorMessage;
        MainActivity.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: "
                + correlationID + "\nPay Key: " + payKey;
        MainActivity.back=true;
    }

    @Override
    public void onPaymentSucceeded(String payKey, String paymentStatus) {
        Log.i("Result Delegate", "Inside opPaymentSucceedde");
        MainActivity.resultTitle = "SUCCESS";
        MainActivity.resultInfo = "You have successfully completed your transaction.";
        MainActivity.resultExtra = "Key: " + payKey;
        MainActivity.back = true;
    }

}
