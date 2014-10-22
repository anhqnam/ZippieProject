package com.example.sdktest;

import unique.packagename.sdkwrapper.contacts.ContactsWrapper;
import unique.packagename.sdkwrapper.contacts.IOnSyncContactsListener;
import unique.packagename.sdkwrapper.registration.RegistrationWrapper;
import unique.packagename.sdkwrapper.registration.id.IRegistrationStateProvider;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdktest.api.APIHelper;

public class SignUpActivity extends Activity implements OnClickListener
{
    public final String TAG = "SignUpActivity";
    private String mStateProviderID,mStateProviderNumber;
    private String mCountryCode, mPhoneNumber, mVerifyCode;
    private RegistrationWrapper mRegistrationWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.signup_activity_lay);
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        getInput();
        switch (v.getId()) {
            case R.id.signup_registerSmsClick_id:
                mRegistrationWrapper.registerWithSms(mPhoneNumber,mCountryCode);
                break;
            case R.id.signup_activateSMSClick_id:
                mRegistrationWrapper.activate(mPhoneNumber,mCountryCode, mVerifyCode);
                break;
            case R.id.signup_changePhoneNumberWithSMSClick_id:
                mRegistrationWrapper.changePhoneNumberWithSms(mPhoneNumber, mCountryCode);
                break;
            case R.id.signup_activateChangedNumberClick_id:
                mRegistrationWrapper.activateChangedNumber(mPhoneNumber,mCountryCode, mVerifyCode);
                break;
            case R.id.signup_loginWithoutPasswordClick_id:
                mRegistrationWrapper.loginWithoutPassword(mPhoneNumber, mCountryCode);
                APIHelper contact = APIHelper.getInstance();
                ContactsWrapper a = contact.getContactsWrapper();
                a.addContactsSyncListener(new IOnSyncContactsListener() {
                    @Override
                    public void onSynchronizedContactsChanged(String arg0) {
                        Toast.makeText(getApplication(), "Contacts synchronisator inited", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSipContactsSyncInited() {

                    }
                });
                break;
            default:
                break;
        }
    }

    private void init()
    {
        mRegistrationWrapper = APIHelper.getInstance().getRegistrationWrapper(SignUpActivity.this,
                new RegistrationStateProviderID(), new RegistrationStateProviderNumber());
        findViewById(R.id.signup_registerSmsClick_id).setOnClickListener(this);
        findViewById(R.id.signup_activateSMSClick_id).setOnClickListener(this);
        findViewById(R.id.signup_changePhoneNumberWithSMSClick_id).setOnClickListener(this);
        findViewById(R.id.signup_activateChangedNumberClick_id).setOnClickListener(this);
        findViewById(R.id.signup_loginWithoutPasswordClick_id).setOnClickListener(this);
    }

    private void getInput()
    {
        mCountryCode = ((EditText)findViewById(R.id.signup_et_countrycodestr_id)).getText().toString();
        mPhoneNumber = ((EditText)findViewById(R.id.signup_et_phonestr_id)).getText().toString();
        mVerifyCode = ((EditText)findViewById(R.id.signup_et_verifycode_id)).getText().toString();
        ((TextView)findViewById(R.id.signup_tv_status_id)).setText("");
    }

    private void updateStatus(final String status)
    {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                ((TextView)findViewById(R.id.signup_tv_status_id)).setText(status);
            }
        });

    }

    private class RegistrationStateProviderID implements IRegistrationStateProvider
    {

        @Override
        public void onBadlyDeactivatedAccount() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onBadlyDeactivatedAccount");
            mStateProviderID = "RegistrationStateProviderID::onBadlyDeactivatedAccount";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onConnectionError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onConnectionError");
            mStateProviderID = "RegistrationStateProviderID::onConnectionError";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onServerInternalError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onServerInternalError");
            mStateProviderID = "RegistrationStateProviderID::onServerInternalError";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onUnknownError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onUnknownError");
            mStateProviderID = "RegistrationStateProviderID::onUnknownError";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onAccount(String login, String password, String countryCode, String vippieId) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onAccount");
            mStateProviderID = "RegistrationStateProviderID::onAccount: login: " + login +
                    " ,password: " + password + " ,countryCode: " + countryCode + " ,vippieId: " + vippieId;
            updateStatus(mStateProviderID);
        }

        @Override
        public void onEnd() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onEnd");
            mStateProviderID = "RegistrationStateProviderID::onEnd";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onNotActivated() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onNotActivated");
            mStateProviderID = "RegistrationStateProviderID::onNotActivated";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onStart() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onStart");
            mStateProviderID = "RegistrationStateProviderID::onStart";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onTooManyDevices() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onTooManyDevices");
            mStateProviderID = "RegistrationStateProviderID::onTooManyDevices";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onUsedEmail(String arg0) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onUsedEmail");
            mStateProviderID = "RegistrationStateProviderID::onUsedEmail";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onUsedVippieId(String vippieId) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onUsedVippieId: vippieId: " + vippieId);
            mStateProviderID = "RegistrationStateProviderID::onUsedVippieId  vippieId: " + vippieId;
            updateStatus(mStateProviderID);
        }

        @Override
        public void onWaitingForActivation(String vippieId, String pass) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onWaitingForActivation");
            mStateProviderID = "RegistrationStateProviderID::onWaitingForActivation vippieId: " + vippieId +
                    " ,pass: " + pass;
            updateStatus(mStateProviderID);
        }

        @Override
        public void onWrongEmail(String email) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onWrongEmail");
            mStateProviderID = "RegistrationStateProviderID::onWrongEmail";
            updateStatus(mStateProviderID);
        }

        @Override
        public void onWrongUserPass() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderID::onWrongUserPass");
            mStateProviderID = "RegistrationStateProviderID::onWrongUserPass";
            updateStatus(mStateProviderID);
        }
    }

    private class RegistrationStateProviderNumber implements unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider
    {

        @Override
        public void onBadlyDeactivatedAccount() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onBadlyDeactivatedAccount");
            mStateProviderNumber = "RegistrationStateProviderNumber::onBadlyDeactivatedAccount";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onConnectionError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onConnectionError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onConnectionError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onServerInternalError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onServerInternalError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onServerInternalError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onUnknownError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onUnknownError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onUnknownError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onAccountActivated(String login, String password, String countryCode,
                String rawNumber) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onAccountActivated login: " + login + " ,password: " + password + " ,countryCode: " + countryCode +
                    " ,rawNumber: " + rawNumber);
            mStateProviderNumber = "RegistrationStateProviderNumber::onAccountActivated login: " + login + " ,password: " + password + " ,countryCode: " + countryCode +
                    ",rawNumber:" + rawNumber;
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onAccountActivationError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onAccountActivationError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onAccountActivationError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onAccountAlreadyExists(String login, String password,
                String countryCode, String rawNumber) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onAccountAlreadyExists login: " + login + " ,password: " + password + " ,countryCode: " + countryCode +
                    " ,rawNumber: " + rawNumber);
            mStateProviderNumber = "RegistrationStateProviderNumber::onAccountAlreadyExists login: " + login + " ,password: " + password + " ,countryCode: " + countryCode +
                    ",rawNumber:" + rawNumber;
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onAlreadyAssignedNumberError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onAlreadyAssignedNumberError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onAlreadyAssignedNumberError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onAlreadyExistNumberError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onAlreadyExistNumberError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onAlreadyExistNumberError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onApiErrorSmsSentError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onApiErrorSmsSentError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onApiErrorSmsSentError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onBlacklistedNumberSmsSentError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onBlacklistedNumberSmsSentError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onBlacklistedNumberSmsSentError";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onCallbackStarted() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onCallbackStarted");
            mStateProviderNumber = "RegistrationStateProviderNumber::onCallbackStarted";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onCallbackUsed() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onCallbackUsed");
            mStateProviderNumber = "RegistrationStateProviderNumber::onCallbackUsed";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onFeedbackSent(boolean arg0) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onFeedbackSent");
            mStateProviderNumber = "RegistrationStateProviderNumber::onFeedbackSent";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onNeedPassword() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onNeedPassword");
            mStateProviderNumber = "RegistrationStateProviderNumber::onNeedPassword";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onSmsPending() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onSmsPending");
            mStateProviderNumber = "RegistrationStateProviderNumber::onSmsPending";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onSmsState(Boolean sent) {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onSmsState sent: " + sent);
            mStateProviderNumber = "RegistrationStateProviderNumber::onSmsState sent: " + sent;
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onTooManyAuthorizationAttempts() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onTooManyAuthorizationAttempts");
            mStateProviderNumber = "RegistrationStateProviderNumber::onTooManyAuthorizationAttempts";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onTooManySmsRequests() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onTooManySmsRequests");
            mStateProviderNumber = "RegistrationStateProviderNumber::onTooManySmsRequests";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onWrongAuthorizationCode() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onWrongAuthorizationCode");
            mStateProviderNumber = "RegistrationStateProviderNumber::onWrongAuthorizationCode";
            updateStatus(mStateProviderNumber);
        }

        @Override
        public void onWrongNumberSmsSentError() {
            // TODO Auto-generated method stub
            Log.d(TAG,"RegistrationStateProviderNumber::onWrongNumberSmsSentError");
            mStateProviderNumber = "RegistrationStateProviderNumber::onWrongNumberSmsSentError";
            updateStatus(mStateProviderNumber);
        }

    }
}
