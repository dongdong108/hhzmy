package com.hhzmy.tools;

import android.app.Application;

/**
 * Created by wangkuan on 2016/11/8.
 */
public class ChuShiHua extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderUtils.initConfiguration(getApplicationContext());
    }
}
