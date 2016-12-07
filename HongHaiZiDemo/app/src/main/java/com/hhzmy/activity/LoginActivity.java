package com.hhzmy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.hhzmy.httputils.OkHttp;
import com.hhzmy.view.ClearableEditText;
import com.hhzmy.view.Code;
import com.hhzmy.view.PhoneTextWatcher;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

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
    private String url = "http://60.205.92.165:8080/userOperAction/logon";
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

        //设置button的可点击
        setBtn();
    }

    private void setBtn() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!username.getText().toString().trim().isEmpty()&&!password.getText().toString().trim().isEmpty()) {
                            //设置背景
                            dengluanniu.setBackgroundColor(Color.parseColor("#ffff99"));
                            dengluanniu.setEnabled(true);

                        } else {
                            dengluanniu.setBackgroundColor(Color.parseColor("#cccccc"));
                            dengluanniu.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                String phone = username.getText().toString().replaceAll(" ", "").trim();
                String pass = password.getText().toString().trim();
                if (phone.length()<11&&phone.length()>0){
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
//                        Toast.makeText(getApplication(), "操作成功", Toast.LENGTH_SHORT).show();
                        //手机号登录
                        loginByphone(phone,pass);
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

    private void loginByphone(String phone,String pass) {
        Map<String,String>  map = new HashMap<>();
        map.put("type","1");

        OkHttp.postAsync(url + "?user_phone=" + phone + "&verify_code=" + pass, map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject object = new JSONObject(result);
                String code = object.getString("code");
                String message_code = object.getString("message_code");
                Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                if(code.equals("200")){
                    Toast.makeText(getApplicationContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                }else if (code.equals("400")){
                    Toast.makeText(getApplicationContext(),"登录失败！"+message_code,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    登录
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String url = data.get("profile_image_url");
            String name = data.get("screen_name");
            //把得到的值回传回去
            setResult(66,new Intent().putExtra("name",name).putExtra("tou",url));
            //关闭当前页面
            finish();

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
