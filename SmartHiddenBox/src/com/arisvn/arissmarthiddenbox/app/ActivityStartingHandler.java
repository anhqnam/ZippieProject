package com.arisvn.arissmarthiddenbox.app;

import java.util.List;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.LoginActivity;
import com.arisvn.arissmarthiddenbox.util.Utils;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ActivityStartingHandler implements ActivityStartingListener {
	private Context mContext;
	private ActivityManager mAm;
	private String lastRunningPackage;
	private String lockScreenActivityName;

	public ActivityStartingHandler(Context context) {
		mContext = context;
		mAm = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		lastRunningPackage = getRunningPackage();
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String packagename = intent
						.getStringExtra(LoginActivity.EXTRA_PACKAGE_NAME);
				lastRunningPackage = packagename;
			}
		}, new IntentFilter(LoginActivity.ACTION_APPLICATION_PASSED));
		lockScreenActivityName = ".LoginActivity" ;
	}

	private String getRunningPackage() {
		List<RunningTaskInfo> infos = mAm.getRunningTasks(1);
		if (infos.size() < 1)
			return null;
		RunningTaskInfo info = infos.get(0);
		return info.topActivity.getPackageName();
	}

	public void onActivityStarting(String packageName, String activityName) {
		synchronized (this) {

			// Putting the lastRunningPackage up makes applocker's preferences
			// activties
			// not getting locked all the time!
			if (packageName.equals(lastRunningPackage))
				return;
			if (packageName.equals(mContext.getPackageName())){
				// Of course cannot block lock screen
				//debug: //debug: log.i("Detector",activityName);
				//debug: //debug: log.i("Detector",lockScreenActivityName);
				if (activityName.equals(lockScreenActivityName)) return;
			}
			List<String> lockPackageName = App.getDB().getHiddenApps();
			for (int i = 0; i < lockPackageName.size(); i++) {
				if (lockPackageName.get(i).equals(packageName)) {
					blockActivity(packageName, activityName);
					return;
				}
			}
			lastRunningPackage = packageName;
		}
	}

	private void blockActivity(String packageName, String activityName) {
		// Block!
		Log.e(Utils.TAG, getClass().getName()
				+ "::blockActivity:: packageName:" + packageName
				+ ": activityName: " + activityName);
		Intent lockIntent = new Intent(mContext, LoginActivity.class);
		lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		lockIntent.setAction(Utils.ACTION_LOCKAPP);
		lockIntent.putExtra(LoginActivity.BlockedPackageName, packageName);
		mContext.startActivity(lockIntent);
	}

}