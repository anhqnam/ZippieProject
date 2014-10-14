/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.listener - OnCameraSelectedListener.java
 * Date create: 3:30:48 PM - Feb 24, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.listener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving onCameraSelected events. The class that
 * is interested in processing a onCameraSelected event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addOnCameraSelectedListener<code> method. When
 * the onCameraSelected event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see OnCameraSelectedEvent
 */
public interface OnCameraSelectedListener {

    /**
     * The Enum CAMERA_ITEM.
     */
    public enum CAMERA_ITEM {

        /** The take picture. */
        TAKE_PICTURE,
        /** The record audio. */
        RECORD_AUDIO,
        /** The record video. */
        RECORD_VIDEO
    }

    /**
     * On camera selected.
     * 
     * @param item the item
     */
    public void onCameraSelected(CAMERA_ITEM item);

}
