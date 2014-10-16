/*
 * @author Vuong Huynh
 * Copyright (C) 2014 Lunextelecom
 */
package com.lunextelecom.zippie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lunextelecom.zippie.R;
import com.lunextelecom.zippie.adapter.IntroduceAdapter;
import com.lunextelecom.zippie.utils.Utils;
import com.lunextelecom.zippie.view.CirclePageIndicator;

public class IntroduceFragment extends Fragment implements OnClickListener{

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
        View view = inflater.inflate(R.layout.layout_introduce, null);
        TextView tvSkip = (TextView)view.findViewById(R.id.introduce_skip_text);
        Utils.setTypefaceRoboto(getActivity(), tvSkip);
        tvSkip.setOnClickListener(this);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.introduce_view_pager);
        IntroduceAdapter adapter = new IntroduceAdapter(getFragmentManager(), getListIntroduceItemFragment());
        viewPager.setAdapter(adapter);
        CirclePageIndicator indicator = (CirclePageIndicator)view.findViewById(R.id.introduce_indicator);
        indicator.setViewPager(viewPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        indicator.setPageColor(0xFF888888);
        indicator.setFillColor(0xFFFFFFFF);
        return view;
    }

    /**
     * Gets the list introduce item fragment.
     * 
     * @return the list introduce item fragment
     */
    private List<IntroduceItemFragment> getListIntroduceItemFragment(){
        List<IntroduceItemFragment> list = new ArrayList<IntroduceItemFragment>();
        IntroduceItemFragment fragment = new IntroduceItemFragment(R.drawable.intro_01);
        list.add(fragment);

        fragment = new IntroduceItemFragment(R.drawable.intro_01);
        list.add(fragment);

        fragment = new IntroduceItemFragment(R.drawable.intro_01);
        list.add(fragment);

        fragment = new IntroduceItemFragment(R.drawable.intro_01);
        list.add(fragment);

        return list;
    }

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
}
