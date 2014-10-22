/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import java.lang.reflect.Field;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.IntroduceAdapter;
import com.lunextelecom.zippie.utils.Utils;
import com.lunextelecom.zippie.view.CirclePageIndicator;
import com.lunextelecom.zippie.view.FixedSpeedScroller;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroduceFragment.
 */
public class IntroduceFragment extends Fragment implements OnClickListener{

    /** The Constant DELAY_BEFORE_AUTO_SCROLL. */
    private final static int DELAY_BEFORE_AUTO_SCROLL = 10000;

    /** The Constant SPEED_DISTANCE_AUTO_SCROLL. */
    private final static int SPEED_DISTANCE_AUTO_SCROLL = 5000;

    /** The Constant SPEED_SCROLL_AUTO. */
    private final static int SPEED_SCROLL_AUTO = 600;

    /** The Constant SPEED_SCROLL_NORMAL. */
    private final static int SPEED_SCROLL_NORMAL = 100;

    /** The Constant SPEED_SCROLL_QUICK. */
    private final static int SPEED_SCROLL_QUICK = 10;
    /** The m handler. */
    private final Handler mHandler = new Handler();

    /** The m view pager. */
    private ViewPager mIntroViewPager;

    /** The m adapter. */
    private IntroduceAdapter mIntroAdapter;

    /** The m direction. */
    private int mDirection = 1;

    /** The m timer. */
    private Timer mTimer;

    /** The m scroller. */
    private FixedSpeedScroller mScroller;

    /**
     * The Enum IntroduceFrom.
     */
    public enum IntroduceFrom{

        /** The local. */
        LOCAL,

        /** The server. */
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
        View view = inflater.inflate(R.layout.introduce_lay, null);
        TextView introSkipTextTv = (TextView)view.findViewById(R.id.introduce_skip_text_id);
        mIntroViewPager = (ViewPager)view.findViewById(R.id.introduce_view_pager_id);
        CirclePageIndicator introIndicator = (CirclePageIndicator)view.findViewById(R.id.introduce_indicator_id);

        Utils.setTypefaceRoboto(getActivity(), introSkipTextTv);
        introSkipTextTv.setOnClickListener(this);

        mIntroAdapter = new IntroduceAdapter(getFragmentManager(), getListIntroduceItemFragment(IntroduceFrom.LOCAL));
        mIntroViewPager.setAdapter(mIntroAdapter);

        introIndicator.setViewPager(mIntroViewPager);
        final float density = getResources().getDisplayMetrics().density;
        introIndicator.setRadius(5 * density);
        introIndicator.setPageColor(0x88FFFFFF);
        introIndicator.setFillColor(0xFFFFFFFF);
        introIndicator.setSnap(true);
        introIndicator.setStrokeWidth(0);
        introIndicator.setSpaceBetweenWidth(7);
        mIntroViewPager.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(mTimer != null){
                    mTimer.cancel();
                    mTimer = null;
                    mScroller.setFixedDuration(SPEED_SCROLL_NORMAL);
                    viewPagerAutoScroll();
                }
                return false;
            }
        });
        return view;
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onStart()
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        viewPagerAutoScroll();
        setScrollAutoViewPagerSpeed();
    }
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.introduce_skip_text_id:

                break;

            default:
                break;
        }
    }

    /**
     * Gets the list introduce item fragment.
     * 
     * @param modeType the mode type
     * @return the list introduce item fragment
     */
    private List<IntroduceItemFragment> getListIntroduceItemFragment(IntroduceFrom modeType){
        List<Object> listImage = null;
        if(modeType == IntroduceFrom.LOCAL){
            //TODO load from local
            listImage = new ArrayList<Object>();
            listImage.add(R.drawable.intro_01);
            listImage.add(R.drawable.intro_02);
            listImage.add(R.drawable.intro_03);
            listImage.add(R.drawable.intro_04);
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
     * View pager auto scroll.
     */
    public void viewPagerAutoScroll(){
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int currentPage = mIntroViewPager.getCurrentItem();
                        int numberPage = mIntroAdapter.getCount();
                        if (currentPage == numberPage-1) {
                            currentPage = -1;
                            mScroller.setFixedDuration(SPEED_SCROLL_QUICK);
                        }else{
                            mScroller.setFixedDuration(SPEED_SCROLL_AUTO);
                        }
                        mIntroViewPager.setCurrentItem(currentPage+mDirection, true);
                    }
                });

            }
        }, DELAY_BEFORE_AUTO_SCROLL, SPEED_DISTANCE_AUTO_SCROLL);
    }

    /**
     * Sets the scroll auto view pager speed.
     */
    public void setScrollAutoViewPagerSpeed(){
        Interpolator sInterpolator = new AccelerateInterpolator();
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            mScroller = new FixedSpeedScroller(mIntroViewPager.getContext(), sInterpolator);
            mScroller.setFixedDuration(SPEED_SCROLL_NORMAL);
            scrollerField.set(mIntroViewPager, mScroller);
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
