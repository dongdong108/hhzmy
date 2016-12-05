package com.hhzmy.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bw.honghaizidemo.R;
import com.google.gson.Gson;
import com.hhzmy.activity.DetailsActivity;
import com.hhzmy.activity.EWMActivity;
import com.hhzmy.activity.GoodsDetailsActivity;
import com.hhzmy.adapter.HpHomeGridView2Adapter;
import com.hhzmy.adapter.HpHomeGridView3Adapter;
import com.hhzmy.adapter.HpHomeGridViewAdapter;
import com.hhzmy.adapter.HpHomeListView2Adapter;
import com.hhzmy.adapter.HpHomeListViewAdapter;
import com.hhzmy.adapter.HpHomeRecycler1Adapter;
import com.hhzmy.adapter.HpHomeViewPagerAdapter;
import com.hhzmy.bean.HomeBean;
import com.hhzmy.httputils.OkHttp;
import com.hhzmy.tools.ImageLoaderUtils;
import com.hhzmy.tools.Tools;
import com.hhzmy.view.MyGridView;
import com.hhzmy.view.MyLinearLayoutManager;
import com.hhzmy.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by asus on 2016/11/8.
 */
public class HpHomeFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    @Bind(R.id.hp_home_iv)
    ImageView hpHomeIv;
    @Bind(R.id.hp_home_ewm)
    ImageView hpHomeEwm;
    @Bind(R.id.hp_home_xx)
    ImageView hpHomeXx;
    @Bind(R.id.hp_home_recyclerView1)
    RecyclerView hpHomeRecyclerView1;
    @Bind(R.id.hp_home_iv2)
    ImageView hpHomeIv2;
    @Bind(R.id.hp_home_iv3)
    ImageView hpHomeIv3;
    @Bind(R.id.hp_home_myGV2)
    MyGridView hpHomeMyGV2;
    @Bind(R.id.hp_home_iv4)
    ImageView hpHomeIv4;
    @Bind(R.id.hp_home_iv5)
    ImageView hpHomeIv5;
    @Bind(R.id.hp_home_myGV3)
    MyGridView hpHomeMyGV3;
    @Bind(R.id.hp_home_iv6)
    ImageView hpHomeIv6;
    @Bind(R.id.hp_home_lv1)
    MyListView hpHomeLv1;
    @Bind(R.id.hp_home_iv7)
    ImageView hpHomeIv7;
    @Bind(R.id.hp_home_lv2)
    MyListView hpHomeLv2;
    @Bind(R.id.hp_home_iv8)
    ImageView hpHomeIv8;
    @Bind(R.id.hp_home_vp)
    ViewPager hpHomeVp;
    @Bind(R.id.hp_home_myGV)
    MyGridView hpHomeMyGV;
    //首页所有东西的接口
    private String mPath = "http://mock.eoapi.cn/success/jSWAxchCQfuhI6SDlIgBKYbawjM3QIga";
    //首页所有东西的bean包
    private HomeBean mHomeBean;
    //ImageLoader用到的类
    private DisplayImageOptions options = ImageLoaderUtils.initOptions();
    //无限轮播用到的适配器
    private HpHomeViewPagerAdapter pagerAdapter;

    //无限轮播handler发送请求
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    //得到当前的位置
                    hpHomeVp.setCurrentItem(hpHomeVp.getCurrentItem() + 1);

                    //继续发送,轮播
                    handler.sendEmptyMessageDelayed(2, 2000);
                    break;
            }
        }
    };
    private ArrayList<HomeBean.DataBean.TagBean> tagBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.hp_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //解析数据
        getDatas();
        hpHomeEwm.setOnClickListener(this);
    }

    //获取首页数据，并解析
    public void getDatas() {

        OkHttp.getAsync(mPath,
                new OkHttp.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
//                        Log.d("dsadadddd",result);
                        Gson gson = new Gson();
                        mHomeBean = gson.fromJson(result, HomeBean.class);

                        //无线轮播
                        getViewPager();
                        //双11来了，母婴预售
                        double11GridView();
                        //秒杀
                        miaosha();

                        //傲娇品牌
                        aojiao();

                        //母婴专区
                        muying();

                        //主题特卖
                        zhuti();

                        //辣妈拼团
                        lama();

                        //查看更多
                        seeMore();
                    }
                }
        );

    }

    //查看更多
    private void seeMore() {
        final List<HomeBean.DataBean.TagBean> list1 = mHomeBean.getData().get(33).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL + list1.get(0).getPicUrl(), hpHomeIv8, options);

        hpHomeIv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiangqing(list1.get(0).getLinkUrl());
            }
        });
    }

    //辣妈专区
    private void lama() {
        List<HomeBean.DataBean.TagBean> list1 = mHomeBean.getData().get(23).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL+ list1.get(0).getPicUrl(), hpHomeIv7, options);

        final List<HomeBean.DataBean.TagBean> mTagBean = new ArrayList<>();
        mTagBean.addAll(mHomeBean.getData().get(24).getTag());
        mTagBean.addAll(mHomeBean.getData().get(26).getTag());
        mTagBean.addAll(mHomeBean.getData().get(28).getTag());
        mTagBean.addAll(mHomeBean.getData().get(30).getTag());
        mTagBean.addAll(mHomeBean.getData().get(32).getTag());
        hpHomeLv2.setAdapter(new HpHomeListView2Adapter(getActivity(), mTagBean, options));

        hpHomeLv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xiangqing(mTagBean.get(i).getLinkUrl());
            }
        });
    }

    //主题特卖
    private void zhuti() {
        List<HomeBean.DataBean.TagBean> list1 = mHomeBean.getData().get(13).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL+ list1.get(0).getPicUrl(), hpHomeIv6, options);

        //大图片的集合
        tagBean = new ArrayList<>();
        tagBean.addAll(mHomeBean.getData().get(14).getTag());
        tagBean.addAll(mHomeBean.getData().get(16).getTag());
        tagBean.addAll(mHomeBean.getData().get(18).getTag());
        tagBean.addAll(mHomeBean.getData().get(20).getTag());

        List<HomeBean.DataBean> mDataBean = new ArrayList<>();
        mDataBean.add(mHomeBean.getData().get(15));
        mDataBean.add(mHomeBean.getData().get(17));
        mDataBean.add(mHomeBean.getData().get(19));
        mDataBean.add(mHomeBean.getData().get(21));
        hpHomeLv1.setAdapter(new HpHomeListViewAdapter(getActivity(), tagBean, mDataBean, options));

    }

    //母婴专区
    private void muying() {
        List<HomeBean.DataBean.TagBean> mTagBean1 = mHomeBean.getData().get(9).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean1.get(0).getPicUrl(), hpHomeIv3, options);

        //准妈妈备孕
        final List<HomeBean.DataBean.TagBean> mTagBean2 = mHomeBean.getData().get(10).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean2.get(0).getPicUrl(), hpHomeIv4, options);
        ImageLoader.getInstance().displayImage(Tools.URL+ mTagBean2.get(1).getPicUrl(), hpHomeIv5, options);

        hpHomeIv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiangqing(mTagBean2.get(0).getLinkUrl());
            }
        });
        hpHomeIv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiangqing(mTagBean2.get(0).getLinkUrl());
            }
        });

        final List<HomeBean.DataBean.TagBean> mTagBean = mHomeBean.getData().get(11).getTag();
        hpHomeMyGV3.setAdapter(new HpHomeGridView3Adapter(getActivity(), mTagBean, options));
        hpHomeMyGV3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xiangqing(mTagBean.get(i).getLinkUrl());
            }
        });
    }

    //傲娇品牌
    private void aojiao() {
        List<HomeBean.DataBean.TagBean> list = mHomeBean.getData().get(4).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL + list.get(0).getPicUrl(), hpHomeIv2, options);

        final List<HomeBean.DataBean.TagBean> mTagBean = new ArrayList<>();
        mTagBean.addAll(mHomeBean.getData().get(5).getTag());
        mTagBean.addAll(mHomeBean.getData().get(6).getTag());
        mTagBean.addAll(mHomeBean.getData().get(7).getTag());

        hpHomeMyGV2.setAdapter(new HpHomeGridView2Adapter(getActivity(), mTagBean, options));

        hpHomeMyGV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xiangqing(mTagBean.get(i).getLinkUrl());
            }
        });
    }

    //秒杀
    public void miaosha() {
        List<HomeBean.DataBean.TagBean> mTagBean = mHomeBean.getData().get(2).getTag();
        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean.get(0).getPicUrl(), hpHomeIv, options);

        hpHomeRecyclerView1.setLayoutManager(new MyLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        HpHomeRecycler1Adapter mAdapter = new HpHomeRecycler1Adapter(getActivity(), mTagBean, options);
        hpHomeRecyclerView1.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HpHomeRecycler1Adapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , String data){
                startActivity(new Intent(getActivity(), GoodsDetailsActivity.class));
            }
        });

    }

    //签到
    private void double11GridView() {
        //得到gridView的类
        final List<HomeBean.DataBean.TagBean> mTagBean = mHomeBean.getData().get(1).getTag();
        //设置适配器
        hpHomeMyGV.setAdapter(new HpHomeGridViewAdapter(getActivity(), mTagBean, options));

        hpHomeMyGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                xiangqing(mTagBean.get(i).getLinkUrl());
            }
        });

    }

    //点击跳转详情页
    public void xiangqing(String path) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("path", path);

        startActivity(intent);
    }

    //无线轮播
    public void getViewPager() {
        //得到无线轮播的图片的类
        List<HomeBean.DataBean.TagBean> mTagBean = mHomeBean.getData().get(0).getTag();
        pagerAdapter = new HpHomeViewPagerAdapter(getActivity(), mTagBean, options);
        //设置适配器
        hpHomeVp.setAdapter(pagerAdapter);

        //延迟发送，图片无限轮播
        handler.sendEmptyMessageDelayed(2, 2000);

        pagerAdapter.setOnItemClickListener(new HpHomeViewPagerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                xiangqing(data);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.hp_home_ewm:
                startActivity(new Intent(getActivity(), EWMActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        requestCodeQrcodePermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQrcodePermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(getActivity(), perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }

}
