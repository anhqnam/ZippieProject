package com.paypalcustom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalPayment;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements OnClickListener,
		OnItemSelectedListener, OnMultiChoiceClickListener, OnDismissListener {

	/** The Constant SIZE_SMALL. */
	final static public int SIZE_SMALL = 0;

	/** The Constant SIZE_MEDIUM. */
	final static public int SIZE_MEDIUM = 1;

	/** The Constant SIZE_LARGE. */
	final static public int SIZE_LARGE = 2;

	/** The Constant METHOD_PICKUP. */
	final static public int METHOD_PICKUP = 0;

	/** The Constant METHOD_DELIVERY. */
	final static public int METHOD_DELIVERY = 1;

	/** The _size. */
	private int _size = SIZE_SMALL;

	/** The _number. */
	private int _number = 1;

	/** The _method. */
	private int _method = METHOD_PICKUP;

	// Local references to our amounts and description
	/** The _the subtotal. */
	private double _theSubtotal;

	/** The _tax amount. */
	private double _taxAmount;

	/** The _pizza description. */
	private String _pizzaDescription;

	/** The _size str. */
	private String _sizeStr;

	/** The _price. */
	String _price;
	// All the booleans we will use for toppings
	/** The _cheese. */
	private boolean _cheese = false;

	/** The _pepperoni. */
	private boolean _pepperoni = false;

	/** The _mushrooms. */
	private boolean _mushrooms = false;

	/** The _onions. */
	private boolean _onions = false;

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
	// Keeps a reference to the progress dialog
	/** The _progress dialog. */
	private ProgressDialog _progressDialog;

	/** The _paypal library init. */
	private boolean _paypalLibraryInit = false;

	/** The _progress dialog running. */
	private boolean _progressDialogRunning = false;

	// method to check if the device is connected to network
	/**
	 * Checks if is online.
	 * 
	 * @return true, if is online
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (this.isOnline()) {
			Thread libraryInitializationThread = new Thread() {
				public void run() {
					initLibrary();
				}
			};
			libraryInitializationThread.start();
		}

		loadMainPage();
	}

	/**
	 * Load main page.
	 */
	private void loadMainPage() {
		setContentView(R.layout.activity_main);
		findViewById(R.id.Buy).setOnClickListener(this);
	}

	/**
	 * Inits the library.
	 */
	private void initLibrary() {
		PayPal pp = PayPal.getInstance();
		if (pp == null) {
			pp = PayPal.initWithAppID(this, "APP-80W284485P519543T",
					PayPal.ENV_SANDBOX);
			pp.setLanguage("en_US");
			pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER);
			pp.setShippingEnabled(true);
			pp.setDynamicAmountCalculationEnabled(false);
			_paypalLibraryInit = true;
		}
	}

	/**
	 * Load customize page.
	 */
	public void loadCustomizePage() {
		setContentView(R.layout.customize);

		Spinner sizeSpinner = (Spinner) findViewById(R.id.Spinner01);
		sizeSpinner.setPrompt("Select a size");
		ArrayAdapter<CharSequence> sizeAdapter;
		sizeAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		sizeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sizeAdapter.add("Small ($7.99)");
		sizeAdapter.add("Medium ($9.99)");
		sizeAdapter.add("Large ($11.99)");
		sizeSpinner.setAdapter(sizeAdapter);
		sizeSpinner.setOnItemSelectedListener(this);
		sizeSpinner.setSelection(_size);

		findViewById(R.id.Button03).setOnClickListener(this);
		findViewById(R.id.Continue).setOnClickListener(this);
	}

	/**
	 * Load finalize page.
	 */
	public void loadFinalizePage() {
		setContentView(R.layout.finalize);

		// Set up the finalize page
		Spinner numberSpinner = (Spinner) findViewById(R.id.Spinner02);
		numberSpinner.setPrompt("Select number of pizzas");
		ArrayAdapter<CharSequence> numberAdapter;
		numberAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		numberAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numberAdapter.add("One");
		numberAdapter.add("Two");
		numberAdapter.add("Three");
		numberSpinner.setAdapter(numberAdapter);
		numberSpinner.setOnItemSelectedListener(this);
		numberSpinner.setSelection(_number - 1);

		Spinner methodSpinner = (Spinner) findViewById(R.id.Spinner03);
		methodSpinner.setPrompt("Select method");
		ArrayAdapter<CharSequence> methodAdapter;
		methodAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_item);
		methodAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		methodAdapter.add("Pickup");
		methodAdapter.add("Delivery");
		methodSpinner.setAdapter(methodAdapter);
		methodSpinner.setOnItemSelectedListener(this);
		methodSpinner.setSelection(_method);

		findViewById(R.id.ReviewOrder).setOnClickListener(this);
	}

	/**
	 * Load review page.
	 */
	public void loadReviewPage() {
		setContentView(R.layout.review);
		_df = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.ENGLISH);
		_df.setCurrency(Currency.getInstance("USD"));
		_sizeStr = "Small";
		switch (_size) {
		case SIZE_SMALL:
			_sizeStr = "Small";
			break;
		case SIZE_MEDIUM:
			_sizeStr = "Medium";
			break;
		case SIZE_LARGE:
			_sizeStr = "Large";
			break;
		}
		_pizzaDescription = _number + " " + _sizeStr + " Pizzas";
		if (_number == 1) {
			_pizzaDescription = _number + " " + _sizeStr + " Pizza";
		}
		((TextView) findViewById(R.id.NumberOfPizzas))
				.setText(_pizzaDescription);
		double priceAmount = 7.99;
		_price = "7.99";
		if (_number == 1) {
			if (_size == SIZE_SMALL) {
				_price = "7.99";
				priceAmount = 7.99;
			} else if (_size == SIZE_MEDIUM) {
				_price = "9.99";
				priceAmount = 9.99;
			} else {
				_price = "11.99";
				priceAmount = 11.99;
			}
		}
		if (_number == 2) {
			if (_size == SIZE_SMALL) {
				_price = "9.99";
				priceAmount = 9.99;
			} else if (_size == SIZE_MEDIUM) {
				_price = "15.99";
				priceAmount = 15.99;
			} else {
				_price = "19.99";
				priceAmount = 19.99;
			}
		} else if (_number == 3) {
			if (_size == SIZE_SMALL) {
				_price = "11.99";
				priceAmount = 11.99;
			} else if (_size == SIZE_MEDIUM) {
				_price = "20.99";
				priceAmount = 20.99;
			} else {
				_price = "25.99";
				priceAmount = 25.99;
			}
		}
		((TextView) findViewById(R.id.Price)).setText("$" + _price);

		if (!_cheese) {
			((TextView) findViewById(R.id.ExtraCheesePrice)).setText("$0.00");
		}
		if (!_pepperoni) {
			((TextView) findViewById(R.id.PepperoniPrice)).setText("$0.00");
		}
		if (!_mushrooms) {
			((TextView) findViewById(R.id.MushroomsPrice)).setText("$0.00");
		}
		if (!_onions) {
			((TextView) findViewById(R.id.OnionsPrice)).setText("$0.00");
		}

		if (_cheese) {
			priceAmount += 2.00;
		}
		if (_pepperoni) {
			priceAmount += 2.00;
		}
		if (_mushrooms) {
			priceAmount += 2.00;
		}
		if (_onions) {
			priceAmount += 2.00;
		}

		_theSubtotal = priceAmount;
		((TextView) findViewById(R.id.SubtotalPrice)).setText(_df
				.format(priceAmount));
		if (_method == METHOD_PICKUP) {
			((TextView) findViewById(R.id.DeliveryFeePrice)).setText("$0.00");
			((TextView) findViewById(R.id.MethodType)).setText("Pick Up");
		} else {
			priceAmount += 2.00;
		}
		// to account for tax
		_taxAmount = priceAmount * .08;
		priceAmount += _taxAmount;
		((TextView) findViewById(R.id.TaxPrice))
				.setText(_df.format(_taxAmount));
		((TextView) findViewById(R.id.TotalPrice)).setText(_df
				.format(priceAmount));
		if (_paypalLibraryInit) {
			showPayPalButton();
		} else {
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			_progressDialog.setMessage("Loading PayPal Payment Library");
			_progressDialog.setCancelable(false);
			_progressDialog.show();
			_progressDialogRunning = true;
			Thread newThread = new Thread(checkforPayPalInitRunnable);
			newThread.start();
		}

	}

	/** The checkfor pay pal init runnable. */
	final Runnable checkforPayPalInitRunnable = new Runnable() {
		public void run() {
			checkForPayPalLibraryInit();
		}
	};

	/**
	 * Check for pay pal library init.
	 */
	private void checkForPayPalLibraryInit() {
		while (_paypalLibraryInit == false) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Error initializing PayPal Library")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		runOnUiThread(showPayPalButtonRunnable);
	}

	/** The show pay pal button runnable. */
	final Runnable showPayPalButtonRunnable = new Runnable() {
		public void run() {
			showPayPalButton();
		}
	};

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
		((RelativeLayout) findViewById(R.id.RelativeLayout01))
				.addView(launchPayPalButton);
		((RelativeLayout) findViewById(R.id.RelativeLayout01))
				.setGravity(Gravity.CENTER_HORIZONTAL);
		if (_progressDialogRunning) {
			_progressDialog.dismiss();
			_progressDialogRunning = false;
		}
	}

	/**
	 * Removes the pay pal button.
	 */
	private void removePayPalButton() {
		if (launchPayPalButton != null) {
			((RelativeLayout) findViewById(R.id.RelativeLayout01))
					.removeView(launchPayPalButton);
		}
	}

	public void loadResultsPage() {
		setContentView(R.layout.results);
		findViewById(R.id.BuyMore).setOnClickListener(this);
		findViewById(R.id.Done).setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.content.DialogInterface.OnDismissListener#onDismiss(android.content
	 * .DialogInterface)
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		findViewById(R.id.Continue).setVisibility(View.VISIBLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.content.DialogInterface.OnMultiChoiceClickListener#onClick(android
	 * .content.DialogInterface, int, boolean)
	 */
	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (which == 0) {
			_cheese = isChecked;
		} else if (which == 1) {
			_pepperoni = isChecked;
		} else if (which == 2) {
			_mushrooms = isChecked;
		} else {
			_onions = isChecked;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent == findViewById(R.id.Spinner01)) {
			_size = position;
		} else if (parent == findViewById(R.id.Spinner02)) {
			_number = position + 1;
		} else if (parent == findViewById(R.id.Spinner03)) {
			_method = position;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android
	 * .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		if (arg0 == (Button) findViewById(R.id.Buy)) {
			loadCustomizePage();
		} else if (arg0 == (Button) findViewById(R.id.Button03)) {
			((Button) findViewById(R.id.Continue)).setVisibility(View.GONE);
			final CharSequence[] items = { "Extra Cheese", "Pepperoni",
					"Mushrooms", "Onions" };

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choose your toppings");
			boolean[] myBools = { _cheese, _pepperoni, _mushrooms, _onions };
			builder.setMultiChoiceItems(items, myBools, this);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			AlertDialog alert = builder.create();
			alert.setOnDismissListener(this);
			alert.show();
		} else if (arg0 == (Button) findViewById(R.id.Continue)) {
			loadFinalizePage();
		} else if (arg0 == (Button) findViewById(R.id.ReviewOrder)) {
			loadReviewPage();
		} else if (arg0 == (Button) findViewById(R.id.BuyMore)) {
			loadMainPage();
		} else if (arg0 == (Button) findViewById(R.id.Done)) {
			finish();
		} else if (arg0 == (CheckoutButton) findViewById(PAYPAL_BUTTON_ID)) {
			PayPalButtonClick(arg0);
		}
	}

	/**
	 * Pay pal button click.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	@SuppressWarnings("deprecation")
	public void PayPalButtonClick(View arg0) {
		PayPalPayment payment = new PayPalPayment();
		payment.setCurrencyType("USD");
		payment.setRecipient("hoangnam.hl2014@gmail.com");
		BigDecimal st = new BigDecimal(_theSubtotal);
		st = st.setScale(2, RoundingMode.HALF_UP);
		payment.setSubtotal(st);
		payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);

		PayPalInvoiceData invoice = new PayPalInvoiceData();
		BigDecimal tax = new BigDecimal(_taxAmount);
		tax = tax.setScale(2, RoundingMode.HALF_UP);
		invoice.setTax(tax);
		if (_method == METHOD_DELIVERY) {
			invoice.setShipping(new BigDecimal("2.00"));
			PayPal.getInstance().setShippingEnabled(true);
		}

		PayPalInvoiceItem item1 = new PayPalInvoiceItem();
		item1.setName("Pizza");
		item1.setID("1234");
		item1.setTotalPrice(new BigDecimal(_price));
		item1.setUnitPrice(new BigDecimal(_price));
		item1.setQuantity(_number);

		invoice.getInvoiceItems().add(item1);
		if (_cheese) {
			PayPalInvoiceItem item2 = new PayPalInvoiceItem();
			item2.setName("Cheese");
			item2.setID("2345");
			item2.setTotalPrice(new BigDecimal("2.00"));
			item2.setUnitPrice(new BigDecimal("2.00"));
			item2.setQuantity(1);
			invoice.getInvoiceItems().add(item2);
		}
		if (_pepperoni) {
			PayPalInvoiceItem item2 = new PayPalInvoiceItem();
			item2.setName("Pepperoni");
			item2.setID("3456");
			item2.setTotalPrice(new BigDecimal("2.00"));
			item2.setUnitPrice(new BigDecimal("2.00"));
			item2.setQuantity(1);
			invoice.getInvoiceItems().add(item2);
		}
		if (_mushrooms) {
			PayPalInvoiceItem item2 = new PayPalInvoiceItem();
			item2.setName("Mushrooms");
			item2.setID("4567");
			item2.setTotalPrice(new BigDecimal("2.00"));
			item2.setUnitPrice(new BigDecimal("2.00"));
			item2.setQuantity(1);
			invoice.getInvoiceItems().add(item2);
		}
		if (_onions) {
			PayPalInvoiceItem item2 = new PayPalInvoiceItem();
			item2.setName("Onions");
			item2.setID("5678");
			item2.setTotalPrice(new BigDecimal("2.00"));
			item2.setUnitPrice(new BigDecimal("2.00"));
			item2.setQuantity(1);
			invoice.getInvoiceItems().add(item2);
		}

		payment.setInvoiceData(invoice);
		payment.setMerchantName("Pizza Express");
		payment.setDescription(_pizzaDescription);

		Intent checkoutIntent = PayPal.getInstance().checkout(payment, this);
		startActivityForResult(checkoutIntent, REQUEST_PAYPAL_CHECKOUT);
	}

	/**
	 * Pay pal activity result.
	 * 
	 * @param requestCode
	 *            the request code
	 * @param resultCode
	 *            the result code
	 * @param intent
	 *            the intent
	 */
	public void PayPalActivityResult(int requestCode, int resultCode,
			Intent intent) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			String payKey = intent.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
			this.paymentSuccessed(payKey);
			break;
		case Activity.RESULT_CANCELED:
			this.paymentCanceled();
			break;
		case PayPalActivity.RESULT_FAILURE:
			String errorID = intent
					.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
			String errorMessage = intent
					.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
			this.paymentFailed(errorID, errorMessage);
		}
	}

	/**
	 * Payment successed.
	 * 
	 * @param payKey
	 *            the pay key
	 */
	public void paymentSuccessed(String payKey) {
		loadResultsPage();
		((TextView) findViewById(R.id.ResultsTitle)).setText("Success!");
		if (_method == METHOD_PICKUP) {
			((TextView) findViewById(R.id.ResultsText1))
					.setText("Your order is being prepared!");
		} else if (_method == METHOD_DELIVERY) {
			((TextView) findViewById(R.id.ResultsText1))
					.setText("Your order is on its way!");
		}
		((TextView) findViewById(R.id.ResultsText2))
				.setText("Estimated time: 30 minutes.");
		((TextView) findViewById(R.id.ResultsText3)).setText("Payment Key: "
				+ payKey);
	}

	/**
	 * Payment failed.
	 * 
	 * @param errorID
	 *            the error id
	 * @param errorMessage
	 *            the error message
	 */
	public void paymentFailed(String errorID, String errorMessage) {
		loadResultsPage();
		((TextView) findViewById(R.id.ResultsTitle)).setText("Failure!");
		((TextView) findViewById(R.id.ResultsText1))
				.setText("We're sorry, but your payment failed.");
		((TextView) findViewById(R.id.ResultsText2)).setText("Error: "
				+ errorMessage);
		((TextView) findViewById(R.id.ResultsText3)).setText("Error ID: "
				+ errorID);
	}

	/**
	 * Payment canceled.
	 */
	public void paymentCanceled() {
		loadResultsPage();
		((TextView) findViewById(R.id.ResultsTitle)).setText("Canceled.");
		((TextView) findViewById(R.id.ResultsText1))
				.setText("Your payment has been canceled.");
		((TextView) findViewById(R.id.ResultsText2))
				.setText("Your account was not charged.");
		((TextView) findViewById(R.id.ResultsText3)).setText("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (requestCode == REQUEST_PAYPAL_CHECKOUT) {
			PayPalActivityResult(requestCode, resultCode, intent);
		} else {
			super.onActivityResult(requestCode, resultCode, intent);
		}
	}
}
