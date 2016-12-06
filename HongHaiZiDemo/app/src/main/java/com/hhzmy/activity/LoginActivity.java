package com.hhzmy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.hhzmy.view.ClearableEditText;
import com.hhzmy.view.Code;
import com.hhzmy.view.PhoneTextWatcher;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String getCode = null; //获取验证码的值
    @Bind(R.id.fanhui)
    ImageView fanhui;
    @Bind(R.id.username)
    EditText username;
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
    //得到头像
    private String iconurl;
    //得到昵称
    private String screenname;

    public String getScreenname() {
        return screenname;
    }

    public void setScreenname(String screenname) {
        this.screenname = screenname;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        username.addTextChangedListener(new PhoneTextWatcher(username));
        mianfeizhuce.setOnClickListener(this);
        passBtn.setOnClickListener(this);
        Image.setOnClickListener(this);
        weixinImage.setOnClickListener(this);
        sinaImage.setOnClickListener(this);

        //验证码 b
        getCodees();


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
                String phone = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (phone.length()<11){
                    Toast.makeText(getApplication(), "手机号不正确", Toast.LENGTH_SHORT).show();
                }else if(phone==null){
                    Toast.makeText(getApplication(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(pass==null){
                    Toast.makeText(getApplication(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    if (v_code == null || v_code.equals("")) {
                        Toast.makeText(getApplication(), "没有填写验证码", Toast.LENGTH_SHORT).show();
                    } else if (!v_code.equals(getCode)) {
                        Toast.makeText(getApplication(), "验证码填写不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "操作成功", Toast.LENGTH_SHORT).show();
                    }
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
            case R.id.Image:
                UMShareAPI  mShareAPI = UMShareAPI.get( LoginActivity.this );
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);


                break;

            case R.id.weixinImage:
                UMShareAPI  mShareAPI1 = UMShareAPI.get( LoginActivity.this );
                mShareAPI1.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);

                break;

            case R.id.sinaImage:
                UMShareAPI  mShareAPI2 = UMShareAPI.get( LoginActivity.this );
                mShareAPI2.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }
//    登录
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
