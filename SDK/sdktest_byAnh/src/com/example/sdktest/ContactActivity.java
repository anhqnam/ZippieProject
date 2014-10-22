
package com.example.sdktest;

import java.util.List;

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

import com.example.sdktest.api.APIHelper;
import com.voipswitch.contacts.Contact;

public class ContactActivity extends Activity implements OnClickListener{

    public final String TAG = "ContactActivity";

    private ContactsWrapper mContactsWrapper;

    // private String mStatus = "";
    private String mResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.contact_activity_lay);
        super.onCreate(savedInstanceState);
        init();
        DialerApplication.register();
    }

    @Override
    protected void onDestroy() {
        DialerApplication.unregister();
        super.onDestroy();
    }

    private void init() {
        mContactsWrapper = APIHelper.getInstance().getContactsWrapper();
        mContactsWrapper.addContactsChangeListener(new IOnContactsChangeListener() {

            @Override
            public void onContacsChanged() {
                Log.d("ContactActivity", "onContacsChanged");
            }
        });

        findViewById(R.id.contact_FetchClick_id).setOnClickListener(this);
        findViewById(R.id.contact_Fetch1Click_id).setOnClickListener(this);
        findViewById(R.id.contact_Fetch2Click_id).setOnClickListener(this);
        findViewById(R.id.contact_AddContactListenerClick_id).setOnClickListener(this);
        findViewById(R.id.contact_CheckForChangeClick_id).setOnClickListener(this);
        findViewById(R.id.contact_ContainsVippieNumberClick_id).setOnClickListener(this);
        findViewById(R.id.contact_ContainsVippieNumbersCachedsClick_id).setOnClickListener(this);
        findViewById(R.id.contact_GetPhonePhotoClick_id).setOnClickListener(this);
        findViewById(R.id.contact_GetContactPhotoClick_id).setOnClickListener(this);
        findViewById(R.id.contact_GetNumberForVippieClick_id).setOnClickListener(this);
        findViewById(R.id.contact_GetVippieLoginForNumberClick_id).setOnClickListener(this);

        findViewById(R.id.contact_GetVippiePhonesClick_id).setOnClickListener(this);
        findViewById(R.id.contact_HasVippieNumberClick_id).setOnClickListener(this);
        findViewById(R.id.contact_IsVippieIdClick_id).setOnClickListener(this);
        findViewById(R.id.contact_IsVippieNumberClick_id).setOnClickListener(this);
        findViewById(R.id.contact_PrepareNumberClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RegisterListenerClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RemoveContactListenerClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RequestAddNewClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RequestEditClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RequestPickClick_id).setOnClickListener(this);
        findViewById(R.id.contact_RequestViewClick_id).setOnClickListener(this);
        findViewById(R.id.contact_VerifyNumberClick_id).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mResult = "";
        switch (v.getId()) {
            case R.id.contact_FetchClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        Contact[] iContacts = mContactsWrapper.fetch();
                        for (int i = 0; i < iContacts.length; i++) {
                            mResult += "   stt : " + i + " GetID: " + iContacts[i].getId()
                                    + " ,getDisplayName: " + iContacts[i].getDisplayName()
                                    + " ,getCurrentPhone: " + iContacts[i].getCurrentPhone() + "\n";
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                            }
                        });
                    }
                };
                thread.start();
                break;
            }

            case R.id.contact_Fetch1Click_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                Contact iContact = mContactsWrapper.fetch(3, true);
                mResult = " GetID: " + iContact.getId() + " ,getDisplayName: "
                        + iContact.getDisplayName() + " ,getCurrentPhone: "
                        + iContact.getCurrentPhone();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }

            case R.id.contact_Fetch2Click_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                Contact iContact = mContactsWrapper.fetch("84922012350", true);
                mResult = " GetID: " + iContact.getId() + " ,getDisplayName: "
                        + iContact.getDisplayName() + " ,getCurrentPhone: "
                        + iContact.getCurrentPhone();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_AddContactListenerClick_id:
                IOnSyncContactsListener listener = new IOnSyncContactsListener() {

                    @Override
                    public void onSynchronizedContactsChanged(String arg0) {

                    }

                    @Override
                    public void onSipContactsSyncInited() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(ContactActivity.this,
                                        "Contacts synchronisator inited", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                };
                mContactsWrapper.addContactsSyncListener(listener);
                break;
            case R.id.contact_CheckForChangeClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                // Contact[] iContacts1 = mContactsWrapper.fetch();
                mContactsWrapper.checkForChanges();
                // Contact[] iContacts2 = mContactsWrapper.fetch();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("Call Check for Change");
                break;
            }
            case R.id.contact_ContainsVippieNumberClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                mResult = "";
                java.util.List<com.voipswitch.contacts.Contact.Phone> list;
                Contact[] iContacts = mContactsWrapper.fetch();
                for (int i = 0; i < iContacts.length; i++) {
                    list = (iContacts[i].getPhones());
                    String m = mContactsWrapper.containsVippieNumber(list);
                    mResult += " stt " + i + " : " + m;
                    ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                }
                break;
            }
            case R.id.contact_ContainsVippieNumbersCachedsClick_id: {
                mResult = "";
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                java.util.List<com.voipswitch.contacts.Contact.Phone> list;
                Contact[] iContacts = mContactsWrapper.fetch();
                for (int i = 0; i < iContacts.length; i++) {
                    list = (iContacts[i].getPhones());
                    List<String> m = mContactsWrapper.containsVippieNumbersCached(list);
                    mResult += " stt " + i + " : " + m;
                    ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                }
                break;
            }
            case R.id.contact_GetPhonePhotoClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                ((ImageView)findViewById(R.id.contact_img_v_id)).setImageBitmap(null);
                Contact contact = mContactsWrapper.fetch(11, false);
                Contact.Phone phone = contact.getCurrentPhone();
                IImageRepositoryCallback<Contact.Phone> callback = new IImageRepositoryCallback<Contact.Phone>() {
                    @Override
                    public void a(Bitmap arg0) {
                        Log.d("IImageRepositoryCallback", arg0.toString());
                        final Bitmap bm = arg0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView)findViewById(R.id.contact_img_v_id)).setImageBitmap(bm);
                            }
                        });
                    }
                };

                mContactsWrapper.getContactPhoto(phone, callback);
                break;
            }
            case R.id.contact_GetContactPhotoClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                ((ImageView)findViewById(R.id.contact_img_v_id)).setImageBitmap(null);
                Contact contact = mContactsWrapper.fetch(3, false);
                IImageRepositoryCallback<Contact> callback = new IImageRepositoryCallback<Contact>() {
                    @Override
                    public void a(Bitmap arg0) {
                        Log.d("IImageRepositoryCallback", arg0.toString());
                        final Bitmap bm = arg0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView)findViewById(R.id.contact_img_v_id)).setImageBitmap(bm);
                            }
                        });
                    }
                };
                mContactsWrapper.getContactPhoto(contact, callback);
                break;
            }

            case R.id.contact_GetNumberForVippieClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                String number1 = mContactsWrapper.getNumberForVippie("1873514");
                String number2 = mContactsWrapper.getNumberForVippie("1888065");
                mResult = " number 1: " + number1 + " number 2: " + number2;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_GetVippieLoginForNumberClick_id: {
                String number1 = mContactsWrapper.getVippieLoginForNumber("84922012350");
                String number2 = mContactsWrapper.getVippieLoginForNumber("84908475112");
                mResult = " number 1: " + number1 + " number 2: " + number2;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_GetVippiePhonesClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                mResult = "";
                java.util.List<com.voipswitch.contacts.Contact.Phone> list;
                List<com.voipswitch.contacts.Contact.Phone> phone;
                Contact[] iContacts = mContactsWrapper.fetch();
                for (int i = 0; i < iContacts.length; i++) {
                    list = (iContacts[i].getPhones());
                    phone = mContactsWrapper.getVippiePhones(list);
                    mResult += " " + phone.toString();
                    ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                }
                break;
            }
            case R.id.contact_HasVippieNumberClick_id: {
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText("");
                mResult = "";
                java.util.List<com.voipswitch.contacts.Contact.Phone> list;
                Boolean b;
                Contact[] iContacts = mContactsWrapper.fetch();
                for (int i = 0; i < iContacts.length; i++) {
                    list = (iContacts[i].getPhones());
                    b = mContactsWrapper.hasVippieNumber(list);
                    mResult += " " + b.toString();
                    ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                }
                break;
            }
            case R.id.contact_IsVippieIdClick_id: {
                Boolean b;
                b = mContactsWrapper.isVippieId("1873514");
                mResult = " " + b.toString();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_IsVippieNumberClick_id: {
                Boolean b;
                b = mContactsWrapper.isVippieNumber("84933094998");
                mResult = " " + b.toString();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }

            case R.id.contact_PrepareNumberClick_id: {
                String prepare = "";
                prepare = mContactsWrapper.prepareNumber("+841656823341");
                mResult = " " + prepare;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_RegisterListenerClick_id: {
                break;
            }
            case R.id.contact_RemoveContactListenerClick_id: {
                IOnContactsChangeListener iolistener = new IOnContactsChangeListener() {

                    @Override
                    public void onContacsChanged() {

                    }
                };
                mContactsWrapper.removeContactsChangeListener(iolistener);
                break;
            }
            case R.id.contact_RequestAddNewClick_id: {
                Intent intent;
                intent = mContactsWrapper.requestAddNew();
                startActivityForResult(intent, 1);
                mResult = " " + intent;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_RequestEditClick_id: {
                Contact contact = mContactsWrapper.fetch(3, false);
                Intent intent;
                intent = mContactsWrapper.requestEdit(contact);
                startActivityForResult(intent, 1);
                mResult = " " + intent;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_RequestViewClick_id: {
                Contact contact = mContactsWrapper.fetch(3, false);
                Intent intent;
                intent = mContactsWrapper.requestView(contact);
                startActivityForResult(intent, 1);
                mResult = " " + intent;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_RequestPickClick_id: {
                Intent intent;
                intent = mContactsWrapper.requestPick();
                startActivityForResult(intent, 1);
                mResult = " " + intent;
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            case R.id.contact_VerifyNumberClick_id: {
                Boolean verify = mContactsWrapper.verifyNumber("+84979485576");
                mResult = "Result : " + verify.toString();
                ((TextView)findViewById(R.id.contact_tv_result_id)).setText(mResult);
                break;
            }
            default:
                break;
        }
    }

}
