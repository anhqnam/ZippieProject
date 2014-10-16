package com.lunextelecom.zippie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.lunextelecom.zippie.R;

// TODO: Auto-generated Javadoc
/**
 * The Class WelcomeScreenFragment.
 */
public class WelcomeScreenFragment extends Fragment {

	/** The m action bar. */
	private ActionBar mActionBar;

	/** The m button start press. */
	private ImageView mImageView, mButtonStart, mButtonStartPress;

	/**
	 * Find view by id.
	 *
	 * @param imagestart the imagestart
	 * @return the image view
	 */
	private ImageView findViewById(int imagestart) {
		// TODO Auto-generated method stub
		return null;
	}

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
		View view = inflater.inflate(R.layout.welcome_screen, null);
		mButtonStart = (ImageView)view.findViewById(R.id.btnStart);
		mButtonStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				transaction.replace(R.id.fragment_container, new InputPhoneNumberFragment());
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		mImageView = (ImageView) view.findViewById(R.id.imageStart);
		mActionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
		mActionBar.hide();

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
