package com.hhzmy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bw.honghaizidemo.R;
import com.google.gson.Gson;
import com.hhzmy.bean.XQBean;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asus on 2016/11/9.
 * 图片详情
 */
public class GDDetailsPicFragment extends Fragment {

    @Bind(R.id.gd_details_webView_pic)
    WebView gdDetailsWebViewPic;
    private XQBean mXQBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.gd_details_pic, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getTexts();
        gdDetailsWebViewPic.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String htmlData = mXQBean.getItemDetail().getDetailUrl();
        htmlData = htmlData.replaceAll("&", "");
        htmlData = htmlData.replaceAll("quot;", "\"");
        htmlData = htmlData.replaceAll("lt;", "<");
        htmlData = htmlData.replaceAll("gt;", ">");
        gdDetailsWebViewPic.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
        super.onActivityCreated(savedInstanceState);
    }

    //解析
    public void getTexts() {
        try {
            InputStream is = getActivity().getAssets().open("xq.txt");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, "GB2312");

            Gson gson = new Gson();
            mXQBean = gson.fromJson(text, XQBean.class);

//            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
