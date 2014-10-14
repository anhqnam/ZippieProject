/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - PickerFragment.java
 * Date create: 2:57:50 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.media;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.App;
import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.dialog.CancelableDialogFrament;
import com.arisvn.arissmarthiddenbox.dialog.ConfirmDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SortDialogFragment;
import com.arisvn.arissmarthiddenbox.dialog.SynDialogFragment;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.BaseItemEntity;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.fragment.HiddenBasePickerFragment;
import com.arisvn.arissmarthiddenbox.listener.OnCancelListener;
import com.arisvn.arissmarthiddenbox.media.adapter.MediaGridAdapter;
import com.arisvn.arissmarthiddenbox.media.adapter.MediaListAdapter;
import com.arisvn.arissmarthiddenbox.util.Compartor;
import com.arisvn.arissmarthiddenbox.util.HiddenBoxDBUtil;
import com.arisvn.arissmarthiddenbox.util.SaveData;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class PickerFragment.
 */
public class MediaPickerFragment extends HiddenBasePickerFragment {

	/** Called when the activity is first created. */

	/** The m action. */
	private int mAction;
    private String path = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        MediaScannerConnection.scanFile(getActivity(), new String[] { Environment.getExternalStorageDirectory().toString() }, null, new MediaScannerConnection.OnScanCompletedListener() {
            /*
             *   (non-Javadoc)
             * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
             */
            public void onScanCompleted(String path, Uri uri) 
              {
                  Log.i("ExternalStorage", "Scanned " + path + ":");
                  Log.i("ExternalStorage", "-> uri=" + uri);
                  new LoadFilesTask().execute();
              }
            });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /*
     * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        mAction = bundle.getInt(Utils.TYPE, 0);
        path = bundle.getString(Utils.FILE_PATH);
        if (itemChangeListener != null) {
            itemChangeListener.onChangedData(false, mAction);
        }
    }

	/**
	 * Display format's gridview.
	 */
    @Override
	public void showGridMode() {
        getGridView().setVisibility(View.VISIBLE);
        getListView().setVisibility(View.GONE);
        mAdapter = new MediaGridAdapter(getActivity(), objs, mAction);
        getGridView().setAdapter(mAdapter);
        getListView().removeAllViewsInLayout();

		SaveData.getInstance(getActivity()).setShowList(false);
	}

	/**
	 * Display format's listview.
	 */
    @Override
	public void showListMode() {
        getListView().setVisibility(View.VISIBLE);
        getGridView().setVisibility(View.GONE);
		
        mAdapter = new MediaListAdapter(getActivity(), objs, mAction);
        getListView().setAdapter(mAdapter);
        getGridView().removeAllViewsInLayout();

		SaveData.getInstance(getActivity()).setShowList(true);
	}
	

	/**
	 * Load all file via type after scanning SDcard.
	 *
	 * @param type the type
	 */
	private void loadItem(int type) {
        final String[] columns = {
                Utils._DATA, Utils._DISPLAY_NAME, Utils.MEDIA_ID, Utils._SIZE, Utils._MIME_TYPE
        };
        boolean isFolder = path != null && path.length() > 0;
		Cursor cursor = null;
		if (type == Utils.TYPE_AUDIO) {
			cursor = getActivity().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns,
                    isFolder ? Utils._DATA + " like ?" : null,
                    isFolder ? new String[] {
                            path + "%"
                    } : null, Utils._DISPLAY_NAME);
		} else if (type == Utils.TYPE_VIDEO) {
            String[] strings = null;
            if (isFolder) {
                strings = new String[] {
                        "video/%", path + "%"
                };
            } else {
                strings = new String[] {
                        "video/%"
                };
            }
			cursor = getActivity().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    Utils._MIME_TYPE + " like ?"
                            + (isFolder ? "and " + Utils._DATA + " like ?" : ""), strings,
                    Utils._DISPLAY_NAME);
		} else if (type == Utils.TYPE_PHOTO) {
			cursor = getActivity().getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    isFolder ? Utils._DATA + " like ? " : null, isFolder ? new String[] {
                            path + "%"
                    } : null, Utils._DISPLAY_NAME);
		}
		// get all data from cursor
		if (cursor != null) {
			if (cursor.moveToFirst()) {
                int ID_column = cursor.getColumnIndex(Utils.MEDIA_ID);
				do {
					FileItem item = new FileItem();
					String filePath = cursor.getString(cursor
                            .getColumnIndex(Utils._DATA));
					String externalStorageDirectory = Environment
							.getExternalStorageDirectory().getAbsolutePath();
					if (filePath.startsWith(externalStorageDirectory)) {
						int id = cursor.getInt(ID_column);
						item.setPathFrom(filePath);
					item.setName(cursor.getString(cursor
                                .getColumnIndex(Utils._DISPLAY_NAME)));
					item.setId(id);
					item.setType(type);
						item.setSize(cursor.getLong(cursor
                                .getColumnIndex(Utils._SIZE)));
					objs.add(item);
					}

				} while (cursor.moveToNext());
			}
			cursor.close();
		}
		// end
	}

	/**
	 * Hide file.
	 */
    @Override
    public void hideFile() {
		new HideFilesTask().execute();
	}

    /**
     * Create Thread for processing encode file.
     */
    class HideFilesTask extends AsyncTask<Void, Void, Boolean> implements OnCancelListener {

        /** The is cancel. */
        private boolean isCancel = false;

        /** The dialog picker frament. */
        private CancelableDialogFrament dialogPickerFrament;

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogPickerFrament = new CancelableDialogFrament();
            dialogPickerFrament.show(getActivity(), getString(R.string.hiding), this);
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                for (Iterator<? extends BaseItemEntity> iterator = objs.iterator(); iterator
                        .hasNext();) {
                    if (!isCancel) {
                        FileItem fileItem = (FileItem) iterator.next();
                        if (!fileItem.isCheck())
                            continue;
                        if (Utils.isSDCardExist()) {
                            if (!App.getDB().isExistFileName(fileItem.getPathFrom())) {
                                String input = fileItem.getPathFrom();

                                String output = null;
                                int byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;
                                Uri uri = null;

                                if (fileItem.getType() == Utils.TYPE_AUDIO) {
                                    output = Utils.FOLDER + Utils._AUDIO + "/"
                                            + (System.currentTimeMillis() / 10);
                                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;
                                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                } else if (fileItem.getType() == Utils.TYPE_PHOTO) {
                                    output = Utils.FOLDER + Utils._PHOTO + "/"
                                            + (System.currentTimeMillis() / 10);
                                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;
                                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                } else if (fileItem.getType() == Utils.TYPE_VIDEO) {
                                    output = Utils.FOLDER + Utils._VIDEO + "/"
                                            + (System.currentTimeMillis() / 10);
                                    byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;
                                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                }
                                output = output + Utils.ENCRYPT_EXTENSION;
                                fileItem.setPathNew(output);

                                if (fileItem.getThumbnail() == null) {
									if (fileItem.getType() == Utils.TYPE_VIDEO) {
										String path = fileItem.getPathFrom(); /*
																			 * get
																			 * video
																			 * path
																			 */
										;
										Bitmap bmp = ThumbnailUtils
												.createVideoThumbnail(
														path,
														MediaStore.Images.Thumbnails.MICRO_KIND);
										fileItem.setThumbnail(Utils
												.getByteArray(bmp));
                                    System.gc();
										if (bmp != null) {
											bmp.recycle();
										}
										bmp = null;
									} else {
										Bitmap bitmap = Utils.createThumbnail(
												getActivity(),
												fileItem.getId(),
												fileItem.getType());
										fileItem.setThumbnail(Utils
												.getByteArray(bitmap));
										System.gc();
                                    if (bitmap != null) {
                                        bitmap.recycle();
                                    }
                                    bitmap = null;
                                }
                                }

                                if (Utils.encrypt(getActivity(), input, byte_to_encrypte)) {
                                    boolean moving = new File(input).renameTo(new File(output));
                                    if (moving) {
                                        getActivity().getContentResolver().delete(uri,
                                                Utils.MEDIA_ID + " = ?",
                                                new String[] {
                                                String.valueOf(fileItem.getId())
                                        });
                                        App.getDB().insertFile(fileItem);
                                        HiddenBoxDBUtil.getInstance().insertFileSdcardDB(fileItem);
                                        iterator.remove();
                                    } else {
                                        Utils.decrypt(getActivity(), input);
                                    }
                                }
                            }
                        } else {
                            Log.e(Utils.TAG, "processEncrypt faile: there is no SDCard");
                            break;

                        }
                    }else{
                        break;
                    }
                }
                // end copying DB
            } catch (Exception e) {
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
            if (getActivity() != null) {
                dialogPickerFrament.dismiss(getActivity());
                try {
                    if (result) {
                        if (itemChangeListener != null)
                            itemChangeListener.onChangedData(result, mAction);
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
            isCancel = true;
        }

    }

    /**
     * The Class LoadFilesTask.
     */
    class LoadFilesTask extends AsyncTask<Void, Void, Boolean> {
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
		protected Boolean doInBackground(Void... params) {
			loadItem(mAction);
			return null;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (getActivity()==null) {
				return;
			}
			showMode();
			SynDialogFragment.dismiss(getActivity());
			if (itemChangeListener!=null&&objs!=null) {
				itemChangeListener.onFinishLoadItems();
			}
			
			if (gridView.getVisibility()==View.VISIBLE) {
				updateUI(gridView);
			} else {
				updateUI(listView);
			}
		}
	}

    @Override
    protected void onOpen(int position) {
        // TODO Auto-generated method stub
        /** The Constant PLAY. */
        if (Utils.isSDCardExist()) {
            Intent viewMediaIntent = new Intent();
            viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
            if (objs.get(position) instanceof FileItem) {
                FileItem fileItem = (FileItem)objs.get(position);
                File file = new File(fileItem.getPathFrom());
                if (mAction == Utils.TYPE_AUDIO) {
                    viewMediaIntent.setDataAndType(Uri.fromFile(file),Utils.URI_TYPE_AUDIO);
                }else   if (mAction == Utils.TYPE_VIDEO) {
                    viewMediaIntent.setDataAndType(Uri.fromFile(file), Utils.URI_TYPE_VIDEO);
                }else   if (mAction == Utils.TYPE_PHOTO) {
                    viewMediaIntent.setDataAndType(Uri.fromFile(file), Utils.URI_TYPE_IMAGE);
                }
                viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(viewMediaIntent);
            }

        } else {
            ConfirmDialogFragment.show(getActivity(), getString(R.string.unmount_sdcard));
        }
    }

	@Override
	protected void showSortDialog() {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag(
				SortDialogFragment.TAG);
		if (prev != null) {
			return;
//			ft.remove(prev);
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
			Collections.sort(objs, Compartor.compareName);
			break;
		case SIZE:
			Collections.sort(objs, Compartor.compareSize);
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
