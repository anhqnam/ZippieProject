package com.arisvn.arissmarthiddenbox.app;

import java.util.List;

import com.arisvn.arissmarthiddenbox.App;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupServiceReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
				|| intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
			List<String> strings = App.getDB().getHiddenApps();
			if (strings != null && strings.size() > 0) {
				// start monitor service if there is any locked APP
				context.startService(new Intent(context, DetectorService.class));
			}
		}
	}
}
