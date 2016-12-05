package com.hhzmy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.honghaizidemo.R;
import com.hhzmy.bean.HomeBean;
import com.hhzmy.tools.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by asus on 2016/11/9.
 */
public class HpHomeViewPagerAdapter extends PagerAdapter implements View.OnClickListener{
    private Context context;
    private List<HomeBean.DataBean.TagBean> mTagBean;
    private DisplayImageOptions options;


    public HpHomeViewPagerAdapter(Context context, List<HomeBean.DataBean.TagBean> mTagBean,DisplayImageOptions options) {
        this.context = context;
        this.mTagBean = mTagBean;
        this.options = options;
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener
                                               listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getCount() {

        return Integer.MAX_VALUE;
    }

    @Override
    // 实例化条目
    public Object instantiateItem(ViewGroup container, int position) {

        // 得到图片的网络地址
        String path = mTagBean.get(position%mTagBean.size()).getPicUrl();
        View view = View.inflate(context, R.layout.hp_home_item,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.hp_home_iv);

        ImageLoader.getInstance().displayImage(Tools.URL+ path, imageView, options);
        container.addView(view);
        //给imageview设置点击事件
        imageView.setOnClickListener(this);
//        然后把需要的URL设置到Tag里面，我们只需要把URL，所以
//        就把URL存进去 就可以了
        imageView.setTag(mTagBean.get(position % mTagBean.size()).getLinkUrl());
        return view;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //主要要在点击事件里面得到以下数据
            mOnItemClickListener.onItemClick(view, (String) view.getTag());
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        // super.destroyItem(container, position, object);

        container.removeView((View) object);
    }
}
