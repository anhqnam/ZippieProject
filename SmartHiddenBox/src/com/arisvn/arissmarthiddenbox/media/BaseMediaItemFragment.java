/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - BaseItemFragment.java
 * Date create: 2:31:12 PM - Feb 19, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.media;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.arisvn.arissmarthiddenbox.encryption.Encryption;
import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.listener.FileItemChangeObserver;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseItemFragment.
 */
public abstract class BaseMediaItemFragment extends Fragment implements Observer {

    /** The file item. */
    protected FileItem fileItem;

    /** The file path. */
    protected String filePath;

    /** The file item change observer. */
    protected FileItemChangeObserver fileItemChangeObserver;

    /** The is selected item. */
    protected boolean isSelectedItem;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            fileItem = (FileItem) args.getSerializable(Utils.FILE_ITEM);
            filePath = fileItem.getPathNew();
            isSelectedItem = args.getBoolean(Utils.SELECTED);
        }
        fileItemChangeObserver = FileItemChangeObserver.getInsatance();
        fileItemChangeObserver.addObserver(this);

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (isSelectedItem) {
            startViewFileItem();
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onStop()
     */
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        if (isSelectedItem) {
            endViewFileItem();
        }
		super.onStop();

    }

    /**
     * Inits the media.
     * 
     * @param filePath the file path
     */
    public abstract void initMedia(String filePath);

    /*
     * (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable observable, Object data) {
        // TODO Auto-generated method stub
        if (observable instanceof FileItemChangeObserver) {
            FileItem fileItem = (FileItem) data;
            if (fileItem.getId() == this.fileItem.getId()) {
                // Decrypting file item, start view it
                isSelectedItem = true;
                startViewFileItem();
            } else {
                // end view file item. encrypting it
                isSelectedItem = false;
                endViewFileItem();
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        fileItemChangeObserver.deleteObserver(this);
        fileItemChangeObserver = null;
        super.onDestroy();
    }

    /**
     * Start view file item.
     */
    private void startViewFileItem() {
        if (filePath != null && filePath.trim().length() > 0) {
            if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
                File file = new File(filePath.substring(0,
                        filePath.lastIndexOf(Utils.ENCRYPT_EXTENSION)));
                if (file.exists()) {
                    initMedia(file.getAbsolutePath());
                } else {
                    // this is encrypted file
                    String decryptePath = "";
                    if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
                        decryptePath = filePath.substring(0,
                                filePath.lastIndexOf(Utils.ENCRYPT_EXTENSION));
                    } else {
                        decryptePath = filePath;
                    }
                    if (!filePath.equals(decryptePath)) {
                        boolean rename = new File(filePath).renameTo(new File(
                                decryptePath));
                        if (rename && Utils.decrypt(getActivity(), decryptePath)) {
                            initMedia(decryptePath);
                        }
                    }
                }

            }

        }

    }

    /**
     * End view file item.
     */
    protected void endViewFileItem() {
        if (filePath != null && filePath.trim().length() > 0) {
            if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
                // this is encrypted file
                String decryptePath = "";
                if (filePath.endsWith(Utils.ENCRYPT_EXTENSION)) {
                    decryptePath = filePath.substring(0,
                            filePath.lastIndexOf(Utils.ENCRYPT_EXTENSION));
                } else {
                    decryptePath = filePath;
                }
                if (!filePath.equals(decryptePath)) {
                    File file = new File(decryptePath);
                    if (file.exists()) {
                        boolean rename = file.renameTo(new File(filePath));
                        if (rename) {
                            int byte_to_encrypte = 0;

                            if (filePath
                                    .startsWith(Utils.FOLDER + Utils._AUDIO)) {
                                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_AUDIO;

                            } else if (filePath.startsWith(Utils.FOLDER
                                    + Utils._PHOTO)) {
                                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_PHOTO;

                            } else if (filePath.startsWith(Utils.FOLDER
                                    + Utils._VIDEO)) {
                                byte_to_encrypte = Encryption.BYTES_TO_ENCRYPT_VIDEO;

                            }
                            Utils.encrypt(getActivity(), filePath, byte_to_encrypte);
                        }
                    }

                }

            }

        }
    }
}
