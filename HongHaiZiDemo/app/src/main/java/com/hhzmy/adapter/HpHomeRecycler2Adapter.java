package com.hhzmy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
 * Created by asus on 2016/11/14.
 */
public class HpHomeRecycler2Adapter extends RecyclerView.Adapter<HpHomeRecycler2Adapter.MyViewHolder> implements View.OnClickListener{
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    List<HomeBean.DataBean.TagBean> mTagBean;
    Context context;
    private DisplayImageOptions options;
    public HpHomeRecycler2Adapter(Context context, List<HomeBean.DataBean.TagBean> mTagBean, DisplayImageOptions options) {
        this.context = context;
        this.mTagBean = mTagBean;
        this.options = options;
    }
    //define interface    ******************
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                context).inflate(R.layout.hp_home_item4, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean.get(position).getPicUrl(), holder.iv, options);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(mTagBean.get(position).getLinkUrl());
    }

    @Override
    public int getItemCount() {
        return mTagBean.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        public MyViewHolder(View view) {
            super(view);

            iv = (ImageView) view.findViewById(R.id.hp_home_iv4);
        }
    }
}
