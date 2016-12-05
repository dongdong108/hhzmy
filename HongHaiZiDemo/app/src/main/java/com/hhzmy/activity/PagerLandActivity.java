package com.hhzmy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.bw.honghaizidemo.R;

public class PagerLandActivity extends FragmentActivity implements View.OnClickListener{
    private ViewPager mVp;
    private List<View> mListVp;
    private SharedPreferences mSp;
    private Button mPass,mStart;
    private RadioButton mIv1,mIv2,mIv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_land);

        mSp = getSharedPreferences("liudongdong.xml", MODE_PRIVATE);
        if (mSp.getBoolean("liudongdong", false)) {
            Intent mIntent = new Intent(PagerLandActivity.this,HomePagerActivity.class);
            startActivity(mIntent);
            finish();
        }

        //添加View
        addView();
        //控件初始化
        initView();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.pl_vp);
        mIv1 = (RadioButton) findViewById(R.id.pl_iv1);
        mIv2 = (RadioButton) findViewById(R.id.pl_iv2);
        mIv3 = (RadioButton) findViewById(R.id.pl_iv3);
        mPass = (Button) findViewById(R.id.pl_btn_pass);
        mStart = (Button) findViewById(R.id.pl_btn_start);
        mPass.setOnClickListener(this);
        mStart.setOnClickListener(this);

        mVp.setAdapter(new PagerAdapter() {
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
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch(position){
                    case 0:
                        mIv1.setChecked(true);
                        mIv2.setChecked(false);
                        mIv3.setChecked(false);
                        break;
                    case 1:
                        mIv1.setChecked(false);
                        mIv2.setChecked(true);
                        mIv3.setChecked(false);
                        break;
                    case 2:
                        mIv1.setChecked(false);
                        mIv2.setChecked(false);
                        mIv3.setChecked(true);
                        break;
                }
                //判断不为空且是最后一张logo图的时候
                if (mListVp != null && position == 2) {
                    mStart.setVisibility(View.VISIBLE);
                    mPass.setVisibility(View.INVISIBLE);
                    RelativeLayout layout = (RelativeLayout) mListVp.get(position).findViewById(R.id.logo_pl3);
                    layout.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            addSh();
                        }
                    });

                }else{
                    mStart.setVisibility(View.INVISIBLE);
                    mPass.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addView() {
        mListVp = new ArrayList<>();

        View view1 = View.inflate(getApplicationContext(),R.layout.logo_pl1,null);
        View view2 = View.inflate(getApplicationContext(),R.layout.logo_pl2,null);
        View view3 = View.inflate(getApplicationContext(),R.layout.logo_pl3,null);

        mListVp.add(view1);
        mListVp.add(view2);
        mListVp.add(view3);
    }

    @Override
    public void onClick(View view) {
        addSh();
    }
    //保存
    private void addSh() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putBoolean("liudongdong", true);
        editor.commit();
        Intent mIntent = new Intent(PagerLandActivity.this,HomePagerActivity.class);
        startActivity(mIntent);
        finish();
    }





}
