/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2013 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - AudioBoxFragment.java
 * Date create: 2:57:24 PM - Nov 21, 2013 - 2013
 * 
 * 
 */
package com.arisvn.arissmarthiddenbox.media.audio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.media.MediaBaseFragment;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class AudioBoxFragment.
 */
public class AudioBoxFragment extends MediaBaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mType = Utils.TYPE_AUDIO;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

	/*
	 * (non-Javadoc)
	 * 
     * @see com.arisvn.arissmarthiddenbox.fragment.BaseFragment#makeFolder()
     */
    @Override
    public void makeFolder() {
        // TODO Auto-generated method stub
        Utils.makeFolder(Utils.FOLDER + Utils._AUDIO);
    }

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return getString(R.string.audio);
	}
}
