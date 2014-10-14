/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.calllog - CallLogPickerFragment.java
 * Date create: 5:33:13 PM - Feb 26, 2014 - 2014
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.calllog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.calllog.adapter.CallLogListAdapter;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.SortDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.CallLogEntry;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.util.Compartor;
import com.arisvn.arissmarthiddenbox.util.HiddenBoxDBUtil;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class CallLogPickerFragment.
 */
public class CallLogPickerFragment extends HiddenBasePickerFragment {

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new LoadCallLogTask().execute();
    }

    /**
     * The Class LoadCallLogTask.
     */
    class LoadCallLogTask extends AsyncTask<Void, Void, List<BaseItemEntity>> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SynDialogFragment.show(getActivity(), getString(R.string.loading));
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected List<BaseItemEntity> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return new ArrayList<BaseItemEntity>(Utils.getCallLog(getActivity()));
        }

        /* (non-Javadoc)
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

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment#showGridMode()
     */
    @Override
    public void showGridMode() {
      showListMode();
    }

    /**
     * Display format's listview.
     */
    @Override
    public void showListMode() {
        listView.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        mAdapter = new CallLogListAdapter(getActivity(), objs, Utils.TYPE_CONTACT);
        listView.setAdapter(mAdapter);
        gridView.removeAllViewsInLayout();

        SaveData.getInstance(getActivity()).setShowList(true);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment#hideFile()
     */
    @Override
    public void hideFile() {
        // TODO Auto-generated method stub
        new HideCallLogTask().execute();
    }

    /**
     * The Class hideCallLogTask.
     */
	class HideCallLogTask extends AsyncTask<Void, Void, Boolean> implements
			OnCancelListener {
		/** The is cancel. */
		private boolean isCancel = false;

		/** The dialog picker frament. */
		private CancelableDialogFrament cancelableDialogFrament;

		/*
		 * (non-Javadoc)
		 * 
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

		/*
		 * (non-Javadoc)
		 * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
				for (Iterator<? extends BaseItemEntity> iterator = objs
						.iterator(); iterator.hasNext();) {
					if (!isCancel) {
						CallLogEntry callLogEntry = (CallLogEntry) iterator
								.next();
                    if (!callLogEntry.isCheck())
                        continue;
                    // insert db
                    if (!Utils.isExistCallLog(getActivity(), callLogEntry)) {
                        App.getDB().insertCallLog(callLogEntry);
							HiddenBoxDBUtil.getInstance()
									.insertCallLogSdcardDB(callLogEntry);
                        // delete sms on device
                        Utils.deleteCallLog(getActivity(), callLogEntry);
						} else {
							Log.d("Tamle", "CallLog is exist : "+callLogEntry.getId());
                    }
					} else {
						Log.d("Tamle", "Cancel HideCallLogTask by users");
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
					Log.e(Utils.TAG,
							"HideFilesTask::onPostExecute: " + e.toString());
				}

        }
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			isCancel = true;
		}

	}

    @Override
    public void disableModeView(View view) {
    	// TODO Auto-generated method stub
    	if (view!=null) {
			view.setVisibility(View.GONE);
		}
    	super.disableModeView(view);
    }

    @Override
    protected void onOpen(int position) {
        // TODO Auto-generated method stub

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
		DialogFragment newFragment = SortDialogFragment.newInstance(this,true,false,true);
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
			  Collections.sort(objs, Compartor.compareNumberCallLog);
			  break;
		case DATE:
			  Collections.sort(objs, Compartor.compareDateTimeCallLog);
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
