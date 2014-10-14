/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: NhuHoang
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.sms - SMSFragment.java
 * Date create: 2:31:00 PM - Feb 26, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.sms;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.database.SQLiteAdapter;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.ConfirmDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.SMSData;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.sms.adapter.SMSListAdapter;
import com.arisvn.arissmarthiddenbox.util.HiddenBoxDBUtil;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;
import com.arisvn.arissmarthiddenbox.util.ZipUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SMSFragment.
 */
public class SMSFragment extends HiddenBaseFragment {

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        objs = new ArrayList<BaseItemEntity>();
        super.onAttach(activity);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#unHide()
     */
    @Override
    public void unHide() {
        // TODO Auto-generated method stub
        new UnHiddenItemsTask().execute();
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#delete()
     */
    @Override
    public void delete() {
        // TODO Auto-generated method stub

        onDeleteSMS();

    }

    /**
     * On delete sms.
     */
    private void onDeleteSMS() {
        // TODO Auto-generated method stub
        new DeleteItemsTask().execute();
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#pickerAction()
     */
    @Override
    public void pickerAction() {
        // TODO Auto-generated method stub
        SMSPickerFragment smsPickerFragment = new SMSPickerFragment();
        Bundle bundle = new Bundle();
        smsPickerFragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, smsPickerFragment, Utils.SMS_PICKER);
        transaction.addToBackStack(Utils.SMS_PICKER);
        transaction.commit();
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#makeFolder()
     */
    @Override
    public void makeFolder() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onLoadHiddenItems()
     */
    @Override
    public void onLoadHiddenItems() {
        // TODO Auto-generated method stub
        new LoadHiddenItemsTask().execute();
    }

    /**
     * The Class LoadHiddenItemsTask.
     */
    class LoadHiddenItemsTask extends AsyncTask<Void, Void, List<BaseItemEntity>> {

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
            List<BaseItemEntity> list = new ArrayList<BaseItemEntity>();
            if (flagChangeData) {
                SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(getActivity());
                list = new ArrayList<BaseItemEntity>(sqLiteAdapter.getAllSMS());
                sqLiteAdapter.close();
                return list;
            } else {
                return objs;
            }
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

    /**
     * The Class BackUpAndRestoreSMSTask.
     */
    class BackUpAndRestoreSMSTask extends AsyncTask<String, Void, Boolean> {

        /** The params. */
        private String[] params;

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SynDialogFragment.show(getActivity());
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (params != null && params.length == 2) {
                this.params = params;
                String filePath = params[0];
                int mode = Integer.parseInt(params[1]);
                switch (mode) {
                    case Utils.EXPORT:
                        @SuppressWarnings("unchecked")
                        List<SMSData> smsDatas = (List<SMSData>)mAdapter.getSelectedItem();
                        List<File> files = new ArrayList<File>();
                        Utils.writeObject(Utils.FOLDER + "/" + Utils.BACKUP_SQL, smsDatas);
                        File sqlFile = new File(Utils.FOLDER + "/" + Utils.BACKUP_SQL);
                        if (sqlFile.exists()) {
                            files.add(sqlFile);
                            if (ZipUtil.compress(files, filePath)) {
                                return Utils.encrypt(getActivity(), filePath,
                                        Encryption.BYTES_TO_ENCRYPT_SMS);
                            } else {
                                File file = new File(filePath);
                                file.delete();
                                ConfirmDialogFragment.show(getActivity(),
                                        String.format(getString(R.string.backup_error), filePath));
                            }
                        }
                        break;
                    case Utils.IMPORT:
                        if (Utils.decrypt(getActivity(), filePath)) {
                            if (ZipUtil.extract(new File(filePath), Utils.RESTORE_FOLDER)) {
                                File file = new File(Utils.RESTORE_FOLDER + "/" + Utils.BACKUP_SQL);
                                if (file.exists()) {
                                    List<? extends BaseItemEntity> SMSRestore = Utils
                                            .readObject(file.getAbsolutePath());
                                    if (SMSRestore != null && SMSRestore.size() > 0) {
                                        for (BaseItemEntity baseItemEntity : SMSRestore) {
                                            if (baseItemEntity instanceof SMSData) {
                                                SMSData sms = (SMSData)baseItemEntity;
                                                // insert db local and db sdcard
                                                if (!App.getDB().isExistSMS(sms)) {
                                                    App.getDB().insertSMS(sms);
                                                    HiddenBoxDBUtil.getInstance()
                                                    .insertSMSSdcardDB(sms);
                                                }
                                            }

                                        }
                                        Utils.encrypt(getActivity(), filePath,
                                                Encryption.BYTES_TO_ENCRYPT_SMS);
                                        return true;
                                    } else {
                                        Utils.encrypt(getActivity(), filePath,
                                                Encryption.BYTES_TO_ENCRYPT_SMS);
                                        Log.e(Utils.TAG, "Can not read fileItems in backup file");
                                        ConfirmDialogFragment.show(getActivity(), String
                                                .format(getString(R.string.restore_error_nofiles),
                                                        filePath));
                                    }

                                } else {
                                    Utils.encrypt(getActivity(), filePath,
                                            Encryption.BYTES_TO_ENCRYPT_SMS);
                                    Log.e(Utils.TAG, Utils.RESTORE_FOLDER + "/" + Utils.BACKUP_SQL
                                            + " is not exist");
                                    ConfirmDialogFragment.show(getActivity(), String.format(
                                            getString(R.string.restore_error), filePath));
                                }
                            } else {
                                // Can not extract back up file
                                Utils.encrypt(getActivity(), filePath,
                                        Encryption.BYTES_TO_ENCRYPT_SMS);
                                ConfirmDialogFragment.show(getActivity(),
                                        String.format(getString(R.string.restore_error), filePath));
                            }
                        } else {
                            Log.e(Utils.TAG, "filePath :" + filePath
                                    + " is not a backup file for this app");
                            ConfirmDialogFragment.show(getActivity(),
                                    String.format(getString(R.string.restore_error), filePath));
                        }
                        break;
                    default:
                        break;
                }
            }
            return false;
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

            // empty restore folder
            File restoreFolder = new File(Utils.RESTORE_FOLDER);
            Utils utils = new Utils();
            utils.emptyRestoreFolder(restoreFolder.getAbsolutePath());
            if (result && params != null && params.length == 2) {
                int mode = Integer.parseInt(params[1]);
                switch (mode) {
                    case Utils.EXPORT:
                        File sqlFile = new File(Utils.FOLDER + "/" + Utils.BACKUP_SQL);
                        if (sqlFile.exists()) {
                            sqlFile.delete();
                        }
                        break;
                    case Utils.IMPORT:
                        onLoadHiddenItems();
                        if (gridView.getVisibility() == View.VISIBLE) {
                            updateUI(gridView);
                        } else {
                            updateUI(listView);
                        }
                        if (itemChangeListener != null) {
                            itemChangeListener.onFinishLoadItems();
                        }
                        break;

                    default:
                        break;
                }
            }
            SynDialogFragment.dismiss(getActivity());
        }
    }

    /**
     * The Class DeleteItemsTask.
     */
    class DeleteItemsTask extends AsyncTask<Void, Void, Boolean> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SynDialogFragment.show(getActivity(), getString(R.string.delete));
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub
            if (flagChangeData) {

                for (Iterator<BaseItemEntity> iterator = objs.iterator(); iterator.hasNext();) {
                    SMSData sms = (SMSData)iterator.next();
                    if (!sms.isCheck())
                        continue;
                    // insert sms to device
                    sms.setIsRead(0);
                    // delete sms to db
                    App.getDB().deleteSMS(sms);
                    HiddenBoxDBUtil.getInstance().deleteSMS(sms);
                    iterator.remove();
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
            SynDialogFragment.dismiss(getActivity());
            if (itemChangeListener != null) {
                itemChangeListener.onSelectedItemChanged(mAdapter.getSelectedItem().size());
            }
            setBackgroundDeselectAll();
        }

    }

    /**
     * The Class UnHiddenItemsTask.
     */
    class UnHiddenItemsTask extends AsyncTask<Void, Void, Boolean> implements OnCancelListener{
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
                		SMSData sms = (SMSData)iterator.next();
                        if (!sms.isCheck())
                            continue;
                        // insert sms to device
                        sms.setIsRead(0);
                        Utils.restoreSms(getActivity(), sms);
                        // delete sms to db
                        App.getDB().deleteSMS(sms);
                        HiddenBoxDBUtil.getInstance().deleteSMS(sms);
                        iterator.remove();
					} else {
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
            setBackgroundDeselectAll();
        }
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			isCancel=true;
		}

    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#showGridMode()
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
        mAdapter = new SMSListAdapter(getActivity(), objs, Utils.TYPE_CONTACT);
        listView.setAdapter(mAdapter);
        gridView.removeAllViewsInLayout();
        SaveData.getInstance(getActivity()).setShowList(true);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onOpen(int)
     */
    @Override
    protected void onOpen(int position) {
        // TODO Auto-generated method stub
        App.needShowLogin = false;
        Intent intent = new Intent(getActivity(), SMSDetailActivity.class);
        intent.putExtra(Utils.POSISITON_SMS, position);
        intent.putExtra(Utils.CALL_LOG_NUMBER, ((SMSData)objs.get(position)).getNumber());
        startActivityForResult(intent, Utils.OPEN_SMS_DETAIL);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onBackup(java.lang.String[])
     */
    @Override
    protected void onBackup(String[] params) {
        // TODO Auto-generated method stub
        new BackUpAndRestoreSMSTask().execute(params);
    }

    /* (non-Javadoc)
     * @see com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment#onRestore(java.lang.String[])
     */
    @Override
    protected void onRestore(String[] params) {
        // TODO Auto-generated method stub
        new BackUpAndRestoreSMSTask().execute(params);

    }

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return getString(R.string.SMS);
	}
	@Override
	protected void disableModeView(ToggleButton modeView) {
		// TODO Auto-generated method stub
		if (modeView!=null) {
			modeView.setVisibility(View.GONE);
		}
		super.disableModeView(modeView);
	}
}
