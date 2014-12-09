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

    /** The m date. */
    private Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callslog);

        Init();
    }

    private void Init() {

        mCallslogWrapper = APIHelper.getInstance().getCallslogWrapper();
        // execute button
        findViewById(R.id.addcallslog).setOnClickListener(this);
        findViewById(R.id.fetchcallslog).setOnClickListener(this);
        findViewById(R.id.fetchcallslogtype).setOnClickListener(this);

        findViewById(R.id.fetchcallslogtypenumber).setOnClickListener(this);
        findViewById(R.id.fetchcallslognumber).setOnClickListener(this);
        findViewById(R.id.fetchIncomingByNumber).setOnClickListener(this);

        findViewById(R.id.fetchOutgoingByNumber).setOnClickListener(this);
        findViewById(R.id.CallslogregisterListener).setOnClickListener(this);
        findViewById(R.id.removecallslog).setOnClickListener(this);

        findViewById(R.id.removecallslogentry).setOnClickListener(this);
        findViewById(R.id.removecallslogtype).setOnClickListener(this);
        findViewById(R.id.RemovecallslogTypeNumber).setOnClickListener(this);

        findViewById(R.id.Removecallslognumber).setOnClickListener(this);
        findViewById(R.id.removeGroupcallslog).setOnClickListener(this);
        findViewById(R.id.unregisterListenercalslog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addcallslog: {
                //                ContactsWrapper mContactsWrapper = APIHelper.getInstance().getContactsWrapper();
                //                Contact iContact = mContactsWrapper.fetch("84933094998", false);
                //                CallsLogEntry mCallsLogEntry = new CallsLogEntry();
                //                mUri = iContact.getSipUri();
                //                Log.d("SipUri :", mUri.toString());
                //                mType = 1;
                //                mDuration = 1;
                //                mDate = mCallsLogEntry.getDate();
                mCallslogWrapper.add(mUri, mType, mDuration, mDate);
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mUri.toString()+ " " + mType +" "+ " " +mDuration +" " + mDate);
                break;
            }
            case R.id.fetchcallslog: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.fetchcallslogtype: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch(0);
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.fetchcallslogtypenumber: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch(1,"84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.fetchcallslognumber: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.fetchIncomingByNumber: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetchIncomingByNumber("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.fetchOutgoingByNumber: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetchOutgoingByNumber("84933094998");
                String mResult = "";
                int len = mArrayCallsLogEntry.length;
                for(int i=0 ;i<len ;i++)
                {
                    mResult += mArrayCallsLogEntry[i].toString() + "\n";
                }
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mResult);
                break;
            }
            case R.id.CallslogregisterListener: {
                mCallslogWrapper.registerListener(new IOnCallLogChangedListener() {

                    @Override
                    public void onCallLogChanged() {
                        Log.d("CallslogregisterListener : ", "onCallLogChanged");
                        ((TextView) findViewById(R.id.tv_CallsLogResult)).setText("CallslogregisterListener : " + "onCallLogChanged");
                    }
                });
                break;
            }
            case R.id.removecallslog: {
                mCallslogWrapper.remove();
                break;
            }
            case R.id.removecallslogentry: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mArrayCallsLogEntry[0] + "removed");
                mCallslogWrapper.remove(mArrayCallsLogEntry[0]);
                break;
            }
            case R.id.removecallslogtype: {
                mCallslogWrapper.remove(1);
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText("Calls Log type = 1 was removed");
                break;
            }
            case R.id.RemovecallslogTypeNumber: {
                mCallslogWrapper.remove(1,"84933094998");
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText("Calls Logtype = 1 and number = 84933094998 were removed");
                break;
            }
            case R.id.Removecallslognumber: {
                mCallslogWrapper.remove("84933094998");
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText("Calls Log number = 84933094998 were removed");
                break;
            }
            case R.id.removeGroupcallslog: {
                CallsLogEntry[] mArrayCallsLogEntry = mCallslogWrapper.fetch();
                mCallslogWrapper.removeGroup(mArrayCallsLogEntry[0]);
                ((TextView) findViewById(R.id.tv_CallsLogResult)).setText(mArrayCallsLogEntry[0] + "removed");
                break;
            }
            case R.id.unregisterListenercalslog: {
                mCallslogWrapper.unregisterListener(new IOnCallLogChangedListener() {

                    @Override
                    public void onCallLogChanged() {
                        Log.d("unregisterListenercalslog : ","onCallLogChanged");
                        ((TextView) findViewById(R.id.tv_CallsLogResult)).setText("unregisterListenercalslog : " + "onCallLogChanged");
                    }
                });
                break;
            }
            default:
                break;
        }
    }
}
