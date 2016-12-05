package com.hhzmy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by asus on 2016/11/16.
 */
public class GDGoodsPagerAdapter extends PagerAdapter {
    private ArrayList<View> mListVp;
    private Context context;

    public GDGoodsPagerAdapter(ArrayList<View> mListVp, Context context) {
        this.mListVp = mListVp;
        this.context = context;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView(mListVp.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        View view = mListVp.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListVp != null ? mListVp.size() : 0;
    }
}
