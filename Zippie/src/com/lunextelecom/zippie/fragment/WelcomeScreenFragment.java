package com.lunextelecom.zippie.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.utils.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class WelcomeScreenFragment.
 */
public class WelcomeScreenFragment extends Fragment {

	/** The m image view. */
	private ImageView mImageView;

	/** The m button start. */
	private ImageView mButtonStart;

	/** The m slogan text view. */
	private TextView mSloganTextView;

	/** The m start text view. */
	private TextView mStartTextView;

	/** The m privacy text view. */
	private TextView mTextView, mPrivacyTextView;

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
		View view = inflater.inflate(R.layout.sigup_welcome_screen_lay, null);
		mSloganTextView = (TextView) view.findViewById(R.id.signup_welcome_slogan_id);
		mStartTextView = (TextView) view.findViewById(R.id.signup_welcome_text_start_id);
		mTextView = (TextView) view.findViewById(R.id.signup_welcome_text_id);
		mPrivacyTextView = (TextView) view.findViewById(R.id.signup_welcome_text_privacy_id);
		
		String htmlString=" &nbsp; <b><u>Privacy Policy</u></b>";
		mPrivacyTextView.setText(Html.fromHtml(htmlString));
		Utils.setTypefaceRoboto(getActivity(), mSloganTextView, mStartTextView,mTextView,
				mPrivacyTextView);

		mButtonStart = (ImageView) view.findViewById(R.id.signup_welcome_button_start_id);
		mButtonStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.fragment_container,
						new SignupInputPhoneNumberFragment());
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		mImageView = (ImageView) view.findViewById(R.id.signup_welcome_background_start_id);

		// Step1 : create the RotateAnimation object
		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// Step 2: Set the Animation properties
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(100000);

		// Step 3: Start animating the image
		mImageView.startAnimation(anim);
		return view;
	}
}
