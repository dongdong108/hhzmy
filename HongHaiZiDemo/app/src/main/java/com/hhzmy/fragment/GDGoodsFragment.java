package com.hhzmy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.activity.MapActivity;
import com.hhzmy.adapter.GDGoodsPagerAdapter;
import com.hhzmy.bean.HomeBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/9.
 */
public class GDGoodsFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.gd_goods_vp)
    ViewPager gdGoodsVp;
    @Bind(R.id.gd_goods_rb1)
    RadioButton gdGoodsRb1;
    @Bind(R.id.gd_goods_rb2)
    RadioButton gdGoodsRb2;
    @Bind(R.id.gd_goods_rb3)
    RadioButton gdGoodsRb3;
    @Bind(R.id.gd_goods_price)
    TextView gdGoodsPrice;
    @Bind(R.id.gd_goods_address)
    TextView gdGoodsAddress;
    @Bind(R.id.gd_goods_model)
    TextView gdGoodsModel;
    @Bind(R.id.gd_goods_title)
    TextView gdGoodsTitle;
    @Bind(R.id.gd_goods_rl)
    RelativeLayout gdGoodsRl;
    @Bind(R.id.iv_fen)
    ImageView ivFen;
    private ArrayList<View> mListVp;
    private SpannableStringBuilder builder;
    private String mPath = "http://mock.eoapi.cn/success/L11SvLlRPNYsdV5F6df54qhT7VHcr6CJ";
    private HomeBean mGsonBean;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //得到返回来的值
        String dizhi = data.getAction();

        gdGoodsAddress.setText(dizhi);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.gd_goods, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //添加数据
        addView();
        //给viewpager设置数据
        gdGoodsVp.setAdapter(new GDGoodsPagerAdapter(mListVp, getActivity()));
        //设置viewpager的滑动事件
        setVpListener();
        //设置数据
        setTexts();
        gdGoodsRl.setOnClickListener(this);
        ivFen.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }


    private void setTexts() {
        //商品价格
        gdGoodsPrice.setText("¥99.00");
        //用SpannableStringBuilder做图文混排
        tuandText();
        gdGoodsTitle.setText(builder);
    }

    private void tuandText() {
        String name = "#花王（Merries）妙而舒 婴幼儿纸尿裤 中号M64片（6-11kg） 宝宝尿不湿";
        builder = new SpannableStringBuilder(name);
        String rexgString = "#";
        Pattern pattern = Pattern.compile(rexgString);
        Matcher matcher = pattern.matcher(name);

        while (matcher.find()) {
            builder.setSpan(
                    new ImageSpan(getActivity(), R.mipmap.public_zi_ying_new_label), matcher.start(), matcher
                            .end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void setVpListener() {
        gdGoodsVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        gdGoodsRb1.setChecked(true);
                        gdGoodsRb2.setChecked(false);
                        gdGoodsRb3.setChecked(false);
                        break;
                    case 1:
                        gdGoodsRb1.setChecked(false);
                        gdGoodsRb2.setChecked(true);
                        gdGoodsRb3.setChecked(false);
                        break;
                    case 2:
                        gdGoodsRb1.setChecked(false);
                        gdGoodsRb2.setChecked(false);
                        gdGoodsRb3.setChecked(true);
                        break;
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

        View view1 = View.inflate(getActivity(), R.layout.gd_goods_img1, null);
        View view2 = View.inflate(getActivity(), R.layout.gd_goods_img2, null);
        View view3 = View.inflate(getActivity(), R.layout.gd_goods_img3, null);

        mListVp.add(view1);
        mListVp.add(view2);
        mListVp.add(view3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gd_goods_rl:
                startActivityForResult(new Intent(getActivity(), MapActivity.class), 99);
                break;

            case R.id.iv_fen:
                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                        .withText(gdGoodsTitle.getText().toString().trim())
                        .withTitle("红孩子母婴")
                        .setCallback(umShareListener)
                        .share();
                break;
        }
    }
    //分享
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


}
