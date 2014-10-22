package com.lunextelecom.zippie.fragment;

import android.os.Bundle;
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
import com.lunextelecom.zippie.utils.Utils;


// TODO: Auto-generated Javadoc
/**
 * The Class SignupVerifyCodeFragment.
 */
public class SignupVerifyCodeFragment extends Fragment implements OnClickListener {
	
	/** The m back image view. */
	private ImageView mBackImageView;
	
	/** The m title text view. */
	private TextView mTitleTextView;
	
	/** The m text view. */
	private TextView mTextView;
	
	/** The m phone text view. */
	private TextView mPhoneTextView;
	
	/** The m code edit text. */
	private EditText mCodeEditText;
	
	/** The m verify button. */
	private Button mVerifyButton;
	
	/** The m resend code text view. */
	private TextView mResendCodeTextView;
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.signup_verify_code_lay, null);
		mBackImageView = (ImageView)view.findViewById(R.id.signup_header_verify_back_id);
		mTitleTextView = (TextView)view.findViewById(R.id.signup_header_verify_title_id);
		mTitleTextView.setText(R.string.signup_title_verify_screen_str);
		mTextView      = (TextView)view.findViewById(R.id.signup_show_text_id);
		mPhoneTextView = (TextView)view.findViewById(R.id.signup_phone_verify_id);
		mCodeEditText  = (EditText)view.findViewById(R.id.signup_verify_code_id);
		mVerifyButton  = (Button)view.findViewById(R.id.signup_button_continue_id);
		mResendCodeTextView = (TextView)view.findViewById(R.id.signup_resend_code_text_id);
		Utils.setTypefaceRoboto(getActivity(), mTitleTextView, mTextView, mPhoneTextView, mCodeEditText, mVerifyButton, mResendCodeTextView);
		Bundle args = getArguments();
		if(args != null){
			String phone_number = args.getString("phonenumber");
			mPhoneTextView.setText(phone_number);
		}
		
		mBackImageView.setOnClickListener(this);
		mVerifyButton.setOnClickListener(this);
		
		return view;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.signup_header_verify_back_id:
			FragmentManager fm = getActivity().getSupportFragmentManager();
		    fm.popBackStack();
			break;
		case R.id.signup_button_continue_id:
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.fragment_container,new CreateProfileFragment());
			transaction.addToBackStack(null);
			transaction.commit();
			break;
		}
	}
}
