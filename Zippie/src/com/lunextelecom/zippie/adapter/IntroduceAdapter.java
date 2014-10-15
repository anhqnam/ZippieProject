package com.lunextelecom.zippie.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lunextelecom.zippie.fragment.IntroduceItemFragment;

public class IntroduceAdapter extends FragmentStatePagerAdapter{
    private List<IntroduceItemFragment> mListData;

    public IntroduceAdapter(FragmentManager fm, List<IntroduceItemFragment> list) {
        super(fm);
        // TODO Auto-generated constructor stub
        mListData = list;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListData.size();
    }

}
