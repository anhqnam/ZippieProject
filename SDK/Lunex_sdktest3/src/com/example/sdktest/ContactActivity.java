package com.example.sdktest;

import com.example.sdktest.api.APIHelper;
import com.voipswitch.contacts.Contact;

import unique.packagename.sdkwrapper.contacts.ContactsWrapper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener {
	
	public final String TAG = "ContactActivity";
	private ContactsWrapper mContactsWrapper;
	private String mStatus,mResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_contact);
		super.onCreate(savedInstanceState);
		init();

	}
	
	private void init()
	{
		mContactsWrapper = APIHelper.getInstance().getContactsWrapper();
		findViewById(R.id.contactFetchClick).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactFetchClick:
		{
			Thread thread = new Thread()
			{
			    @Override
			    public void run() {
			    	Contact[] iContacts = mContactsWrapper.fetch();
			    	for (int i=0;i<iContacts.length;i++)
			    	{
			    		mResult += "GetID: " + iContacts[i].getId() + " ,getDisplayName: " +
			    					iContacts[i].getDisplayName() + " ,getCurrentPhone: " + iContacts[i].getCurrentPhone();
			    	}
			    	runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							((TextView)findViewById(R.id.tv_result)).setText(mResult);
						}
					});
			    }
			};
			thread.start();
			break;
		}
		case R.id.contactFetch1Click:
			
			break;
		case R.id.contactFetch2Click:
			
			break;
		case R.id.contactAddContactListenerClick:
			
			break;
		case R.id.contactCheckForChangeClick:
			
			break;
		case R.id.contactContainsVippieNumberClick:
			
			break;
		case R.id.contactContainsVippieNumbersCachedsClick:
			
			break;
		case R.id.contactGetContactPhotoClick:
			
			break;
		case R.id.contactGetNumberForVippieClick:
			
			break;
		case R.id.contactGetVippieLoginForNumberClick:
			
			break;
		case R.id.contactGetVippiePhonesClick:
			
			break;
		case R.id.contactHasVippieNumberClick:
			
			break;
		case R.id.contactIsVippieIdClick:
			
			break;
		case R.id.contactIsVippieNumberClick:
			
			break;
		case R.id.contactPhoneForVippieNumberClick:
			
			break;
		case R.id.contactPrepareNumberClick:
			
			break;
		case R.id.contactRegisterListenerClick:
			
			break;
		case R.id.contactRemoveContactListenerClick:
			
			break;
		case R.id.contactRequestAddNewClick:
			
			break;
		case R.id.contactRequestEditClick:
			
			break;
		case R.id.contactRequestViewClick:
			
			break;
		case R.id.contactUnregisterListenerClick:
			
			break;
		case R.id.contactVerifyNumberClick:
			
			break;
		default:
			break;
		}
	}

}
