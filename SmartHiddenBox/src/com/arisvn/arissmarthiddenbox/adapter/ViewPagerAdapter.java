/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: khue.pm
 * Location: BookShelf - jp.co.creatorsguild.bcapp.adapter - ViewPagerAdapter.java
 * Date create: 11:08:05 AM - Jan 16, 2013 - 2013
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.entity.FileItem;
import com.arisvn.arissmarthiddenbox.media.BaseMediaItemFragment;
import com.arisvn.arissmarthiddenbox.media.photo.PhotoItemFragment;
import com.arisvn.arissmarthiddenbox.media.video.VideoItemFragment;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class ViewPagerAdapter.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    /** The m list book. */
    private List<FileItem> fileItems;
    private FileItem selectedFile;

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.app.FragmentStatePagerAdapter#destroyItem(android.
     * view.ViewGroup, int, java.lang.Object)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        super.destroyItem(container, position, object);
    }

    /**
     * Instantiates a new view pager adapter.
     * 
     * @param fm the fm
     * @param listBook the list book
     * @param res the res
     */
    public ViewPagerAdapter(FragmentManager fm, List<FileItem> fileItems, FileItem fileItem) {
        super(fm);
        this.fileItems = fileItems;
        this.selectedFile = fileItem;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        if (fileItems != null) {
            return fileItems.size();
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.FragmentStatePagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        BaseMediaItemFragment fragment = null;
        FileItem item = fileItems.get(position);
        if (item.getType() == Utils.TYPE_PHOTO) {
            fragment = new PhotoItemFragment();
        } else {
            fragment = new VideoItemFragment();
        }
        args.putSerializable(Utils.FILE_ITEM, item);
        if (selectedFile.getId() == fileItems.get(position).getId()) {
            args.putBoolean(Utils.SELECTED, true);
        }
        fragment.setArguments(args);
        return fragment;
    }
}
