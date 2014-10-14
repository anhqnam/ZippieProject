/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - BaseFragment.java
 * Date create: 2:32:37 PM - Nov 8, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.media;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.FileBrowserActivity;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.dialog.AddMediaItemsDialog;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.ConfirmDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBaseFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.listener.OnItemsAddListener;
import com.arisvn.arissmarthiddenbox.media.adapter.MediaGridAdapter;
import com.arisvn.arissmarthiddenbox.media.adapter.MediaListAdapter;
import com.arisvn.arissmarthiddenbox.media.photo.PhotoDetailFragment;
import com.arisvn.arissmarthiddenbox.media.video.VideoDetailFragment;
import com.arisvn.arissmarthiddenbox.util.HiddenBoxDBUtil;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;
import com.arisvn.arissmarthiddenbox.util.ZipUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseFragment.
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public abstract class MediaBaseFragment extends HiddenBaseFragment implements
		OnItemsAddListener {
    /** The Constant PLAY. */
    public final static int PLAY = 99;
    /** The m type. */
    protected int mType = 0;
    /** The m path. */
    protected String mPath = "";

	/*
	 * (non-Javadoc)
	 * 
     * @see android.support.v4.app.Fragment#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY) {
            App.needShowLogin = false;
            getActivity().supportInvalidateOptionsMenu();
        } else if (requestCode == Utils.REQUEST_FILE) {
            App.needShowLogin = false;
            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra(Utils.FILE_NAME);
                mPath = path;
				try {
					onPicker(mPath);
					mPath = "";
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Tamle",
							"onActivityResult : onPicker: " + e.toString());
				}
			}
		}
	}

    /**
     * Unhide file.
     */
    @Override
    public void unHide() {
        onUnHide();
    }

    /**
     * Delete selected files.
     */
    @Override
    public void delete() {
        // onDelete();
        new DeleteTask().execute();
    }
    /**
     * Create class Restore file using asynctask.
     */
    protected class TaskUnHide extends AsyncTask<Void, Void, Boolean> implements OnCancelListener {
        /** The is cancel. */
        private boolean isCancel = false;

        /** The dialog picker frament. */
        private CancelableDialogFrament cancelableDialogFrament;

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cancelableDialogFrament = new CancelableDialogFrament();
            cancelableDialogFrament.show(getActivity(), getString(R.string.unhiding), this);
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @SuppressLint("NewApi")
        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                for (Iterator<BaseItemEntity> iterator = objs.iterator(); iterator.hasNext();) {
                    if (!isCancel) {
                        FileItem fileItem = (FileItem)iterator.next();
                        if (!fileItem.isCheck())
                            continue;
                        if (Utils.isSDCardExist()) {
							File file = new File(fileItem.getPathNew());
							if (file.exists()) {
								Log.d("Tamle", fileItem.getPathNew()
										+ " is exist");
							} else {
								Log.d("Tamle", fileItem.getPathNew()
										+ " is not exist, skip this file");
								continue;
							}
							if (Utils.decrypt(getActivity(),
									fileItem.getPathNew())) {
								File pathFromFolder = (new File(
										fileItem.getPathFrom()))
                                        .getParentFile();
                                if (!pathFromFolder.exists()) {
                                    pathFromFolder.mkdirs();
                                }
								boolean isMoving = file
										.renameTo(new File(fileItem
												.getPathFrom()));
                                if (isMoving) {
                                    App.getDB().deleteFile(fileItem);
                                    HiddenBoxDBUtil.getInstance().deleteFile(fileItem);
                                    iterator.remove();
                                    MediaScannerConnection.scanFile(getActivity(), new String[] {
                                        fileItem.getPathFrom().toString()
                                    }, null, new MediaScannerConnection.OnScanCompletedListener() {
                                        @Override
                                        public void onScanCompleted(String path, Uri uri) {
                                        }
                                    });
                                } else {
                                    String filePath = fileItem.getPathNew();
                                    int byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;
                                    if (filePath.startsWith(Utils.FOLDER + Utils._AUDIO)) {
                                        byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;

                                    } else if (filePath.startsWith(Utils.FOLDER + Utils._PHOTO)) {
                                        byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;

                                    } else if (filePath.startsWith(Utils.FOLDER + Utils._VIDEO)) {
                                        byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;

                                    }
                                    Utils.encrypt(getActivity(), filePath, byte_to_encrypte);
                                    Log.e(Utils.TAG, "Move fail from " + fileItem.getPathNew()
                                            + " to:  " + fileItem.getPathFrom());
                                }
                            } else {
                                Log.e(Utils.TAG, "Decrypt fail " + fileItem.getPathFrom());
                            }
                        } else {
                            Log.e(Utils.TAG, "processDecrypt faile: there is no SDCard");
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } catch (Exception e1) {
                return false;
            }
            return true;
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (getActivity() == null) {
                return;
            }
            mAdapter.notifyDataSetChanged();
            cancelableDialogFrament.dismiss(getActivity());
            if (itemChangeListener != null) {
                itemChangeListener.onSelectedItemChanged(mAdapter.getSelectedItem().size());
            }
            setBackgroundDeselectAll();
        }

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            isCancel = true;
        }
    }

    /**
     * Picker action.
     */
    @Override
    public void pickerAction() {
    	   if (Utils.isSDCardExist()) {
    	        showDialogPicker();
           } else {
               ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
           }
    }

    // Show dialog to choose mode to show Picker
    /**
     * Show dialog picker.
     */
    public void showDialogPicker() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(
				AddMediaItemsDialog.TAG);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		DialogFragment newFragment = AddMediaItemsDialog.newInstance(this);
		newFragment.show(ft, AddMediaItemsDialog.TAG);

    }

    /**
     * Navigate to picker screen to choose files need to encode.
     */
	private void onPicker(String path) {
		MediaPickerFragment pickerFrg = new MediaPickerFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(Utils.TYPE, mType);
		bundle.putString(Utils.FILE_PATH, path);
		pickerFrg.setArguments(bundle);
		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, pickerFrg,
				Utils.FRAGMENT_PICKER);
		transaction.addToBackStack(Utils.FRAGMENT_PICKER);
		transaction.commitAllowingStateLoss();
	}

    /**
     * Play file.
     * 
     * @param path the path
     */
    @Override
    protected void onOpen(int position) {
        if (Utils.isSDCardExist()) {
			if (mType == Utils.TYPE_PHOTO) {
				PhotoDetailFragment detailFragment = new PhotoDetailFragment();
				Bundle bundle = new Bundle();
				bundle.putInt(Utils.FILE_POSITION, position);
				bundle.putInt(Utils.TYPE, Utils.TYPE_PHOTO);
				detailFragment.setArguments(bundle);
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.fragment_container, detailFragment,
						Utils.PHOTO_DETAIL_FRAGMENT);
				transaction.addToBackStack(Utils.PHOTO_DETAIL_FRAGMENT);
				transaction.commit();
			} else {
				VideoDetailFragment detailFragment = new VideoDetailFragment();
				Bundle bundle = new Bundle();
				bundle.putInt(Utils.FILE_POSITION, position);
				bundle.putInt(Utils.TYPE,mType);
				detailFragment.setArguments(bundle);
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.fragment_container, detailFragment,
						Utils.VIDEO_DETAIL_FRAGMENT);
				transaction.addToBackStack(Utils.VIDEO_DETAIL_FRAGMENT);
				transaction.commit();
			}

		} else {
			ConfirmDialogFragment.show(getActivity(),
					getString(R.string.unmount_sdcard));
        }
    }

    /**
     * Restore files that imported before.
     */
    protected void onUnHide() {
        if (Utils.isSDCardExist()) {
            new TaskUnHide().execute();
        } else {
            ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
        }

    }

    /**
     * Load encoded files.
     */
	private List<FileItem> loadFile() {
		return App.getDB().getAllFile(mType);
	}

    @Override
    public void onLoadHiddenItems() {
        // TODO Auto-generated method stub
        new LoadHiddenFileTask().execute();
    }
    @Override
    public void showGridMode() {
        gridView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        mAdapter = null;
        mAdapter = new MediaGridAdapter(getActivity(), objs, Utils.TYPE_THUMBNAIL);
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
        mAdapter = new MediaListAdapter(getActivity(), objs, Utils.TYPE_THUMBNAIL);
        listView.setAdapter(mAdapter);
        gridView.removeAllViewsInLayout();

        SaveData.getInstance(getActivity()).setShowList(true);
    }
    /**
     * The Class BackUpAndRestoreTask.
     */
    class BackUpAndRestoreTask extends AsyncTask<String, Void, Boolean> {

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
                        List<File> files = new ArrayList<File>();
					List<FileItem> fileItems = new ArrayList<FileItem>();
					for (Iterator<? extends BaseItemEntity> iterator = objs
							.iterator(); iterator.hasNext();) {
						FileItem obj = (FileItem) iterator.next();
						if (!obj.isCheck())
							continue;
						File file = new File(obj.getPathNew());
						if (file.exists()) {
							Log.d("Tamle", file.getAbsolutePath() + " is exist");
						} else {
							Log.d("Tamle",
									file.getAbsolutePath()
											+ " is not exist, skip this file: "
											+ obj.getName());
							// continue;
						}
                            files.add(file);
						String pathFrom = obj.getPathFrom();
                            if (pathFrom.startsWith(Utils.SDCARD)) {
							pathFrom = pathFrom.substring(pathFrom
									.indexOf(Utils.SDCARD)
                                        + Utils.SDCARD.length());
                            }
						obj.setPathFrom(pathFrom);
						String pathTo = obj.getPathNew();
                            if (pathTo.startsWith(Utils.FOLDER)) {
							pathTo = pathTo.substring(pathTo
									.indexOf(Utils.FOLDER)
                                        + Utils.FOLDER.length());
                            }
						obj.setPathNew(pathTo);
						fileItems.add(obj);
					}
					Utils.writeObject(Utils.FOLDER + "/" + Utils.BACKUP_SQL,
							fileItems);
					File sqlFile = new File(Utils.FOLDER + "/"
							+ Utils.BACKUP_SQL);
                        if (sqlFile.exists()) {
                            files.add(sqlFile);
                            if (ZipUtil.compress(files, filePath)) {
                                return Utils.encrypt(getActivity(), filePath,
                                        Encryption.BYTES_TO_ENCRYPT_PHOTO);
                            } else {
                                File file = new File(filePath);
                                file.delete();
							ConfirmDialogFragment.show(getActivity(), String
									.format(getString(R.string.backup_error),
											filePath));
							return false;
                            }
                        }
                        break;
                    case Utils.IMPORT:
                        if (Utils.decrypt(getActivity(), filePath)) {
                            if (ZipUtil.extract(new File(filePath), Utils.RESTORE_FOLDER)) {
                                File file = new File(Utils.RESTORE_FOLDER + "/" + Utils.BACKUP_SQL);
                                if (file.exists()) {
                                    List<? extends BaseItemEntity> fileItemsRestore = Utils.readObject(file
                                            .getAbsolutePath());
                                    if (fileItemsRestore != null && fileItemsRestore.size() > 0) {
                                        File restoreFolder = new File(Utils.RESTORE_FOLDER);
                                        if (restoreFolder.exists()) {
                                            String[] restorefiles = restoreFolder.list();
                                            for (BaseItemEntity baseItemEntity : fileItemsRestore) {
                                                FileItem fileItem=null;
                                                if (baseItemEntity instanceof FileItem) {
                                                    fileItem=(FileItem)baseItemEntity;
                                                } else {
                                                    continue;
                                                }
                                                for (int i = 0; i < restorefiles.length; i++) {
                                                    String fileName = fileItem.getPathNew()
                                                            .substring(
                                                                    fileItem.getPathNew()
                                                                    .lastIndexOf("/") + 1);
                                                    if (fileName.equals(restorefiles[i])) {
                                                        // move file into this
                                                        // location in
                                                        // encrypted folder and
                                                        // insert into
                                                        // DB
                                                        // Append sdcard folder
                                                        // into the start of
                                                        // pathNew and pathFrom
                                                        if (!fileItem.getPathNew().startsWith(
                                                                Utils.FOLDER)) {
                                                            fileItem.setPathNew(Utils.FOLDER
                                                                    + fileItem.getPathNew());
                                                        }
                                                        if (!fileItem.getPathFrom().startsWith(
                                                                Utils.SDCARD)) {
                                                            fileItem.setPathFrom(Utils.SDCARD
                                                                    + fileItem.getPathFrom());
                                                        }
                                                        File filePathNew = new File(
                                                                fileItem.getPathNew());
                                                        File restoreFile = new File(
                                                                Utils.RESTORE_FOLDER + "/"
                                                                        + restorefiles[i]);
                                                        if (filePathNew.exists()
                                                                && !App.getDB()
                                                                .isEncryptedFileExist(
                                                                        fileItem.getPathNew())) {
                                                            filePathNew.delete();
                                                        }
                                                        if (!filePathNew.exists()) {
                                                            // Just move file
                                                            // into
                                                            // encrypted
                                                            // folder if it's
                                                            // not
                                                            // exist now.
                                                            restoreFile.renameTo(filePathNew);
                                                            App.getDB().insertFile(fileItem);
                                                            HiddenBoxDBUtil.getInstance()
                                                            .insertFileSdcardDB(fileItem);
                                                        } else {
                                                            Log.e(Utils.TAG,
                                                                    filePathNew.exists()
                                                                    + " is exist now. Can not restore file");
                                                        }
                                                        break;

                                                    }
                                                }
                                            }
                                            Utils.encrypt(getActivity(), filePath,
                                                    Encryption.BYTES_TO_ENCRYPT_PHOTO);
                                            return true;
                                        } else {
                                            Utils.encrypt(getActivity(), filePath,
                                                    Encryption.BYTES_TO_ENCRYPT_PHOTO);
                                            Log.e(Utils.TAG, "restoreFolder is not exist");
                                            ConfirmDialogFragment.show(getActivity(), String
                                                    .format(getString(R.string.no_restore_folder),
                                                            Utils.RESTORE_FOLDER));
                                        }

                                    } else {
                                        Utils.encrypt(getActivity(), filePath,
                                                Encryption.BYTES_TO_ENCRYPT_PHOTO);
                                        Log.e(Utils.TAG, "Can not read fileItems in backup file");
                                        ConfirmDialogFragment.show(getActivity(), String
                                                .format(getString(R.string.restore_error_nofiles),
                                                        filePath));
                                    }

                                } else {
                                    Utils.encrypt(getActivity(), filePath,
                                            Encryption.BYTES_TO_ENCRYPT_PHOTO);
                                    Log.e(Utils.TAG, Utils.RESTORE_FOLDER + "/" + Utils.BACKUP_SQL
                                            + " is not exist");
                                    ConfirmDialogFragment.show(getActivity(), String.format(
                                            getString(R.string.restore_error), filePath));
                                }
                            } else {
                                // Can not extract back up file
                                Utils.encrypt(getActivity(), filePath,
                                        Encryption.BYTES_TO_ENCRYPT_PHOTO);
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

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (getActivity() == null) {
                return;
            }
                int mode = Integer.parseInt(params[1]);
			if (mode == Utils.EXPORT) {
				@SuppressWarnings("unchecked")
				List<FileItem> fileItems = (List<FileItem>) mAdapter
						.getSelectedItem().clone();
				for (FileItem fileItem : fileItems) {
					if (!fileItem.getPathNew().startsWith(Utils.FOLDER)) {
						fileItem.setPathNew(Utils.FOLDER
								+ fileItem.getPathNew());
					}
					if (!fileItem.getPathFrom().startsWith(Utils.SDCARD)) {
						fileItem.setPathFrom(Utils.SDCARD
								+ fileItem.getPathFrom());
					}
				}
			}
			// empty restore folder
			File restoreFolder = new File(Utils.RESTORE_FOLDER);
			Utils utils = new Utils();
			utils.emptyRestoreFolder(restoreFolder.getAbsolutePath());
			if (result && params != null && params.length == 2) {
                switch (mode) {
                    case Utils.EXPORT:
                        File sqlFile = new File(Utils.FOLDER + "/" + Utils.BACKUP_SQL);
                        if (sqlFile.exists()) {
                            sqlFile.delete();
                        }
                        break;
                    case Utils.IMPORT:
                        if (objs != null) {
                            objs.clear();
                            objs.addAll(loadFile());
                        }

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
    class LoadHiddenFileTask extends AsyncTask<Void, Void, List<BaseItemEntity>> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SynDialogFragment.show(getActivity(), getString(R.string.loading));
        }

        @Override
        protected List<BaseItemEntity> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            if (flagChangeData) {
                return new ArrayList<BaseItemEntity>(loadFile());
            } else {
                return objs;
            }

        }

        @Override
        protected void onPostExecute(List<BaseItemEntity> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            SynDialogFragment.dismiss(getActivity());
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
            if (itemChangeListener != null) {
                itemChangeListener.onFinishLoadItems();
            }
        }

    }


    /**
     * Class delete file
     */
    class DeleteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SynDialogFragment.show(getActivity(), getString(R.string.deleting));
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (Utils.isSDCardExist()) {
                for (Iterator<? extends BaseItemEntity> iterator = objs.iterator(); iterator.hasNext();) {
                    FileItem obj = (FileItem)iterator.next();
                    if (!obj.isCheck())
                        continue;
                    File file = new File(obj.getPathNew());
                    if (file.delete()) {
                        App.getDB().deleteFile(obj);
                        iterator.remove();
                        HiddenBoxDBUtil.getInstance().deleteFile(obj);
                    } else {
                        // File is deleted on SDCard
                        if (!file.exists()) {
                            App.getDB().deleteFile(obj);
                            iterator.remove();
                            HiddenBoxDBUtil.getInstance().deleteFile(obj);
                        }
                    }
                }

            } else {
                ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            SynDialogFragment.dismiss(getActivity());
            mAdapter.notifyDataSetChanged();
            // end copying DB
            if (itemChangeListener != null) {
                itemChangeListener.onSelectedItemChanged(0);
                itemChangeListener.onFinishLoadItems();
            }
            setBackgroundDeselectAll();
        }
    }
    @Override
    protected void onBackup(String[] params) {
        // TODO Auto-generated method stub
        new BackUpAndRestoreTask().execute(params);
    }
    @Override
    protected void onRestore(String[] params) {
        // TODO Auto-generated method stub
        new BackUpAndRestoreTask().execute(params);

	}

	@Override
	public void onAddItemsModeSelected(AddFileMode addFileMode) {
		// TODO Auto-generated method stub
		switch (addFileMode) {
		case ALL:
			onPicker(mPath);
			break;
		case FOLDER:
			// show folder
			if (Utils.isSDCardExist()) {
				App.needShowLogin = false;
				if (getActivity() != null) {
					Intent intent = new Intent(getActivity(),
							FileBrowserActivity.class);
					intent.putExtra(Utils.FILE_MODE, Utils.EXPORT);
					startActivityForResult(intent, Utils.REQUEST_FILE);
				}
			} else {
				ConfirmDialogFragment.show(getActivity(),
						getString(R.string.unmount_sdcard));
			}
			break;

		default:
			break;
		}

	}
}
