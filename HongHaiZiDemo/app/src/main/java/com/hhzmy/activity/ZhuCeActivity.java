package com.hhzmy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.view.ClearableEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZhuCeActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.yibu)
    Button yibu;
    @Bind(R.id.qiyehuiyuan)
    TextView qiyehuiyuan;
    String phonenum = "";
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.pohne)
    ClearableEditText phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        ButterKnife.bind(this);

        //图文混排
        tvandImg();
//        phone.addTextChangedListener(new PhoneTextWatcher(phone));
        yibu.setOnClickListener(this);
    }

    public void xiayibu(View view) {
        phonenum = phone.getText().toString().trim();
        if (!isPhoneNumberValid(phonenum)) {
            Toast.makeText(ZhuCeActivity.this, "电话格式不正确,请检查！", Toast.LENGTH_SHORT).show();
        } else if (phonenum.length() == 0) {
            Toast.makeText(ZhuCeActivity.this, "请输入电话号码！", Toast.LENGTH_SHORT).show();
        } else if (!checkBox.isChecked()) {
            Toast.makeText(ZhuCeActivity.this, "需要同意", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ZhuCeActivity.this, SetPassActivity.class);
            intent.putExtra("phone",phonenum);
            startActivity(intent);
        }
    }

    /*检查字符串是否为电话号码的方法,并回传true or false的判断值*/
    public static boolean isPhoneNumberValid(String mobiles) {
        Matcher m = null;
        if (mobiles.trim().length() > 0) {
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-3])|(15[7-9])|(18[0,5-9]))\\d{8}$");
            m = p.matcher(mobiles);
        } else {
            return false;
        }
        return m.matches();
    }


    private void tvandImg() {
        SpannableString word = new SpannableString("同意苏宁易购会员章程和易付宝协议");

        word.setSpan(new ForegroundColorSpan(Color.RED), 2, 10,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), 11, word.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.append(word);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yibu:
                xiayibu(view);
                break;
        }
    }
}
