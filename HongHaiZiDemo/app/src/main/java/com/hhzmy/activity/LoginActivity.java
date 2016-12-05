package com.hhzmy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.view.ClearableEditText;
import com.hhzmy.view.Code;
import com.hhzmy.view.PhoneTextWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String getCode = null; //获取验证码的值
    @Bind(R.id.fanhui)
    ImageView fanhui;
    @Bind(R.id.username)
    ClearableEditText username;
    @Bind(R.id.password)
    ClearableEditText password;
    @Bind(R.id.yanzhengma)
    ClearableEditText yanzhengma;
    @Bind(R.id.yanzhengImg)
    ImageView yanzhengImg;
    @Bind(R.id.dengluanniu)
    Button dengluanniu;
    @Bind(R.id.wangjimima)
    TextView wangjimima;
    @Bind(R.id.mianfeizhuce)
    ImageView mianfeizhuce;
    @Bind(R.id.Image)
    ImageView Image;
    @Bind(R.id.sinaImage)
    ImageView sinaImage;
    @Bind(R.id.weixinImage)
    ImageView weixinImage;
    @Bind(R.id.pass_rb)
    RadioButton passBtn;
    private boolean mbDisplayFlg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        username.addTextChangedListener(new PhoneTextWatcher(username));
//        password.addTextChangedListener(new PhoneTextWatcher(password));
        mianfeizhuce.setOnClickListener(this);
        passBtn.setOnClickListener(this);

        //判断登录按钮
        setBetton();

        //验证码
        getCodees();

    }

    private void setBetton() {
        String name = username.getText().toString();
        String pass = password.getText().toString();
        if (!name.equals(null)&&!pass.equals(null)){

            dengluanniu.setBackgroundColor(Color.RED);
            dengluanniu.setTextColor(Color.WHITE);
            dengluanniu.setEnabled(true);
        }else{
            dengluanniu.setEnabled(false);
            dengluanniu.setBackgroundColor(Color.WHITE);
            dengluanniu.setTextColor(Color.BLACK);
        }

    }

    private void getCodees() {
        //生成验证码
        yanzhengImg.setImageBitmap(Code.getInstance().getBitmap());
        getCode = Code.getInstance().getCode(); //获取显示的验证码
        yanzhengImg.setOnClickListener(this);

        dengluanniu.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mianfeizhuce:
                startActivity(new Intent(getApplication(), ZhuCeActivity.class));
                break;

            //验证码刷新
            case R.id.yanzhengImg:
                yanzhengImg.setImageBitmap(Code.getInstance().getBitmap());
                getCode = Code.getInstance().getCode();
                break;

            case R.id.dengluanniu:
                String v_code = yanzhengma.getText().toString().trim();
                if (v_code == null || v_code.equals("")) {
                    Toast.makeText(getApplication(), "没有填写验证码", Toast.LENGTH_SHORT).show();
                } else if (!v_code.equals(getCode)) {
                    Toast.makeText(getApplication(), "验证码填写不正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "操作成功", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pass_rb:
                Log.d("AndroidTest", "mbDisplayFlg = " + mbDisplayFlg);
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passBtn.setChecked(true);
                } else {
                    // hide password, display "."
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passBtn.setChecked(false);
                }
                mbDisplayFlg = !mbDisplayFlg;
                password.postInvalidate();
                break;
        }
    }
}
