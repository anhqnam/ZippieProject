
package com.arisvn.arissmarthiddenbox.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.app.adapter.APPGridAdapter;
import com.arisvn.arissmarthiddenbox.app.adapter.APPListAdapter;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.entity.AppItem;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

public class APPFragment extends HiddenBaseFragment {

    @Override
    protected void onOpen(int position) {
        // TODO Auto-generated method stub
		Log.d(Utils.TAG, getClass().getName()+" not support onOpen function");
    }

    @Override
    public void unHide() {
        // TODO Auto-generated method stub
		   new UnHiddenItemsTask().execute();

    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
		Log.d(Utils.TAG, getClass().getName()+" not support delete function");

    }

    @Override
    public void pickerAction() {
        // TODO Auto-generated method stub
    	   // TODO Auto-generated method stub
        APPPickerFragment appPickerFragment = new APPPickerFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, appPickerFragment, Utils.APP_PICKER);
        transaction.addToBackStack(Utils.APP_PICKER);
        transaction.commit();
    }

    @Override
    public void makeFolder() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadHiddenItems() {
        // TODO Auto-generated method stub
		new LoadHiddenItemsTask().execute();
    }

    @Override
    protected void onBackup(String[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onRestore(String[] params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showGridMode() {
        gridView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        mAdapter = null;
        mAdapter = new APPGridAdapter(getActivity(), objs, Utils.TYPE_THUMBNAIL);
        gridView.setAdapter(mAdapter);
        listView.removeAllViewsInLayout();

        SaveData.getInstance(getActivity()).setShowList(false);
    }

    /**
     * Display format's listview.
     */
    @Override
    public void showListMode() {
        listView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        mAdapter = new APPListAdapter(getActivity(), objs, Utils.TYPE_THUMBNAIL);
        listView.setAdapter(mAdapter);
        gridView.removeAllViewsInLayout();

        SaveData.getInstance(getActivity()).setShowList(true);
    }

	/**
	 * The Class LoadHiddenItemsTask.
	 */
	class LoadHiddenItemsTask extends
			AsyncTask<Void, Void, List<BaseItemEntity>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SynDialogFragment.show(getActivity(), getString(R.string.loading));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected List<BaseItemEntity> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<BaseItemEntity> list = new ArrayList<BaseItemEntity>();
			if (flagChangeData) {
				List<String> hiddenApps = App.getDB().getHiddenApps();
				if (hiddenApps==null||hiddenApps.isEmpty()) {
					return list;
				}
				Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

				List<ResolveInfo> mApps = getActivity().getPackageManager()
						.queryIntentActivities(mainIntent, 0);
				int len = hiddenApps.size();

				int length = mApps.size();
				ArrayList<AppItem> appItems = new ArrayList<AppItem>();
				for (int i = 0; i < length; i++) {
					boolean isLock = false;
					ResolveInfo info = mApps.get(i);
					Drawable image = info.loadIcon(getActivity()
							.getPackageManager());
					for (int j = 0; j < len; j++) {
						if (info.activityInfo.packageName.equals(hiddenApps
								.get(j))) {
							// app have been locked
							isLock = true;
							break;
						}
					}
					if (!isLock)
						continue;
					AppItem appItem = new AppItem(info.activityInfo.loadLabel(
							getActivity().getPackageManager()).toString(),
							info.activityInfo.name,
							info.activityInfo.packageName, image, 0);
					appItems.add(appItem);

				}
				if (appItems != null && appItems.size() > 0) {
					list.addAll(appItems);
				}
				return list;
			} else {
				return objs;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(List<BaseItemEntity> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			SynDialogFragment.dismiss(getActivity());
			objs = new ArrayList<BaseItemEntity>();
			synchronized (objs) {
				if (result.size() != objs.size()) {
					if (objs != null) {
						objs.clear();
					}
					objs.addAll(result);
				}

			}
			if (gridView.getVisibility() == View.VISIBLE) {
				updateUI(gridView);
			} else {
				updateUI(listView);
			}
			showMode();
			if (itemChangeListener != null) {
				itemChangeListener.onFinishLoadItems();
			}

		}

	}
	 /**
     * The Class UnHiddenItemsTask.
     */
    class UnHiddenItemsTask extends AsyncTask<Void, Void, Boolean> implements  OnCancelListener{
    	 /** The is cancel. */
        private boolean isCancel = false;

        /** The dialog picker frament. */
        private CancelableDialogFrament cancelableDialogFrament;

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            cancelableDialogFrament = new CancelableDialogFrament();
            cancelableDialogFrament.show(getActivity(), getString(R.string.unhiding), this);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
            if (flagChangeData) {
                for (Iterator<BaseItemEntity> iterator = objs.iterator(); iterator.hasNext();) {
                	if (!isCancel) {
                		  AppItem  appItem = (AppItem)iterator.next();
                          if (!appItem.isCheck())
                              continue;
                          // delete sms to db
                          App.getDB().unHideApp(appItem.getPackageName());
//                          HiddenBoxDBUtil.getInstance().unHideAppSDcard(appItem.getPackageName());
                          iterator.remove();
                	}else {
                		Log.d("Tamle", "Cancel UnHiddenItemsTask by users");
						break;
					}
                  
                }

            } else {
                return false;
            }
            return true;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (getActivity() == null) {
                return;
            }
            mAdapter.notifyDataSetChanged();
            cancelableDialogFrament.dismiss(getActivity());
            if (itemChangeListener != null) {
                itemChangeListener.onFinishLoadItems();
            }
            List<String> strings=App.getDB().getHiddenApps();
            if (strings!=null&&strings.size()==0) {
				Intent intent=new Intent(getActivity(), DetectorService.class);
				getActivity().stopService(intent);
			}
            setBackgroundDeselectAll();
        }

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			isCancel=true;
			
		}

    }
    @Override
    public void startBackupProgress() {
    	// TODO Auto-generated method stub
    	Log.d(Utils.TAG, getClass().getName()+" not support back up function");
    }
    @Override
    public void startRestoreFilesProgress() {
    	// TODO Auto-generated method stub
    	Log.d(Utils.TAG, getClass().getName()+" not support restore function");
    }

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return getString(R.string.app);
	}

}
