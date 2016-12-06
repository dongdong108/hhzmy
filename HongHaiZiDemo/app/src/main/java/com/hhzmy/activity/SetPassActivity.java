package com.hhzmy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.httputils.OkHttp;
import com.hhzmy.view.ClearableEditText;
import com.hhzmy.view.TimerButton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

public class SetPassActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv_duanxin)
    TextView tvDuanxin;
    @Bind(R.id.pass_rb2)
    RadioButton passRb2;
    @Bind(R.id.new_btn)
    TimerButton newBtn;
    @Bind(R.id.set_pass)
    ClearableEditText setPass;
    @Bind(R.id.btn_tijiao)
    Button btnTijiao;
    @Bind(R.id.yan)
    ClearableEditText yan;
    private String path = "counter/userOperAction/register";

    private boolean mbDisplayFlg = false;
    //手机号
    private String num;
    //密码
    private String pass;
    //验证码
    private String yanzheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);
        ButterKnife.bind(this);

        //得到 手机号
        getPhoneNum();
        //短信验证的button
        newBtn.setOnClickListener(this);
        newBtn.setTextAfter("").setTextBefore("重新获取").setLenght(60 * 1000);
        passRb2.setOnClickListener(this);
        btnTijiao.setOnClickListener(this);
    }


    private void getPhoneNum() {
        Intent intent = getIntent();
        num = intent.getStringExtra("phone");

        tvDuanxin.setText("短信验证码已发送至" + num);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pass_rb2:
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    setPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passRb2.setChecked(true);
                } else {
                    // hide password, display "."
                    setPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passRb2.setChecked(false);
                }
                mbDisplayFlg = !mbDisplayFlg;
                setPass.postInvalidate();
                break;
            case R.id.new_btn:

                break;

            case R.id.btn_tijiao:
                pass = setPass.getText().toString().trim();
                yanzheng = yan.getText().toString().trim();

                Map<String,String>  map = new HashMap<>();

                OkHttp.postAsync(path + "?user_phone=" + num + "&user_pwd=" + pass + "&random=" + yanzheng, map, new OkHttp.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        newBtn.onDestroy();
        super.onDestroy();
    }

}
