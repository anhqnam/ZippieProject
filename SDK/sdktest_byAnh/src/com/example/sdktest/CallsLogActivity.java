/*
 * 
 */

package com.example.sdktest;

import java.util.Date;

import unique.packagename.sdkwrapper.callslog.CallsLogWrapper;
import unique.packagename.sdkwrapper.callslog.IOnCallLogChangedListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.sdktest.api.APIHelper;
import com.voipswitch.callslog.CallsLogEntry;
import com.voipswitch.sip.SipUri;

public class CallsLogActivity extends Activity implements OnClickListener {

    /** The m callslog wrapper. */
    private CallsLogWrapper mCallslogWrapper;

    /** The m uri. */
    private SipUri mUri;

    /** The m type. */
    private int mType;

    /** The m duration. */
    private int mDuration;

    private Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callslog_activity_lay);

        Init();
    }

    private void Init() {

        mCallslogWrapper = APIHelper.getInstance().getCallslogWrapper();
        // execute button
        findViewById(R.id.callslog_addcallslog_id).setOnClickListener(this);
        findViewById(R.id.callslog_fetchcallslog_id).setOnClickListener(this);
        findViewById(R.id.callslog_fetchcallslogtype_id).setOnClickListener(this);

        findViewById(R.id.callslog_fetchcallslogtypenumber_id).setOnClickListener(this);
        findViewById(R.id.callslog_fetchcallslognumber_id).setOnClickListener(this);
        findViewById(R.id.callslog_fetchIncomingByNumber_id).setOnClickListener(this);

        findViewById(R.id.callslog_fetchOutgoingByNumber_id).setOnClickListener(this);
        findViewById(R.id.callslog_CallslogregisterListener_id).setOnClickListener(this);
        findViewById(R.id.callslog_removecallslog_id).setOnClickListener(this);

        findViewById(R.id.callslog_removecallslogentry_id).setOnClickListener(this);
        findViewById(R.id.callslog_removecallslogtype_id).setOnClickListener(this);
        findViewById(R.id.callslog_RemovecallslogTypeNumber_id).setOnClickListener(this);

        findViewById(R.id.callslog_Removecallslognumber_id).setOnClickListener(this);
        findViewById(R.id.callslog_removeGroupcallslog_id).setOnClickListener(this);
        findViewById(R.id.callslog_unregisterListenercalslog_id).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.callslog_addcallslog_id: {
                //                ContactsWrapper mContactsWrapper = APIHelper.getInstance().getContactsWrapper();
                //                Contact iContact = mContactsWrapper.fetch("84933094998", false);
                //                CallsLogEntry mCallsLogEntry = new CallsLogEntry();
                //                mUri = iContact.getSipUri();
                //                Log.d("SipUri :", mUri.toString());
                //                mType = 1;
                //                mDuration = 1;
                //                mDate = mCallsLogEntry.getDate();
                mCallslogWrapper.add(mUri, mType, mDuration, mDate);
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mUri.toString()+ " " + mType +" "+ " " +mDuration +" " + mDate);
                break;
            }
            case R.id.callslog_fetchcallslog_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_fetchcallslogtype_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch(1);
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_fetchcallslogtypenumber_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch(1,"84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_fetchcallslognumber_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_fetchIncomingByNumber_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetchIncomingByNumber("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_fetchOutgoingByNumber_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetchOutgoingByNumber("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mResult);
                break;
            }
            case R.id.callslog_CallslogregisterListener_id: {
                mCallslogWrapper.registerListener(new IOnCallLogChangedListener() {

                    @Override
                    public void onCallLogChanged() {
                        Log.d("CallslogregisterListener : ", "onCallLogChanged");
                        ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText("CallslogregisterListener : " + "onCallLogChanged");
                    }
                });
                break;
            }
            case R.id.callslog_removecallslog_id: {
                mCallslogWrapper.remove();
                break;
            }
            case R.id.callslog_removecallslogentry_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mArrayCallsLogEntry[0] + "removed");
                mCallslogWrapper.remove(mArrayCallsLogEntry[0]);
                break;
            }
            case R.id.callslog_removecallslogtype_id: {
                mCallslogWrapper.remove(1);
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText("Calls Log type = 1 was removed");
                break;
            }
            case R.id.callslog_RemovecallslogTypeNumber_id: {
                mCallslogWrapper.remove(1,"84933094998");
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText("Calls Logtype = 1 and number = 84933094998 were removed");
                break;
            }
            case R.id.callslog_Removecallslognumber_id: {
                mCallslogWrapper.remove("84933094998");
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText("Calls Log number = 84933094998 were removed");
                break;
            }
            case R.id.callslog_removeGroupcallslog_id: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                mCallslogWrapper.removeGroup(mArrayCallsLogEntry[0]);
                ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText(mArrayCallsLogEntry[0] + "removed");
                break;
            }
            case R.id.callslog_unregisterListenercalslog_id: {
                mCallslogWrapper.unregisterListener(new IOnCallLogChangedListener() {

                    @Override
                    public void onCallLogChanged() {
                        Log.d("unregisterListenercalslog : ","onCallLogChanged");
                        ((TextView) findViewById(R.id.callslog_tv_CallsLogResult_id)).setText("unregisterListenercalslog : " + "onCallLogChanged");
                    }
                });
                break;
            }
            default:
                break;
        }
    }
}
