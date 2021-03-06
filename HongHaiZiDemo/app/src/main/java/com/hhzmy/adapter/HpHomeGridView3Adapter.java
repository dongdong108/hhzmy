package com.hhzmy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bw.honghaizidemo.R;
import com.hhzmy.bean.HomeBean;
import com.hhzmy.tools.Tools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/9.
 */
public class HpHomeGridView3Adapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.DataBean.TagBean> mTagBean;
    private DisplayImageOptions options;

    public HpHomeGridView3Adapter(Context context, List<HomeBean.DataBean.TagBean> mTagBean, DisplayImageOptions options) {
        this.options = options;
        this.mTagBean = mTagBean;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mTagBean.size();
    }

    @Override
    public Object getItem(int i) {
        return mTagBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.hp_home_item3, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean.get(i).getPicUrl(), holder.hpHomeIv3, options);
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.hp_home_iv3)
        ImageView hpHomeIv3;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
