/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Lunextelecom, Inc. All rights reserved.
 * Author: AnhBui
 * Location: Zippie - com.lunextelecom.zippie - SignUpActivity.java
 * created Date: 2014-10-24
 * 
 */
package com.lunextelecom.zippie.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.animation.Interpolator;
import android.widget.Scroller;

// TODO: Auto-generated Javadoc
/**
 * The Class FixedSpeedScroller.
 */
public class FixedSpeedScroller extends Scroller {

    /** The m duration. */
    private int mDuration = 5000;

    /**
     * Instantiates a new fixed speed scroller.
     *
     * @param context the context
     */
    public FixedSpeedScroller(Context context) {
        super(context);
    }

    /**
     * Instantiates a new fixed speed scroller.
     *
     * @param context the context
     * @param interpolator the interpolator
     */
    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * Instantiates a new fixed speed scroller.
     *
     * @param context the context
     * @param interpolator the interpolator
     * @param flywheel the flywheel
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    /* (non-Javadoc)
     * @see android.widget.Scroller#startScroll(int, int, int, int, int)
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /* (non-Javadoc)
     * @see android.widget.Scroller#startScroll(int, int, int, int)
     */
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /**
     * Sets the fixed duration.
     *
     * @param duration the new fixed duration
     */
    public void setFixedDuration(int duration) {
        this.mDuration = duration;
    }


}
