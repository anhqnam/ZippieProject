package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.bean.CountryObject;
import com.lunextelecom.zippie.dialog.CustomCountryDialog;
import com.lunextelecom.zippie.dialog.CustomCountryDialog.CountryDialogListener;
import com.lunextelecom.zippie.utils.CountryHelper;
import com.lunextelecom.zippie.utils.CountryHelper.ConnectionGPSSuccess;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputPhoneNumberFragment.
 */
public class SignupInputPhoneNumberFragment extends Fragment implements
		ConnectionGPSSuccess, CountryDialogListener {

	/** The m show text view. */
	private TextView mShowTextView;

	/** The m country text view. */
	private TextView mCountryTextView;

	/** The m flag image view. */
	private ImageView mFlagImageView;

	/** The m number edit text. */
	private EditText mNumberEditText;

	/** The m continue button. */
	private Button mContinueButton;

	/** The m title text view. */
	private TextView mTitleTextView;

	/** The m country helper. */
	private CountryHelper mCountryHelper;

	/** The m list country. */
	private List<CountryObject> mListCountry;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCountryHelper = new CountryHelper(getActivity(), this);
		mListCountry = CountryHelper.getListCountryFromJsonFile(getActivity()
				.getAssets());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onStart()
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mCountryHelper.connectGPS();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.signup_input_phone_number, null);

		mTitleTextView = (TextView) view.findViewById(R.id.txtTitle);
		mTitleTextView.setText(R.string.signup_title_input_number_str);
		mShowTextView = (TextView) view.findViewById(R.id.signup_textshow_id);
		mCountryTextView = (TextView) view.findViewById(R.id.signup_country_name_textview_id);
		mCountryTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getFragmentManager();
				ArrayList<String> mlistItemFragment = getListCountry(mListCountry);
				CustomCountryDialog mFragmentDialog = CustomCountryDialog
						.newInstance("Select Country", mlistItemFragment);
				mFragmentDialog.setOnItemCountry(SignupInputPhoneNumberFragment.this);
				mFragmentDialog.show(fm, "fragment_edit_name");
			}
		});
		mFlagImageView = (ImageView) view.findViewById(R.id.signup_image_flag_id);
		mNumberEditText = (EditText) view.findViewById(R.id.signup_phone_number_edittext_id);
		mContinueButton = (Button) view.findViewById(R.id.signup_button_continue_id);
		mContinueButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container,
						new SignupVerifyCodeFragment());
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		Utils.setTypefaceRoboto(getActivity(), mShowTextView, mCountryTextView,
				mNumberEditText, mFlagImageView, mContinueButton);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lunextelecom.zippie.utils.CountryHelper.ConnectionGPSSuccess#
	 * connectSuccess(java.lang.String)
	 */
	@Override
	public void connectSuccess(String countryCode) {
		// TODO Auto-generated method stub
		CountryObject country = CountryHelper.getCountryObject(countryCode,
				mListCountry);
		getInfoCountry(country);
	}

	/**
	 * Gets the list country.
	 * 
	 * @param list
	 *            the list
	 * @return the list country
	 */
	public ArrayList<String> getListCountry(List<CountryObject> list) {
		ArrayList<String> results = new ArrayList<String>();
		if (list != null) {
			for (CountryObject ob : list) {
				String name = ob.getmName() + " (" + ob.getmDialCode() + ")";
				results.add(name);
			}
		}
		return results;
	}

	/**
	 * Gets the info country.
	 * 
	 * @param object
	 *            the object
	 * @return the info country
	 */
	public void getInfoCountry(CountryObject object) {
		mCountryTextView.setText(object.getmName());
		mFlagImageView.setImageDrawable(CountryHelper.getDrawableFlagCountry(
				getActivity().getAssets(), object.getmCode()));
		mNumberEditText.setText(object.getmDialCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lunextelecom.zippie.dialog.CustomCountryDialog.CountryDialogListener
	 * #callbackCountryDialog(int, java.lang.String)
	 */
	@Override
	public void callbackCountryDialog(String dialCode) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), dialCode, Toast.LENGTH_LONG).show();
		CountryObject country = CountryHelper.getCountryObjectByDialCode(dialCode, mListCountry);
		getInfoCountry(country);
	}
}
