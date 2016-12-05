package com.hhzmy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.google.gson.Gson;
import com.hhzmy.adapter.LeftRecyclerView_Adapter;
import com.hhzmy.adapter.RightRecyclerView_Adapter;
import com.hhzmy.bean.ChildrenBean;
import com.hhzmy.bean.RedBaby;
import com.hhzmy.bean.RsBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/8.
 */
public class HpClassifyFragment extends Fragment {
    @Bind(R.id.hp_classify_recyclerView1)
    RecyclerView hpClassifyRecyclerView1;
    @Bind(R.id.hp_classify_recyclerView2)
    RecyclerView hpClassifyRecyclerView2;
    private List<ChildrenBean> AllList;
    private List<RsBean> rs;
    private int index=0;
    private LeftRecyclerView_Adapter leftadapter;
    private RightRecyclerView_Adapter rightadapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.hp_classfig, null);
        ButterKnife.bind(this, view);
        AllList= new ArrayList<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getData();
        super.onActivityCreated(savedInstanceState);
    }
    public void getData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    InputStream inputStream = getActivity().getAssets().open("category.txt");
                    byte[] bytes = new byte[1024];
                    int len;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                    }
                    String json = outputStream.toString("utf-8");
                    final RedBaby redBaby = new Gson().fromJson(json, RedBaby.class);

                    rs = redBaby.getRs();
                    hpClassifyRecyclerView1.post(new Runnable() {
                        @Override
                        public void run() {
                            rs.get(0).isChecked=true;
                            //设置布局管理器
                            hpClassifyRecyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
                            //设置适配器
                            leftadapter = new LeftRecyclerView_Adapter(getActivity(), rs);
                            hpClassifyRecyclerView1.setAdapter(leftadapter);
                            //设置分割线
//                            hpClassifyRecyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

                            //点击事件
                            leftadapter.setOnRecyclerItemClickListener(new LeftRecyclerView_Adapter.OnRecyclerItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    rs.get(index).isChecked=false;
                                    rs.get(position).isChecked=true;
                                    index=position;
                                    update(position);
                                    Toast.makeText(getActivity(),"item"+position,Toast.LENGTH_SHORT).show();
                                }
                            });
//                            AllList= new ArrayList<>();
                            //右边
                            //设备管理器
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
                            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    return AllList.get(position).isHeader?3:1;
                        }
                    });
                            hpClassifyRecyclerView2.setLayoutManager(gridLayoutManager);
                            //设置适配器
                            List<ChildrenBean> childrenBeen = rs.get(0).getChildren();

                            for (int i = 0; i < childrenBeen.size(); i++) {
                                childrenBeen.get(i).isHeader=true;

                                AllList.add(childrenBeen.get(i));
                                AllList.addAll(childrenBeen.get(i).getChildren());
                            }
                            rightadapter = new RightRecyclerView_Adapter(AllList,getActivity());
                            hpClassifyRecyclerView2.setAdapter(rightadapter);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void update(int position){
        leftadapter.notifyDataSetChanged();
        AllList.clear();
        List<ChildrenBean> childrenBeen =rs.get(position).getChildren();
        for (int i = 0; i < childrenBeen.size(); i++) {
            childrenBeen.get(i).isHeader=true;
            AllList.add(childrenBeen.get(i));
            AllList.addAll(childrenBeen.get(i).getChildren());
        }

        rightadapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
