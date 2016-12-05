package com.hhzmy.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.fragment.HpClassifyFragment;
import com.hhzmy.fragment.HpHomeFragment;
import com.hhzmy.fragment.HpMyFragment;
import com.hhzmy.fragment.HpShopCarFragment;

public class HomePagerActivity extends FragmentActivity implements View.OnClickListener{
    private RadioButton mHome;
    private RadioButton mClassify;
    private RadioButton mShopCar;
    private RadioButton mMy;
    private HpHomeFragment mHpHome;
    private HpClassifyFragment mHpClassify;
    private HpShopCarFragment mHpShopCar;
    private HpMyFragment mHpMy;
    private FragmentTransaction beginTransaction;

    //管理者
    private FragmentManager mFragmentManager;

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);

        //控件初始化
        initView();
        // 得到FragmentManager fargment管理者对象
        mFragmentManager = this.getSupportFragmentManager();
        // 开启事务,得到事务对象
        start();
        //得到fragment对象
        getFragments();
        // 同时添加四个fragment
        beginTransaction.add(R.id.home_fl,mHpHome).add(R.id.home_fl,mHpClassify).add(R.id.home_fl, mHpShopCar).add(R.id.home_fl, mHpMy);
        //给你个默认的
        beginTransaction.show(mHpHome).hide(mHpClassify).hide(mHpShopCar).hide(mHpMy);
        // 提交事务
        beginTransaction.commit();

    }

    private void start() {
        beginTransaction = mFragmentManager.beginTransaction();
    }

    private void initView() {
        mHome = (RadioButton) findViewById(R.id.hp_home);
        mClassify = (RadioButton) findViewById(R.id.hp_classify);
        mShopCar = (RadioButton) findViewById(R.id.hp_shop_car);
        mMy = (RadioButton) findViewById(R.id.hp_my);

        mHome.setOnClickListener(this);
        mClassify.setOnClickListener(this);
        mShopCar.setOnClickListener(this);
        mMy.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);

        } else {
            finish();
            System.exit(0);
        }
    }

    public void getFragments() {
        mHpHome = new HpHomeFragment();
        mHpClassify = new HpClassifyFragment();
        mHpShopCar = new HpShopCarFragment();
        mHpMy = new HpMyFragment();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.hp_home:
                start();
                // 替换fragment
                beginTransaction.show(mHpHome).hide(mHpClassify).hide(mHpShopCar).hide(mHpMy);
                // 提交事务
                beginTransaction.commit();
                break;
            case R.id.hp_classify:
                start();

                // 替换fragment
                beginTransaction.hide(mHpHome).show(mHpClassify).hide(mHpShopCar).hide(mHpMy);
                // 提交事务
                beginTransaction.commit();
                break;
            case R.id.hp_shop_car:
               start();

                // 替换fragment
                beginTransaction.hide(mHpHome).hide(mHpClassify).show(mHpShopCar).hide(mHpMy);
                // 提交事务
                beginTransaction.commit();
                break;
            case R.id.hp_my:
               start();
                // 替换fragment
                beginTransaction.hide(mHpHome).hide(mHpClassify).hide(mHpShopCar).show(mHpMy);
                // 提交事务
                beginTransaction.commit();
                break;
        }
    }
}
