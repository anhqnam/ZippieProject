/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.listener - FileItemChangeObserver.java
 * Date create: 4:19:02 PM - Feb 18, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.listener;

import java.util.Observable;

import com.arisvn.arissmarthiddenbox.entity.FileItem;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications about
 * FileItemChange information as the FileItemChange is constructed.
 */
public class FileItemChangeObserver extends Observable {

    /** The change observer. */
    private static FileItemChangeObserver changeObserver;

    /**
     * @return the insatance
     */
    public static FileItemChangeObserver getInsatance() {
        if (changeObserver == null) {
            synchronized (FileItemChangeObserver.class) {
                if (changeObserver == null) {
                    changeObserver = new FileItemChangeObserver();
                }
            }
        }
        return changeObserver;

    }

    /**
     * This method is called when information about an FileItemChange which was
     * previously requested using an asynchronous interface becomes available.
     * 
     * @param fileItem the file item
     */
    public void notifyFileItemChange(FileItem fileItem) {
        setChanged();
        notifyObservers(fileItem);
    }
}
