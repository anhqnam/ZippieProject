package com.example.sdktest;

import java.util.List;

import com.example.sdktest.api.APIHelper;
import com.voipswitch.contacts.Contact;

import unique.packagename.sdkwrapper.contacts.ContactsWrapper;
import unique.packagename.sdkwrapper.contacts.IOnContactsChangeListener;
import unique.packagename.sdkwrapper.contacts.IOnSyncContactsListener;
import unique.packagename.sdkwrapper.contacts.callbacks.IImageRepositoryCallback;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends Activity implements OnClickListener {

	public final String TAG = "ContactActivity";
	private ContactsWrapper mContactsWrapper;
	// private String mStatus = "";
	private String mResult = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_contact);
		super.onCreate(savedInstanceState);
		init();

	}

	private void init() {
		mContactsWrapper = APIHelper.getInstance().getContactsWrapper();
		mContactsWrapper.addContactsSyncListener(new IOnSyncContactsListener() {

			@Override
			public void onSynchronizedContactsChanged(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSipContactsSyncInited() {
				// TODO Auto-generated method stub

			}
		});
		findViewById(R.id.contactFetchClick).setOnClickListener(this);
		findViewById(R.id.contactFetch1Click).setOnClickListener(this);
		findViewById(R.id.contactFetch2Click).setOnClickListener(this);
		findViewById(R.id.contactAddContactListenerClick).setOnClickListener(
				this);
		findViewById(R.id.contactCheckForChangeClick).setOnClickListener(this);
		findViewById(R.id.contactContainsVippieNumberClick).setOnClickListener(
				this);
		findViewById(R.id.contactContainsVippieNumbersCachedsClick)
				.setOnClickListener(this);
		findViewById(R.id.contactGetContactPhotoClick).setOnClickListener(this);
		findViewById(R.id.contactGetNumberForVippieClick).setOnClickListener(
				this);
		findViewById(R.id.contactGetVippieLoginForNumberClick)
				.setOnClickListener(this);

		findViewById(R.id.contactGetVippiePhonesClick).setOnClickListener(this);
		findViewById(R.id.contactHasVippieNumberClick).setOnClickListener(this);
		findViewById(R.id.contactIsVippieIdClick).setOnClickListener(this);
		findViewById(R.id.contactIsVippieNumberClick).setOnClickListener(this);
		findViewById(R.id.contactPhoneForVippieNumberClick).setOnClickListener(
				this);
		findViewById(R.id.contactPrepareNumberClick).setOnClickListener(this);
		findViewById(R.id.contactRegisterListenerClick)
				.setOnClickListener(this);
		findViewById(R.id.contactRemoveContactListenerClick)
				.setOnClickListener(this);
		findViewById(R.id.contactRequestAddNewClick).setOnClickListener(this);
		findViewById(R.id.contactRequestEditClick).setOnClickListener(this);

		findViewById(R.id.contactRequestViewClick).setOnClickListener(this);
		findViewById(R.id.contactUnregisterListenerClick).setOnClickListener(
				this);
		findViewById(R.id.contactVerifyNumberClick).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactFetchClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			Thread thread = new Thread() {
				@Override
				public void run() {
					Contact[] iContacts = mContactsWrapper.fetch();
					for (int i = 0; i < iContacts.length; i++) {
						mResult += "stt : " + i + " GetID: "
								+ iContacts[i].getId() + " ,getDisplayName: "
								+ iContacts[i].getDisplayName()
								+ " ,getCurrentPhone: "
								+ iContacts[i].getCurrentPhone();
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							((TextView) findViewById(R.id.tv_result))
									.setText(mResult);
						}
					});
				}
			};
			thread.start();
			break;
		}

		case R.id.contactFetch1Click: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			Contact iContact = mContactsWrapper.fetch(47, false);
			mResult = " GetID: " + iContact.getId() + " ,getDisplayName: "
					+ iContact.getDisplayName() + " ,getCurrentPhone: "
					+ iContact.getCurrentPhone();
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}

		case R.id.contactFetch2Click: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			Contact iContact = mContactsWrapper.fetch("21456668955", false);
			mResult = " GetID: " + iContact.getId() + " ,getDisplayName: "
					+ iContact.getDisplayName() + " ,getCurrentPhone: "
					+ iContact.getCurrentPhone();
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactAddContactListenerClick:
			IOnSyncContactsListener listener = new IOnSyncContactsListener() {

				@Override
				public void onSynchronizedContactsChanged(String arg0) {

				}

				@Override
				public void onSipContactsSyncInited() {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(ContactActivity.this,"Contacts synchronisator inited",Toast.LENGTH_SHORT).show();
						}
					});
				}
			};
			mContactsWrapper.addContactsSyncListener(listener);
			break;
		case R.id.contactCheckForChangeClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			// Contact[] iContacts1 = mContactsWrapper.fetch();
			mContactsWrapper.checkForChanges();
			// Contact[] iContacts2 = mContactsWrapper.fetch();
			((TextView) findViewById(R.id.tv_result))
					.setText("Call Check for Change");
			break;
		}
		case R.id.contactContainsVippieNumberClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			java.util.List<com.voipswitch.contacts.Contact.Phone> list;
			Contact[] iContacts = mContactsWrapper.fetch();
			for (int i = 0; i < iContacts.length; i++) {
				list = (iContacts[i].getPhones());
				String m = mContactsWrapper.containsVippieNumber(list);
				mResult = " stt " + i + " : " + m;
				((TextView) findViewById(R.id.tv_result)).setText(mResult);
			}

			break;
		}
		case R.id.contactContainsVippieNumbersCachedsClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			java.util.List<com.voipswitch.contacts.Contact.Phone> list;
			Contact[] iContacts = mContactsWrapper.fetch();
			for (int i = 0; i < iContacts.length; i++) {
				list = (iContacts[i].getPhones());
				List<String> m = mContactsWrapper
						.containsVippieNumbersCached(list);
				mResult += " stt " + i + " : " + m;
				((TextView) findViewById(R.id.tv_result)).setText(mResult);
			}
			break;
		}
		case R.id.contactGetContactPhotoClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			Contact contact = mContactsWrapper.fetch(3, false);
			IImageRepositoryCallback<Contact> callback = new IImageRepositoryCallback<Contact>() {
				@Override
				public void a(Bitmap arg0) {
					Log.d("IImageRepositoryCallback", arg0.toString());
					final Bitmap bm = arg0;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							((ImageView) findViewById(R.id.img_v))
									.setImageBitmap(bm);
						}
					});
				}
			};
			mContactsWrapper.getContactPhoto(contact, callback);
			break;
		}

		case R.id.contactGetNumberForVippieClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			String number1 = mContactsWrapper.getNumberForVippie("1656823341");
			String number2 = mContactsWrapper.getNumberForVippie("1888373");
			mResult = " number 1: " + number1 + " number 2: " + number2;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactGetVippieLoginForNumberClick: {
			String number1 = mContactsWrapper
					.getVippieLoginForNumber("1656823341");
			String number2 = mContactsWrapper
					.getVippieLoginForNumber("1888373");
			mResult = " number 1: " + number1 + " number 2: " + number2;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactGetVippiePhonesClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			java.util.List<com.voipswitch.contacts.Contact.Phone> list;
			List<com.voipswitch.contacts.Contact.Phone> phone;
			Contact[] iContacts = mContactsWrapper.fetch();
			for (int i = 0; i < iContacts.length; i++) {
				list = (iContacts[i].getPhones());
				phone = mContactsWrapper.getVippiePhones(list);
				mResult = " " + phone.toString();
				((TextView) findViewById(R.id.tv_result)).setText(mResult);
			}
			break;
		}
		case R.id.contactHasVippieNumberClick: {
			((TextView) findViewById(R.id.tv_result)).setText("");
			java.util.List<com.voipswitch.contacts.Contact.Phone> list;
			Boolean b;
			Contact[] iContacts = mContactsWrapper.fetch();
			for (int i = 0; i < iContacts.length; i++) {
				list = (iContacts[i].getPhones());
				b = mContactsWrapper.hasVippieNumber(list);
				mResult = " " + b.toString();
				((TextView) findViewById(R.id.tv_result)).setText(mResult);
			}
			break;
		}
		case R.id.contactIsVippieIdClick: {
			Boolean b;
			b = mContactsWrapper.isVippieId("1656823341");
			mResult = " " + b.toString();
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactIsVippieNumberClick: {
			Boolean b;
			b = mContactsWrapper.isVippieNumber("1656823341");
			mResult = " " + b.toString();
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactPhoneForVippieNumberClick: {
			break;
		}
		case R.id.contactPrepareNumberClick: {
			String prepare = "";
			prepare = mContactsWrapper.prepareNumber("01656823341");
			mResult = " " + prepare;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactRegisterListenerClick: {
			break;
		}
		case R.id.contactRemoveContactListenerClick: {
			IOnContactsChangeListener iolistener = new IOnContactsChangeListener() {

				@Override
				public void onContacsChanged() {

				}
			};
			mContactsWrapper.removeContactsChangeListener(iolistener);
			break;
		}
		case R.id.contactRequestAddNewClick:
			Intent intend;
			intend = mContactsWrapper.requestAddNew();
			mResult = " " + intend;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		case R.id.contactRequestEditClick: {
			Contact contact = mContactsWrapper.fetch(3, false);
			Intent intent;
			intent = mContactsWrapper.requestEdit(contact);
			mResult = " " + intent;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactRequestViewClick: {
			Contact contact = mContactsWrapper.fetch(3, false);
			Intent intent;
			intent = mContactsWrapper.requestView(contact);
			mResult = " " + intent;
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		case R.id.contactUnregisterListenerClick: {
			break;
		}
		case R.id.contactVerifyNumberClick: {
			Boolean verify = mContactsWrapper.verifyNumber("+84979485576");
			mResult = "Result : " + verify.toString();
			((TextView) findViewById(R.id.tv_result)).setText(mResult);
			break;
		}
		default:
			break;
		}
	}
}
