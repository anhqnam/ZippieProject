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

import java.io.Serializable;

import com.paypal.android.MEP.PayPalResultDelegate;

public class ResultDelegate implements PayPalResultDelegate, Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 10001L;

    @Override
    public void onPaymentCanceled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPaymentFailed(String arg0, String arg1, String arg2, String arg3, String arg4) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPaymentSucceeded(String arg0, String arg1) {
        // TODO Auto-generated method stub

    }

}
