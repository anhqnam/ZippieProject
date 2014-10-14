/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - PhotoItemFragment.java
 * Date create: 1:44:42 PM - Feb 18, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.media.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.media.BaseMediaItemFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class PhotoItemFragment.
 */
public class PhotoItemFragment extends BaseMediaItemFragment {

    /** The image view. */
    private ImageView imageView;

    /** The height. */
    private int height;

    /** The width. */
    private int width;

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onAttach(android.app.Activity)
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.photo_item, container, false);
        imageView = (ImageView) view.findViewById(R.id.photo_image);
        return view;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.arisvn.arissmarthiddenbox.fragment.BaseItemFragment#initMedia(java
     * .lang.String)
     */
    @Override
    public void initMedia(String filePath) {
        // TODO Auto-generated method stub
    	System.gc();
        new ImageLoadingTask().execute(filePath);

    }

    /**
     * The Class ImageLoadingTask.
     */
    class ImageLoadingTask extends AsyncTask<String, Void, Bitmap> {

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (params != null && params.length > 0) {
                return getBitmap(params[0]);
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

        /**
         * Gets the bitmap.
         * 
         * @param imageFilePath the image file path
         * @return the bitmap
         */
        private Bitmap getBitmap(String imageFilePath) {
            // Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(imageFilePath,
                    bmpFactoryOptions);
            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
                    / (float) height);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) width);
            Log.v("HEIGHTRATIO", "" + heightRatio);
            Log.v("WIDTHRATIO", "" + widthRatio);
            // If both of the ratios are greater than 1, one of the sides of
            // the image is greater than the screen
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            // Decode it for real
            bmpFactoryOptions.inPreferredConfig = Config.ARGB_8888;
            bmpFactoryOptions.inPurgeable = true;
            DisplayMetrics dm = getResources().getDisplayMetrics();
            bmpFactoryOptions.inDensity = dm.densityDpi;
            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
            return bmp;
        }
    }

}
