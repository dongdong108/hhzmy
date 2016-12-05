package com.hhzmy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by asus on 2016/11/16.
 */
public class GDViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragment;
    public GDViewPagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    @Override
    public Fragment getItem(int arg0) {

        return mFragment.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragment.size()!=0?mFragment.size():0;
    }


}
