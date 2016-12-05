package com.hhzmy.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bw.honghaizidemo.R;
import com.hhzmy.bean.HomeBean;
import com.hhzmy.tools.Tools;
import com.hhzmy.view.MyLinearLayoutManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/15.
 */
public class HpHomeListViewAdapter extends BaseAdapter{
    private Context context;
    private List<HomeBean.DataBean.TagBean> mTagBean;
    private List<HomeBean.DataBean> mDataBean;
    private DisplayImageOptions options;

    public HpHomeListViewAdapter(Context context, List<HomeBean.DataBean.TagBean> mTagBean, List<HomeBean.DataBean> mDataBean, DisplayImageOptions options) {
        this.context = context;
        this.mTagBean = mTagBean;
        this.mDataBean = mDataBean;
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
        if (view==null){
            view = View.inflate(context, R.layout.hp_home_lv_item, null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        ImageLoader.getInstance().displayImage(Tools.URL + mTagBean.get(i).getPicUrl(),holder.lvImg , options);

        List<HomeBean.DataBean.TagBean> mTagBean1 = mDataBean.get(i).getTag();
        holder.hpHomeLvRecyclerView.setLayoutManager(new MyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));

        HpHomeRecycler2Adapter mAdapter = new HpHomeRecycler2Adapter(context, mTagBean1, options);
        holder.hpHomeLvRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new HpHomeRecycler2Adapter.OnRecyclerViewItemClickListener(){
//            @Override
//            public void onItemClick(View view , String data){
//                HpHomeFragment hp = new HpHomeFragment();
//                hp.xiangqing(data);
//            }
//        });
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.lv_img)
        ImageView lvImg;
        @Bind(R.id.hp_home_lv_recyclerView)
        RecyclerView hpHomeLvRecyclerView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
