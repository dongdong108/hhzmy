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
 * Created by asus on 2016/11/15.
 */
public class HpHomeListView2Adapter extends BaseAdapter {
    private Context context;
    private List<HomeBean.DataBean.TagBean> mTagBean;
    private DisplayImageOptions options;

    public HpHomeListView2Adapter(Context context, List<HomeBean.DataBean.TagBean> mTagBean, DisplayImageOptions options) {
        this.context = context;
        this.mTagBean = mTagBean;
        this.options = options;
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
            view = View.inflate(context, R.layout.hp_home_lv_item2, null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(Tools.URL+ mTagBean.get(i).getPicUrl(), holder.lvImg2, options);

        return view;
    }


    static class ViewHolder {
        @Bind(R.id.lv_img2)
        ImageView lvImg2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
