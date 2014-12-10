package com.example.sdktest;

import unique.packagename.messages.MsgThreadsActivity;
import unique.packagename.sdkwrapper.calling.Calling;
import unique.packagename.sdkwrapper.contacts.ContactsWrapper;
import unique.packagename.sdkwrapper.contacts.IOnSyncContactsListener;
import unique.packagename.sdkwrapper.messages.MessagesWrapper;
import unique.packagename.sip.SipContactsSynchronizationManager;
import unique.packagename.sip.SipServersManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.voipswitch.settings.Settings;
import com.voipswitch.sip.ISipManager;
import com.voipswitch.sip.SipUri;

public class NumberActionFragment extends Fragment implements OnClickListener{

    private EditText mNumber;
    private Button mFreeCall;
    private Button mPaidCall;
    private Button mFreeSms;
    private Button mPaidSms;
    private Button mMessagesThreads;
    private ContactsWrapper mContacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_init_call, container, false);
        mNumber = (EditText) root.findViewById(R.id.call_number);

        mFreeCall = (Button) root.findViewById(R.id.call_free);
        mPaidCall = (Button) root.findViewById(R.id.call_paid);

        mFreeSms = (Button) root.findViewById(R.id.sms_free);
        mPaidSms = (Button) root.findViewById(R.id.sms_paid);

        mMessagesThreads = (Button) root.findViewById(R.id.messages_threads);

        mContacts = new ContactsWrapper();
        mContacts.addContactsSyncListener(new IOnSyncContactsListener() {
            @Override
            public void onSynchronizedContactsChanged(String arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onSipContactsSyncInited() {
                if(isAdded()){
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Contacts synchronisator inited", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOnClickListeners();
        //account has to be loged int ovippie system to call methods (after log in dialer synchronizes numbers etc )
        //same thing for changeNumber methods
    }

    private void setupOnClickListeners() {
        mFreeCall.setOnClickListener(this);
        mPaidCall.setOnClickListener(this);

        mFreeSms.setOnClickListener(this);
        mPaidSms.setOnClickListener(this);

        mMessagesThreads.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_free:
                makeFreeCall();
                break;
            case R.id.call_paid:
                makePaidCall();
                break;
            case R.id.sms_paid:
                sendPaidSms();
                break;
            case R.id.sms_free:
                sendFreeSms();
                break;
            case R.id.messages_threads:
                openMessagesThreads();
                break;
            default:
                break;
        }
    }

    private String getRawNumber(){
        return mNumber.getText().toString();
    }

    private String getNumber(){
        String number = getRawNumber();
        number = SipContactsSynchronizationManager.prepareNumber(number);
        return number;
    }

    private void makeFreeCall() {
        String number = getNumber();
        if(isFreeActionPossible(number)){
            SipUri uri = SipUri.create(number, "", getRawNumber(), "");
            new Calling().callFree(getActivity(), uri, Settings.VideoCallMode.ASK);	//can provide call mode here without asking
        }
    }

    private void makePaidCall() {
        String number = getNumber();
        if(isPaidActionPossible(number)){
            SipUri uri = SipUri.create(SipServersManager.GSM_CALL_PREFIX + number, "", getRawNumber(), "");
            new Calling().callPaid(getActivity(), uri);
        }
    }

    private void sendFreeSms(){
        String number = getNumber();
        number = SipContactsSynchronizationManager.prepareNumber(number);
        SipUri toUri = SipUri.create(number, "", getRawNumber(), "");
        new MessagesWrapper().smsFree(getActivity(), toUri);
        /*if(isFreeActionPossible(number)){
            number = SipContactsSynchronizationManager.prepareNumber(number);
            SipUri toUri = SipUri.create(number, "", getRawNumber(), "");
            new MessagesWrapper().smsFree(getActivity(), toUri);
        }*/
    }

    private void sendPaidSms(){
        String number = getNumber();
        if(isPaidActionPossible(number)){
            SipUri toUri = SipUri.create(SipServersManager.GSM_CALL_PREFIX + number, "", getRawNumber(), "");
            new MessagesWrapper().smsPaid(getActivity(), toUri);
        }
    }

    private boolean isFreeActionPossible(String number){
        boolean actionPossible = true;
        ISipManager manager = DialerApplication.getSipManager();
        boolean hasSip = manager.getSipContactsSynchronizationManager().isVippieNumber(number);
        if(!manager.isRegistered()){
            actionPossible = false;
            Toast.makeText(getActivity(), "Account not registerd", Toast.LENGTH_LONG).show();
        } else if(!hasSip){
            actionPossible = false;
            Toast.makeText(getActivity(), "No sip for this number,  can't perform free sction", Toast.LENGTH_LONG).show();
        }
        return actionPossible;
    }

    private boolean isPaidActionPossible(String number){
        boolean actionPossible = true;
        ISipManager manager = DialerApplication.getSipManager();
        if(!manager.isRegistered()){
            actionPossible = false;
            Toast.makeText(getActivity(), "Account not registerd", Toast.LENGTH_LONG).show();
        }
        return actionPossible;
    }

    public void openMessagesThreads(){
        startActivity(new Intent(getActivity(), MsgThreadsActivity.class));
    }
}
