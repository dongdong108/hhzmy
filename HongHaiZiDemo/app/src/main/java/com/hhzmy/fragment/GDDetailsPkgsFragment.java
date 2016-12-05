package com.hhzmy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bw.honghaizidemo.R;
import com.hhzmy.tools.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/9.
 * 包装售后
 */
public class GDDetailsPkgsFragment extends Fragment {
    @Bind(R.id.gd_details_webView_pkgs)
    WebView gdDetailsWebViewPkgs;
    private String path = "http://product.suning.com/pds-web/product/graphicSaleApp/0000000000/102295661.html";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.gd_details_pkgs, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Tools.getWebView(gdDetailsWebViewPkgs, path);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
