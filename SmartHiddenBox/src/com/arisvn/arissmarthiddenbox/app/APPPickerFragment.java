package com.arisvn.arissmarthiddenbox.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.app.adapter.APPGridAdapter;
import com.arisvn.arissmarthiddenbox.app.adapter.APPListAdapter;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.SortDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.entity.AppItem;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.util.Compartor;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;
public class APPPickerFragment extends HiddenBasePickerFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new LoadApplicationTask().execute();
    }

    @Override
    public void hideFile() {
        // TODO Auto-generated method stub
		new HideAppTask().execute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment#showGridMode
	 * ()
	 */
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

	private class LoadApplicationTask extends
			AsyncTask<Void, Void, List<BaseItemEntity>> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	        SynDialogFragment.show(getActivity(), getString(R.string.loading));
		}
		@Override
		protected List<BaseItemEntity> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<BaseItemEntity> arrayList = new ArrayList<BaseItemEntity>();
			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

			List<ResolveInfo> mApps = getActivity().getPackageManager()
					.queryIntentActivities(mainIntent, 0);
			List<String> hiddenApps = App.getDB().getHiddenApps();
			int len = hiddenApps.size();

			int length = mApps.size();
			ArrayList<AppItem> appItems=new ArrayList<AppItem>();
			for (int i = 0; i < length; i++) {
				boolean isLock = false;
				ResolveInfo info = mApps.get(i);
				Drawable image = info.loadIcon(getActivity()
						.getPackageManager());
				for (int j = 0; j < len; j++) {
					if (info.activityInfo.packageName.equals(hiddenApps.get(j))) {
						// app have been locked
						isLock = true;
						break;
					}
				}
				if (isLock)
					continue;
				AppItem appItem = new AppItem(
						info.activityInfo.loadLabel(
								getActivity().getPackageManager()).toString(),
						info.activityInfo.name, info.activityInfo.packageName,
						image,0);
				appItems.add(appItem);

			}
			if (appItems!=null&&appItems.size()>0) {
				arrayList.addAll(appItems);
			}
			return arrayList;
		}

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
     * The Class hideCallLogTask.
     */
    class HideAppTask extends AsyncTask<Void, Void, Boolean> implements OnCancelListener{
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
			cancelableDialogFrament.show(getActivity(),
					getString(R.string.hiding), this);
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                for (Iterator<? extends BaseItemEntity> iterator = objs.iterator(); iterator
                        .hasNext();) {
                	if (!isCancel) {
                		  AppItem  appItem = (AppItem)iterator.next();
                          if (!appItem.isCheck())
                              continue;
                          // insert db
                          if (!App.getDB().isExistApp( appItem)) {
                              App.getDB().insertHiddenApp(appItem.getPackageName());
                          }
					} else {
						Log.d("Tamle", "Cancel HideAppTask by users");
						break;
					}
                  
                }
            } catch (Exception e) {
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
            if (getActivity() != null) {
                cancelableDialogFrament.dismiss(getActivity());
                List<String> strings=App.getDB().getHiddenApps();
                if (strings!=null&&strings.size()>0) {
    				Intent intent=new Intent(getActivity(), DetectorService.class);
    				getActivity().startService(intent);
    			}
                try {
                    if (result) {
                        if (itemChangeListener != null)
                            itemChangeListener.onFinishLoadItems();
                        getActivity().onBackPressed();
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e(Utils.TAG, "HideFilesTask::onPostExecute: " + e.toString());
                }

            }
        }

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			isCancel=true;
			
		}

    }
    @Override
    protected void onOpen(int position) {
        // TODO Auto-generated method stub
        if(objs.get(position)instanceof AppItem){
            AppItem appItem =(AppItem)objs.get(position);
            Intent LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(appItem.getPackageName());
            startActivity( LaunchIntent );
        }

    }

	@Override
	protected void showSortDialog() {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(
				SortDialogFragment.TAG);
		if (prev != null) {
//			ft.remove(prev);
			return;
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		DialogFragment newFragment = SortDialogFragment.newInstance(this,true,false,false);
		newFragment.show(ft, SortDialogFragment.TAG);
		
	}

	@Override
	public void sort(SortDirection sortDirection, SortCondition sortCondition) {
		// TODO Auto-generated method stub
		boolean isAsc=false;
		switch (sortDirection) {
		case ASC:
			isAsc=true;
			break;
		case DESC:
			isAsc=false;
			break;
		default:
			break;
		}
		Compartor.isAsc = isAsc;
		switch (sortCondition) {
		case NAME:
			Collections.sort(objs, Compartor.compareAppName);
			break;
		default:
			break;
		}
		if (gridView.getVisibility()==View.VISIBLE) {
			updateUI(gridView);
		} else {
			updateUI(listView);
		}
		
	}

}
