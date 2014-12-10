package com.example.sdktest;

import java.util.Vector;

import com.example.sdktest.api.APIHelper;
import com.voipswitch.messages.Message;
import com.voipswitch.messages.MessagesThread;

import unique.packagename.sdkwrapper.messages.IMessagesRepositoryListener;
import unique.packagename.sdkwrapper.messages.MessagesWrapper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MessageActivity extends Activity implements OnClickListener {

	/** The m messages wrapper. */
	private MessagesWrapper mMessagesWrapper;
	
	/** The address. */
	private static String address = "841677113348";

	private String mResult = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		Init();
	}

	private void Init() {
		mMessagesWrapper = APIHelper.getInstance().getMessageWrapper();

		findViewById(R.id.Messagefetch).setOnClickListener(this);
		findViewById(R.id.MessagefetchThread).setOnClickListener(this);
		findViewById(R.id.WaittingConfirmation).setOnClickListener(this);
		findViewById(R.id.MessageModify).setOnClickListener(this);
		findViewById(R.id.MessageRegisterListener).setOnClickListener(this);
		findViewById(R.id.MessageRemoveid).setOnClickListener(this);
		findViewById(R.id.MessageRemoveMessage).setOnClickListener(this);
		findViewById(R.id.MessageRemoveThread).setOnClickListener(this);
		findViewById(R.id.MessagesmsFree).setOnClickListener(this);
		findViewById(R.id.MessagesmsPaid).setOnClickListener(this);
		findViewById(R.id.MessageunregisterListener).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Messagefetch:
			((TextView) findViewById(R.id.tvMessageResult)).setText("");
			Vector<Message> listMessage = mMessagesWrapper.fetch(address);
			((TextView) findViewById(R.id.tvMessageResult)).setText(listMessage.toString());
			Log.d("Message Adress : ", listMessage.toString());
			break;
		case R.id.MessagefetchThread: {
			((TextView) findViewById(R.id.tvMessageResult)).setText("");
			Thread thread = new Thread() {
				@Override
				public void run() {
					final Vector<MessagesThread> listMessageThread = mMessagesWrapper
							.fetchThreads();
					for (int i = 0; i < listMessageThread.size(); i++) {
						mResult += listMessageThread.get(i).getAddress()+ " " + listMessageThread.get(i).getMessage(i);
					}
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.d("Message Thread : ",
									listMessageThread.toString());
							((TextView) findViewById(R.id.tvMessageResult))
									.setText(mResult);
						}
					});
				}
			};
			thread.start();
			break;
		}
		case R.id.WaittingConfirmation:
			Message msg1 = mMessagesWrapper.fetch(address).get(0);
			boolean message = mMessagesWrapper.isWaitingForConfirmation(msg1);
			Log.d("WaittingConfirmation", message + "");
			break;
		case R.id.MessageModify:
			Message msg2 = mMessagesWrapper.fetch(address).get(0);
			mMessagesWrapper.modify(msg2);
			break;
		case R.id.MessageRegisterListener:
			mMessagesWrapper.registerListener(new IMessagesRepositoryListener() {

						@Override
						public void onMessageRepositoryChanged(int arg0) {
							Log.d("onMessageRepositoryChanged registerListener", arg0 + "");
						}

						@Override
						public void onMessageChanged(Message arg0) {
							Log.d("onMessageChanged registerListener", arg0 + "");
						}
					});
			break;
		case R.id.MessageRemoveid:
			mMessagesWrapper.remove(1917965);
			break;
		case R.id.MessageRemoveMessage:
			Message msg3 = mMessagesWrapper.fetch(address).get(0);
			mMessagesWrapper.remove(msg3);
			break;
		case R.id.MessageRemoveThread:
			MessagesThread thread = mMessagesWrapper.fetchThreads().get(0);
			mMessagesWrapper.remove(thread);
			break;
		case R.id.MessagesmsFree:
			// mMessagesWrapper.smsFree(getApplicationContext(), uri);
			break;
		case R.id.MessagesmsPaid:
			// mMessagesWrapper.smsPaid(getApplicationContext(), uri);
			break;
		case R.id.MessageunregisterListener:
			mMessagesWrapper.unregisterListener(new IMessagesRepositoryListener() {

						@Override
						public void onMessageRepositoryChanged(int arg0) {
							Log.d("onMessageRepositoryChanged unregisterListener", arg0 + "");
						}

						@Override
						public void onMessageChanged(Message arg0) {
							Log.d("onMessageChanged unregisterListener", arg0 + "");
						}
					});
			break;
		default:
			break;
		}
	}

}
