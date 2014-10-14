
package com.arisvn.arissmarthiddenbox.sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.SortDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.sms.adapter.SMSListAdapter;
import com.arisvn.arissmarthiddenbox.util.Compartor;
import com.arisvn.arissmarthiddenbox.util.HiddenBoxDBUtil;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

public class SMSPickerFragment extends HiddenBasePickerFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new LoadSMSTask().execute();
    }

    class LoadSMSTask extends AsyncTask<Void, Void, List<BaseItemEntity>> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
	        SynDialogFragment.show(getActivity(), getString(R.string.loading));
        }

        @Override
        protected List<BaseItemEntity> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            return new ArrayList<BaseItemEntity>(Utils.getListSMSMessage(getActivity()));
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
        mAdapter = new SMSListAdapter(getActivity(), objs, Utils.TYPE_CONTACT);
        listView.setAdapter(mAdapter);
        gridView.removeAllViewsInLayout();
        SaveData.getInstance(getActivity()).setShowList(true);
    }

	class HideSMSTask extends AsyncTask<Void, Void, Boolean> implements
			OnCancelListener {
		/** The is cancel. */
		private boolean isCancel = false;

		/** The dialog picker frament. */
		private CancelableDialogFrament cancelableDialogFrament;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
			cancelableDialogFrament = new CancelableDialogFrament();
			cancelableDialogFrament.show(getActivity(),
					getString(R.string.hiding), this);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
				for (Iterator<? extends BaseItemEntity> iterator = objs
						.iterator(); iterator.hasNext();) {
					if (!isCancel) {
						SMSData sms = (SMSData) iterator.next();
                    if (!sms.isCheck())
                        continue;
                    // insert db
					if (!App.getDB().isExistSMS(sms)) {
                        App.getDB().insertSMS(sms);
							HiddenBoxDBUtil.getInstance()
									.insertSMSSdcardDB(sms);
                        // delete sms on device
                        Utils.deleteSMS(getActivity(), sms);
						} else {
							Log.d("Tamle", "SMS is exist: " + sms.toString());
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
    public void hideFile() {
        // TODO Auto-generated method stub
        // insert database and remove sms on device
        new HideSMSTask().execute();
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
        if (objs.get(position) instanceof SMSData) {
            SMSData smsData = (SMSData)objs.get(position);
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType(Utils.URI_TYPE_SMS);
            smsIntent.putExtra(Utils.NUMBBER, smsData.getNumber());
            startActivity(smsIntent);

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
		DialogFragment newFragment = SortDialogFragment.newInstance(this,true,true,false);
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
			Collections.sort(objs, Compartor.compareNumberSMS);
			break;
		case SIZE:
			Collections.sort(objs, Compartor.compareContentSMS);
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
