package com.hhzmy.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.honghaizidemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/9.
 */
public class GDDetailsFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.gd_details_pic)
    TextView gdDetailsPic;
    @Bind(R.id.gd_details_spec)
    TextView gdDetailsSpec;
    @Bind(R.id.gd_details_pkgs)
    TextView gdDetailsPkgs;
    private FragmentManager mFragmentManager;
    private GDDetailsPicFragment mPic;
    private GDDetailsSpecFragment mSpec;
    private GDDetailsPkgsFragment mPkgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.gd_details, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // 得到FragmentManager fargment管理者对象
        mFragmentManager = getActivity().getSupportFragmentManager();
        // 开启事务,得到事务对象
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        //得到fragment对象
        mPic = new GDDetailsPicFragment();
        mSpec = new GDDetailsSpecFragment();
        mPkgs = new GDDetailsPkgsFragment();

        // 同时添加三个fragment
        beginTransaction.add(R.id.gd_details_fl,mPic).add(R.id.gd_details_fl,mPkgs).add(R.id.gd_details_fl, mSpec);
        //给你个默认的
        beginTransaction.hide(mPkgs).hide(mSpec).show(mPic);
        // 提交事务
        beginTransaction.commit();

        gdDetailsPic.setOnClickListener(this);
        gdDetailsSpec.setOnClickListener(this);
        gdDetailsPkgs.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.gd_details_pic:
                FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
                beginTransaction.hide(mPkgs).hide(mSpec).show(mPic);
                gdDetailsPic.setTextColor(Color.YELLOW);
                gdDetailsSpec.setTextColor(Color.BLACK);
                gdDetailsPkgs.setTextColor(Color.BLACK);
                beginTransaction.commit();
                break;
            case R.id.gd_details_spec:
                FragmentTransaction beginTransaction2 = mFragmentManager.beginTransaction();
                beginTransaction2.hide(mPkgs).hide(mPic).show(mSpec);
                gdDetailsPic.setTextColor(Color.BLACK);
                gdDetailsSpec.setTextColor(Color.YELLOW);
                gdDetailsPkgs.setTextColor(Color.BLACK);
                beginTransaction2.commit();
                break;
            case R.id.gd_details_pkgs:
                FragmentTransaction beginTransaction3 = mFragmentManager.beginTransaction();
                beginTransaction3.hide(mPic).hide(mSpec).show(mPkgs);
                gdDetailsPic.setTextColor(Color.BLACK);
                gdDetailsSpec.setTextColor(Color.BLACK);
                gdDetailsPkgs.setTextColor(Color.YELLOW);
                beginTransaction3.commit();
                break;
        }
    }
}
