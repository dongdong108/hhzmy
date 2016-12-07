package com.hhzmy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.honghaizidemo.R;
import com.hhzmy.activity.LoginActivity;
import com.hhzmy.tools.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/8.
 */
public class HpMyFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.touxiang)
    ImageView touxiang;
    @Bind(R.id.myname)
    TextView myname;
    //ImageLoader用到的类
    private DisplayImageOptions options = ImageLoaderUtils.initOptions();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.hp_my, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        touxiang.setOnClickListener(this);
        //得到传过来的值
//        getDatas();
        super.onActivityCreated(savedInstanceState);
    }

//    private void getDatas() {
//        Bundle bundle = getArguments();
//        tou = bundle.getString("touxiang");
//        name = bundle.getString("name");
//        if (name!=null&&tou!=null){
//            myname.setText(name);
//            ImageLoader.getInstance().displayImage(tou, touxiang, options);
//        }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.touxiang:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),66);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //得到返回来的值
        String tou = data.getStringExtra("tou");
        String name = data.getStringExtra("name");
        //设置值
        myname.setText(name);
        ImageLoader.getInstance().displayImage(tou, touxiang, options);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
