
package com.arisvn.arissmarthiddenbox.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Class NetworkUtil. This class is used to listen the network state change
 * and save it into shared_preference file
 */
public class NetworkUtil extends BroadcastReceiver {

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();
            SaveData saveData = SaveData.getInstance(context);
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                saveData.setNetworkStatus(true);
            } else {
                saveData.setNetworkStatus(false);
            }
        }
    }
}
