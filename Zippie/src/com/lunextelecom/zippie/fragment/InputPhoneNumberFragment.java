package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputPhoneNumberFragment.
 */
public class InputPhoneNumberFragment extends Fragment {

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

	/** The m action bar. */
	private ActionBar mActionBar;
	
	/** The m title text view. */
	private TextView mTitleTextView;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//mActionBar.setTitle(R.string.title_input_number);
		/*SpannableString s = new SpannableString(getResources().getString(
				R.string.title_input_number));
		s.setSpan(new TypefaceSpan("fonts/Roboto.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mActionBar = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		mActionBar.show();
		mActionBar.setTitle(s);*/
		View view = inflater.inflate(R.layout.input_phone_number, null);
		mTitleTextView = (TextView)view.findViewById(R.id.txtTitle);
		mTitleTextView.setText(R.string.title_input_number);
		mShowTextView = (TextView) view.findViewById(R.id.txtTextView);
		mCountryTextView = (TextView) view.findViewById(R.id.txtCountry);
		mFlagImageView = (ImageView) view.findViewById(R.id.imgFlag);
		mNumberEditText = (EditText) view.findViewById(R.id.edtNumber);
		mContinueButton = (Button) view.findViewById(R.id.btnContinue);
		Utils.setTypefaceRoboto(getActivity(), mShowTextView, mCountryTextView,
				mNumberEditText, mFlagImageView, mContinueButton);

		return view;
	}
}
