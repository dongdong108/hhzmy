package com.hhzmy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.honghaizidemo.R;
import com.hhzmy.bean.RsBean;

import java.util.List;

/**
 * Created by Liuxiaoyu on 2016/11/11.
 */
public class LeftRecyclerView_Adapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<RsBean> rsBeen;
    OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public LeftRecyclerView_Adapter(Context context, List<RsBean> rsBeen) {
        this.context = context;
        this.rsBeen = rsBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LeftRecyclerView_ViewHolder holder = new LeftRecyclerView_ViewHolder(LayoutInflater.from(context).inflate(R.layout.recaycalerview_leftitem, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ((LeftRecyclerView_ViewHolder)holder).tv_typename.setText(rsBeen.get(position).getDirName());
        if(rsBeen.get(position).isChecked){
            ((LeftRecyclerView_ViewHolder)holder).ll.setBackgroundColor(Color.parseColor("#F2F2F2"));
            ((LeftRecyclerView_ViewHolder)holder).tv_typename.setTextColor(Color.parseColor("#F29400"));
            ((LeftRecyclerView_ViewHolder)holder).view.setVisibility(View.VISIBLE);

        }else{
            ((LeftRecyclerView_ViewHolder)holder).tv_typename.setTextColor(Color.BLACK);
            ((LeftRecyclerView_ViewHolder)holder).view.setVisibility(View.INVISIBLE);
        }
        if (onRecyclerItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rsBeen.size();
    }

    public interface OnRecyclerItemClickListener {
        /**
         * @param view     被点击的ittem
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }

    class LeftRecyclerView_ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_typename;
        View view;
        LinearLayout ll;
        public LeftRecyclerView_ViewHolder(View itemView) {
            super(itemView);
            ll= (LinearLayout) itemView.findViewById(R.id.linerLayout);
            tv_typename= (TextView) itemView.findViewById(R.id.tv_typename);
            view=itemView.findViewById(R.id.yellow);
        }
    }

}
