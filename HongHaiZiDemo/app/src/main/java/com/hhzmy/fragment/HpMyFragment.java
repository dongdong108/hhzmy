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
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.touxiang:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
