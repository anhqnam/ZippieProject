package com.example.sdktest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    //private final String TAG = "Lunex";
    //private IRegistrationWrapper mRegistrationWrapper;

    //private String mActivationCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_lay);

        findViewById(R.id.main_openSignUp_id).setOnClickListener(this);
        findViewById(R.id.main_openContact_id).setOnClickListener(this);
        findViewById(R.id.main_openSetting_id).setOnClickListener(this);
        findViewById(R.id.main_openAvatar_id).setOnClickListener(this);
        findViewById(R.id.main_openCallslog_id).setOnClickListener(this);

        /*		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new NumberActionFragment()).commit();
		}*/

        //setupRegistrationWrapper();

        //example usage of registration wrapper
        //		String number = "123456";
        //		String cc = "44";
        //		mRegistrationWrapper.registerWithSms(number, cc);
        //		String activationCode = "4444";
        //		mRegistrationWrapper.activate(number, cc, activationCode);
        /*		findViewById(R.id.okClick).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mActivationCode = ((EditText)findViewById(R.id.input)).getText().toString();
				if(!mActivationCode.equalsIgnoreCase("") && mRegistrationWrapper != null)
				{
					String number = "922012350";
					String cc = "84";
					mRegistrationWrapper.activate(number, cc, mActivationCode);
				}
			}
		});

		findViewById(R.id.loginClick).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String number = "922012350";
				String cc = "84";
				mRegistrationWrapper.loginWithoutPassword(number, cc);
			}
		});*/

    }

    /*	protected void setupRegistrationWrapper() {
		unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider numberStateProvider = new unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider() {

			@Override
			public void onUnknownError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onUnknownError");
			}

			@Override
			public void onServerInternalError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onServerInternalError");
			}

			@Override
			public void onConnectionError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onConnectionError");
			}

			@Override
			public void onBadlyDeactivatedAccount() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onBadlyDeactivatedAccount");
			}

			@Override
			public void onWrongNumberSmsSentError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onWrongNumberSmsSentError");
			}

			@Override
			public void onWrongAuthorizationCode() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onWrongAuthorizationCode");
			}

			@Override
			public void onTooManySmsRequests() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onTooManySmsRequests");
			}

			@Override
			public void onTooManyAuthorizationAttempts() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onTooManyAuthorizationAttempts");
			}

			@Override
			public void onSmsState(Boolean sent) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onSmsState : sent" + sent);
			}

			@Override
			public void onSmsPending() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onSmsPending");
			}

			@Override
			public void onNeedPassword() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onNeedPassword");
			}

			@Override
			public void onFeedbackSent(boolean b) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onFeedbackSent");
			}

			@Override
			public void onCallbackUsed() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onCallbackUsed");
			}

			@Override
			public void onCallbackStarted() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onCallbackStarted");
			}

			@Override
			public void onBlacklistedNumberSmsSentError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onBlacklistedNumberSmsSentError");
			}

			@Override
			public void onApiErrorSmsSentError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onApiErrorSmsSentError");
			}

			@Override
			public void onAlreadyExistNumberError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onAlreadyExistNumberError");
			}

			@Override
			public void onAlreadyAssignedNumberError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onAlreadyAssignedNumberError");
			}

			@Override
			public void onAccountAlreadyExists(String login, String password,
					String countryCode, String rawNumber) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onAccountAlreadyExists: login: " + login + " ,password: " + password +
						" ,countryCode: " + countryCode + " ,rawNumber" + rawNumber);
			}

			@Override
			public void onAccountActivationError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onAccountActivationError");
			}

			@Override
			public void onAccountActivated(String login, String password,
					String countryCode, String rawNumber) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onAccountActivated: login: " + login + " ,password: " + password +
						" ,countryCode: " + countryCode + " ,rawNumber" + rawNumber);
			}
		};

		unique.packagename.sdkwrapper.registration.id.IRegistrationStateProvider idStateProvider = new IRegistrationStateProvider() {

			@Override
			public void onUnknownError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onUnknownError");
			}

			@Override
			public void onServerInternalError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onServerInternalError");
			}

			@Override
			public void onConnectionError() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onConnectionError");
			}

			@Override
			public void onBadlyDeactivatedAccount() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onBadlyDeactivatedAccount");
			}

			@Override
			public void onWrongUserPass() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onWrongUserPass");
			}

			@Override
			public void onWrongEmail(String email) {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onWrongEmail: " + email);
			}

			@Override
			public void onWaitingForActivation(String vippieId, String pass) {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onWaitingForActivation: vippieId: " + vippieId + " ,pass: " + pass);
			}

			@Override
			public void onUsedVippieId(String vippieId) {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onUsedVippieId vippieId: " + vippieId);
			}

			@Override
			public void onUsedEmail(String email) {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onUsedEmail: " + email);
			}

			@Override
			public void onTooManyDevices() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onTooManyDevices");
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onStart");
			}

			@Override
			public void onNotActivated() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onNotActivated");
			}

			@Override
			public void onEnd() {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onEnd");
			}

			@Override
			public void onAccount(String login, String password, String countryCode,
					String vippieId) {
				// TODO Auto-generated method stub
				Log.d(TAG, "id.IRegistrationStateProvider: onAccount: login: " + login + " ,countryCode: " + countryCode + " ,vippieId: " + vippieId);
			}
		};

		//mRegistrationWrapper = new RegistrationWrapper(this, idStateProvider, numberStateProvider);

		//mRegistrationWrapper.changePhoneNumberWithSms("1234", "44");
		//testInProgress();
	}

	private void testInProgress()
	{
		String number = "922012350";
		String cc = "84";
		//mRegistrationWrapper.registerWithSms(number, cc);

	}*/

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.main_openSignUp_id:
                i.setClass(getApplicationContext(), SignUpActivity.class);
                break;
            case R.id.main_openContact_id:
                i.setClass(getApplicationContext(), ContactActivity.class);
                break;
            case R.id.main_openSetting_id:
                i.setClass(getApplicationContext(), SettingActivity.class);
                break;
            case R.id.main_openAvatar_id:
                i.setClass(getApplicationContext(), AvatarActivity.class);
                break;
            case R.id.main_openCallslog_id:
                i.setClass(getApplicationContext(), CallsLogActivity.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }
}
