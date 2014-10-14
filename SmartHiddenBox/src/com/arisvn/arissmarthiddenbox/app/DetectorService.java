package com.arisvn.arissmarthiddenbox.app;

import com.arisvn.arissmarthiddenbox.util.Utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.util.Log;

public class DetectorService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.e(Utils.TAG, getClass().getName()+"::onCreate");

	}

	@Override
	public void onDestroy() {
		// debug: log.i("Detector","Service.Ondestroy");
		Log.e(Utils.TAG, getClass().getName()+"::onDestroy");
		mThread.interrupt();

		// Make sure our notification is gone.
	}

	// This is the old onStart method that will be called on the pre-2.0
	// platform. On 2.0 or later we override onStartCommand() so this
	// method will not be called.
	@Override
	public void onStart(Intent intent, int startId) {
		// debug: log.i("Detector","Service.Onstart");
		Log.e(Utils.TAG, getClass().getName()+"::onStart");
		handleCommand(intent);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// debug: log.i("Detector","Service.OnStartCommand");
		Log.e(Utils.TAG, getClass().getName()+"::onStartCommand");
		handleCommand(intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return Service.START_STICKY;
	}

	private void handleCommand(Intent intent) {
		startMonitorThread((ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE));
	}

	private void startMonitorThread(final ActivityManager am) {
		if (mThread != null)
			mThread.interrupt();

		mThread = new MonitorlogThread(new ActivityStartingHandler(this));
		mThread.start();
	}

	private static Thread mThread;

	private class MonitorlogThread extends Thread {

		ActivityStartingListener mListener;

		public MonitorlogThread(ActivityStartingListener listener) {
			// debug: log.i("Detector","Monitor//debug: logThread");
			mListener = listener;
		}

		@Override
		public void run() {

			// debug: log.i("Detector","RUN!");

			while (!this.isInterrupted()) {

				try {
					Thread.sleep(100);
					// //debug: log.i("Detector","try!");
					// This is the code I use in my service to identify the
					// current foreground application, its really easy:

					ActivityManager am = (ActivityManager) getBaseContext()
							.getSystemService(ACTIVITY_SERVICE);
					// The first in the list of RunningTasks is always the
					// foreground task.
					RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1)
							.get(0);

					// Thats it, then you can easily access details of the
					// foreground app/activity:
					String foregroundTaskPackageName = foregroundTaskInfo.topActivity
							.getPackageName();
					PackageManager pm = getBaseContext().getPackageManager();
					PackageInfo foregroundAppPackageInfo = null;
					String foregroundTaskActivityName = foregroundTaskInfo.topActivity
							.getShortClassName().toString();
					try {
						foregroundAppPackageInfo = pm.getPackageInfo(
								foregroundTaskPackageName, 0);
					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (mListener != null) {
						mListener.onActivityStarting(
								foregroundAppPackageInfo.packageName,
								foregroundTaskActivityName);
					}

				} catch (InterruptedException e) {
					// good practice
					Thread.currentThread().interrupt();
					return;
				}
			}

		}
	}
}
