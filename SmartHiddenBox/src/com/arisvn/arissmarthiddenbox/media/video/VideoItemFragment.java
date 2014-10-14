/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * 
 * Copyright 2011 - 2014 Aris-vn, Inc. All rights reserved.
 * Author: Tam-LT
 * Location: ARISSmartHiddenBox - com.arisvn.arissmarthiddenbox.fragment - VideoItemFragment.java
 * Date create: 2:30:53 PM - Feb 19, 2014 - 2014
 * 
 * 
 */

package com.arisvn.arissmarthiddenbox.media.video;

import java.io.File;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.arisvn.arissmarthiddenbox.R;
import com.arisvn.arissmarthiddenbox.media.BaseMediaItemFragment;
import com.arisvn.arissmarthiddenbox.util.Utils;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoItemFragment.
 */
public class VideoItemFragment extends BaseMediaItemFragment implements
        OnPreparedListener, OnErrorListener {
    /** The video view. */
    private VideoView videoView;

    /** The ready to play. */
    private boolean readyToPlay;

    /** The media controller. */
    private MediaController mediaController;

    /** The image view. */
    private ImageView imageView;

    /*
     * (non-Javadoc)
     * @see
     * com.arisvn.arissmarthiddenbox.fragment.BaseItemFragment#initMedia(java
     * .lang.String)
     */
    @Override
    public void initMedia(String filePath) {
        // TODO Auto-generated method stub
        readyToPlay = false;
        stopMedia();
        Uri videoUri = Uri.fromFile(new File(filePath));
        if (filePath.startsWith(Utils.FOLDER + Utils._AUDIO)) {
            // SynDialogFragment.show(getActivity(),
            // getString(R.string.loading));
        	if (fileItem.getThumbnail()!=null) {
        		imageView
				.setImageBitmap(BitmapFactory.decodeByteArray(
						fileItem.getThumbnail(), 0,
						fileItem.getThumbnail().length));
			} else {
				imageView
				.setImageBitmap(Utils.getBitmapViaType(getActivity(), fileItem.getType()));
			}
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
        } else {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
        }

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
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.video_item, container, false);
        videoView = (VideoView) view.findViewById(R.id.videoView1);
        // listeners for VideoView:
        videoView.setOnErrorListener(this);
        videoView.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        imageView = (ImageView) view.findViewById(R.id.image);
        return view;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.media.MediaPlayer.OnErrorListener#onError(android.media.MediaPlayer
     * , int, int)
     */
    @Override
    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        // SynDialogFragment.dismiss(getActivity());
		// ConfirmDialogFragment.show(getActivity(),
		// "Can not play this media file");
		Toast.makeText(getActivity(),
				getString(R.string.cant_play) + " : " + fileItem.getName(),
				Toast.LENGTH_SHORT).show();
        return true;
    }

    /*
     * (non-Javadoc)
     * @see
     * android.media.MediaPlayer.OnPreparedListener#onPrepared(android.media
     * .MediaPlayer)
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        // TODO Auto-generated method stub
        mp.setLooping(false);
        // SynDialogFragment.dismiss(getActivity());
        mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                stopMedia();
                // getActivity().finish();
            }
        });

        readyToPlay = true;
        playMedia();
    }

    /**
     * Play media.
     */
    public void playMedia() {
        if (readyToPlay) {
            videoView.start();
        }
    }

    /**
     * Stop media.
     */
    public void stopMedia() {
        if (videoView != null && videoView.getCurrentPosition() != 0) {
            videoView.pause();
            videoView.seekTo(0);
            videoView.stopPlayback();
            videoView.setVideoURI(null);
        }
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        stopMedia();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.arisvn.arissmarthiddenbox.fragment.BaseItemFragment#endViewFileItem()
     */
    @Override
    protected void endViewFileItem() {
        // TODO Auto-generated method stub
        stopMedia();
        super.endViewFileItem();
    }

}
