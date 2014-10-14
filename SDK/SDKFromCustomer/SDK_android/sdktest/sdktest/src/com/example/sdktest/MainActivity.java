package com.example.sdktest;

import unique.packagename.DialerTabsActivity;
import unique.packagename.VippieApplication;
import unique.packagename.contacts.ContactsActivity;
import unique.packagename.registration.fragment.RegistrationActivity;
import unique.packagename.sdkwrapper.registration.IRegistrationWrapper;
import unique.packagename.sdkwrapper.registration.RegistrationWrapper;
import unique.packagename.sdkwrapper.registration.id.IRegistrationStateProvider;
import unique.packagename.util.NotificationHelper;

import com.example.sdktest.R;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {

	private RegistrationWrapper mRegistrationWrapper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new NumberActionFragment()).commit();
		}
		
		setupRegistrationWrapper();
		
		//example usage of registration wrapper
//		String number = "123456";
//		String cc = "44";
//		mRegistrationWrapper.registerWithSms(number, cc);
//		String activationCode = "4444";
//		mRegistrationWrapper.activate(number, cc, activationCode);
	}

	protected void setupRegistrationWrapper() {
		unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider numberStateProvider = new unique.packagename.sdkwrapper.registration.number.IRegistrationStateProvider() {
			
			@Override
			public void onUnknownError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServerInternalError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConnectionError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBadlyDeactivatedAccount() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onWrongNumberSmsSentError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onWrongAuthorizationCode() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTooManySmsRequests() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTooManyAuthorizationAttempts() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSmsState(Boolean sent) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSmsPending() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onNeedPassword() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFeedbackSent(boolean b) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCallbackUsed() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCallbackStarted() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBlacklistedNumberSmsSentError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onApiErrorSmsSentError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAlreadyExistNumberError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAlreadyAssignedNumberError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAccountAlreadyExists(String login, String password,
					String countryCode, String rawNumber) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAccountActivationError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAccountActivated(String login, String password,
					String countryCode, String rawNumber) {
				// TODO Auto-generated method stub
				
			}
		};
		
		unique.packagename.sdkwrapper.registration.id.IRegistrationStateProvider idStateProvider = new IRegistrationStateProvider() {
			
			@Override
			public void onUnknownError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onServerInternalError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConnectionError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBadlyDeactivatedAccount() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onWrongUserPass() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onWrongEmail(String email) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onWaitingForActivation(String vippieId, String pass) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUsedVippieId(String vippieId) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onUsedEmail(String email) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTooManyDevices() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onNotActivated() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEnd() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAccount(String login, String password, String countryCode,
					String vippieId) {
				// TODO Auto-generated method stub
				
			}
		};
		
		mRegistrationWrapper = new RegistrationWrapper(this, idStateProvider, numberStateProvider);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		
		case R.id.action_registration:
			startRegistrationActivity();
			break;
		case R.id.action_sip_register:
			sipRegister();
			break;
		case R.id.action_sip_unregister:
			sipUnregister();
			break;
		case R.id.action_dialer_exit:
			exitDialer();
			break;
		case R.id.action_contacts:
			startContactsActivity();
			break;
		case R.id.action_start_dialer:
			startDialerTabsActivity();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void startDialerTabsActivity() {
		startActivity(new Intent(this, DialerTabsActivity.class));
	}

	private void sipRegister() {
		DialerApplication.register();
	}

	private void sipUnregister() {
		DialerApplication.unregister();
		NotificationHelper.hideAllNotifications(this);
	}

	private void exitDialer() {
		VippieApplication.exitApp();
	}
	
	private void startContactsActivity() {
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivity(intent);
	}

	private void startRegistrationActivity() {
		startActivity(new Intent(this, RegistrationActivity.class));
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
