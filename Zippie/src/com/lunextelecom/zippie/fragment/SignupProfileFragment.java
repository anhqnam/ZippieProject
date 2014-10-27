/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.activity.SignUpActivity;
import com.lunextelecom.zippie.utils.Utils;
import com.lunextelecom.zippie.view.CircularImageView;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateProfileFragment.
 */
public class SignupProfileFragment extends Fragment implements OnClickListener{

    /** The m face ui helper. */
    private UiLifecycleHelper mFaceUiHelper;

    /** The m create profile name et. */
    private EditText mCreateProfileNameEt;

    /** The m create profile email et. */
    private EditText mCreateProfileEmailEt;

    /** The m create profile avatar civ. */
    private CircularImageView mCreateProfileAvatarCiv;

    /** The m face callback. */
    private Session.StatusCallback mFaceCallback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    /** The dialog callback. */
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

	private SignUpActivity mSignUpActivity;

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mFaceUiHelper = new UiLifecycleHelper(getActivity(), mFaceCallback);
        mFaceUiHelper.onCreate(savedInstanceState);
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //get view
        View view = inflater.inflate(R.layout.signup_create_profile_lay, null);
        initView(view);
        return view;
    }
    
    private void initView(View view)
    {
        TextView createProfileTitleTv = (TextView)view.findViewById(R.id.signup_header_text_id);
        createProfileTitleTv.setText(getResources().getString(R.string.createprofile_action_bar_title_str));
        View createProfileButtonFaceView = view.findViewById(R.id.createprofile_button_face_id);
        TextView createProfileLogoFaceTextTv = (TextView)view.findViewById(R.id.createprofile_logo_face_text_id);
        TextView createProfileTextTaceTv = (TextView)view.findViewById(R.id.createprofile_text_face_id);
        TextView createProfileOrTv = (TextView)view.findViewById(R.id.createprofile_or_id);
        mCreateProfileAvatarCiv = (CircularImageView)view.findViewById(R.id.createprofile_avatar_id);
        TextView createProfileAddPhotoTv = (TextView)view.findViewById(R.id.createprofile_add_photo_id);
        mCreateProfileNameEt = (EditText)view.findViewById(R.id.createprofile_name_id);
        mCreateProfileEmailEt = (EditText)view.findViewById(R.id.createprofile_email_id);
        Button createProfileFinishBtn = (Button)view.findViewById(R.id.createprofile_finish_id);

        //set font
        Utils.setTypeface(getActivity(), Utils.FONT_FACEBOLF_NAME, createProfileLogoFaceTextTv);
        Utils.setTypefaceRoboto(getActivity(), createProfileTextTaceTv, createProfileOrTv, createProfileAddPhotoTv,
                mCreateProfileNameEt, mCreateProfileEmailEt, createProfileFinishBtn, createProfileTitleTv);

        //set on click listener
        createProfileButtonFaceView.setOnClickListener(this);
        createProfileAddPhotoTv.setOnClickListener(this);
        createProfileFinishBtn.setOnClickListener(this);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            //on click button facebook
            case R.id.createprofile_button_face_id:
                onClickLoginFace();
                break;
                //on click link add photo
            case R.id.createprofile_add_photo_id:
                break;
                //on click button finish
            case R.id.createprofile_finish_id:
            {
            	if(mSignUpActivity != null)
            	{
            		mSignUpActivity.startIntroduceFragment();
            	}
            	break;
            }
            default:
                break;
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mFaceUiHelper.onResume();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFaceUiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mFaceUiHelper.onPause();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mFaceUiHelper.onDestroy();
    }

    /**
     * On click login face.
     */
    private void onClickLoginFace() {
    	if(mSignUpActivity != null)
    	{
            Session session = Session.getActiveSession();
            if (!session.isOpened() && !session.isClosed()) {
                session.openForRead(new Session.OpenRequest(mSignUpActivity)
                .setPermissions(Arrays.asList("public_profile"))
                .setCallback(mFaceCallback));
            } else {
                Session.openActiveSession(getActivity(), true, mFaceCallback);
            }
    	}
    }

    /**
     * On session state change.
     * 
     * @param session the session
     * @param state the state
     * @param exception the exception
     */
    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (session != null && session.isOpened()) {
            // If the session is open, make an API call to get user data
            // and define a new callback to handle the response
            Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    // If the response is successful
                    if (session == Session.getActiveSession()) {
                        if(user != null){
                            mCreateProfileNameEt.setText(user.getName());
                            mCreateProfileEmailEt.setText(user.getProperty("email").toString());
                            getFacebookProfilePicture(user.getId());
                            session.closeAndClearTokenInformation();
                        }
                    }
                }
            });
            Request.executeBatchAsync(request);
        }
    }

    /**
     * Gets the facebook profile picture.
     * 
     * @param userID the user id
     * @return the facebook profile picture
     */
    private void getFacebookProfilePicture(String userID){
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {
                // TODO Auto-generated method stub
                URL imageURL;
                try {
                    imageURL = new URL("https://graph.facebook.com/" + params[0] + "/picture?type=large");
                    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    return bitmap;
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(final Bitmap result) {
                if(result != null){
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mCreateProfileAvatarCiv.setImageBitmap(result);
                        }
                    });
                }
            };
        }.execute(userID);

    }
    
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mSignUpActivity = (SignUpActivity) activity;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Zippie", getClass().getName()
					+ " must implement mSignUpActivity.startSplashFragment();");
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onDetach()
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		mSignUpActivity = null;
		super.onDetach();
	}
}
