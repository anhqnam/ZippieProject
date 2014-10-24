package com.paypalcustom;

import java.io.Serializable;

import com.paypal.android.MEP.PayPalResultDelegate;

public class ResultDelegate implements Serializable, PayPalResultDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10001L;

	@Override
	public void onPaymentCanceled(String paymentStatus) {
		
	}

	@Override
	public void onPaymentFailed(String paymentStatus, String correlationID,
			String payKey, String errorID, String errorMessage) {
		
	}

	@Override
	public void onPaymentSucceeded(String payKey, String paymentStatus) {
		
	}

}
