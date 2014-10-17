/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.IntroduceAdapter;
import com.lunextelecom.zippie.utils.Utils;
import com.lunextelecom.zippie.view.CirclePageIndicator;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceFragment.
 */
public class IntroduceFragment extends Fragment implements OnClickListener{

    /** The m handler. */
    private final Handler mHandler = new Handler();

    /** The m view pager. */
    private ViewPager mViewPager;

    /** The m adapter. */
    private IntroduceAdapter mAdapter;

    /** The m direction. */
    private int mDirection = 1;

    /** The m timer. */
    private Timer mTimer;

    public enum IntroduceFrom{
        LOCAL,
        SERVER
    }


    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //get view
        View view = inflater.inflate(R.layout.layout_introduce, null);
        TextView tvSkip = (TextView)view.findViewById(R.id.introduce_skip_text);
        mViewPager = (ViewPager)view.findViewById(R.id.introduce_view_pager);
        CirclePageIndicator indicator = (CirclePageIndicator)view.findViewById(R.id.introduce_indicator);

        Utils.setTypefaceRoboto(getActivity(), tvSkip);
        tvSkip.setOnClickListener(this);

        mAdapter = new IntroduceAdapter(getFragmentManager(), getListIntroduceItemFragment(IntroduceFrom.LOCAL));
        mViewPager.setAdapter(mAdapter);

        indicator.setViewPager(mViewPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        indicator.setPageColor(0x88FFFFFF);
        indicator.setFillColor(0xFFFFFFFF);
        indicator.setSnap(true);
        indicator.setStrokeWidth(0);
        indicator.setSpaceBetweenWidth(7);
        mViewPager.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(mTimer != null){
                    mTimer.cancel();
                    mTimer = null;
                    viewPagerAutoScroll(5);
                }
                return false;
            }
        });

        viewPagerAutoScroll(5);

        return view;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.introduce_skip_text:

                break;

            default:
                break;
        }
    }

    /**
     * Gets the list introduce item fragment.
     * 
     * @return the list introduce item fragment
     */
    private List<IntroduceItemFragment> getListIntroduceItemFragment(IntroduceFrom modeType){
        List<Object> listImage = null;
        if(modeType == IntroduceFrom.LOCAL){
            //TODO load from local
            listImage = getListImageFromLocal();
        }else{
            //TODO load from server
        }
        if(listImage != null){
            List<IntroduceItemFragment> list = new ArrayList<IntroduceItemFragment>();
            for(Object image: listImage){
                IntroduceItemFragment fragment = new IntroduceItemFragment(image,modeType);
                list.add(fragment);
            }
            return list;
        }
        return null;
    }

    /**
     * Gets the list image from local.
     * 
     * @return the list image from local
     */
    private List<Object> getListImageFromLocal(){
        List<Object> list = new ArrayList<Object>();
        list.add(R.drawable.intro_01);
        list.add(R.drawable.intro_02);
        list.add(R.drawable.intro_03);
        list.add(R.drawable.intro_04);
        return list;
    }

    /**
     * View pager auto scroll.
     * 
     * @param seconds the seconds
     */
    public void viewPagerAutoScroll(final int seconds){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = mViewPager.getCurrentItem();
                        int numberPage = mAdapter.getCount();
                        if (currentPage == numberPage-1) {
                            mDirection = -1;
                        }
                        if(currentPage == 0){
                            mDirection = 1;
                        }
                        mViewPager.setCurrentItem(currentPage+mDirection, true);
                    }
                });

            }
        }, 10*1000, seconds*1000);
    }
}
