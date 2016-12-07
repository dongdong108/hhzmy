package com.hhzmy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.hhzmy.httputils.OkHttp;
import com.hhzmy.view.ClearableEditText;
import com.hhzmy.view.TimerButton;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
    private String path = "http://60.205.92.165:8080/userOperAction/register";

    private boolean mbDisplayFlg = false;
    //手机号
    private String num;
    //密码
    private String pass;
    //验证码
    private String yanzheng;
    private String nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass);
        ButterKnife.bind(this);

        //得到 手机号
        getPhoneNum();
        //设置button的可点击
        setBtn();
        //短信验证的button
        newBtn.setOnClickListener(this);
        newBtn.setTextAfter("").setTextBefore("重新获取").setLenght(60 * 1000);
        passRb2.setOnClickListener(this);
        btnTijiao.setOnClickListener(this);

    }
    private void setBtn() {
        yan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setPass.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (!yan.getText().toString().trim().isEmpty()&&!setPass.getText().toString().trim().isEmpty()) {
                            //设置背景
                            btnTijiao.setBackgroundColor(Color.parseColor("#ffff99"));
                            btnTijiao.setEnabled(true);

                        } else {
                            btnTijiao.setBackgroundColor(Color.parseColor("#cccccc"));
                            btnTijiao.setEnabled(false);
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
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //发送验证码
                        sendYan();
                    }
                }.start();

                break;

            case R.id.btn_tijiao:
                pass = setPass.getText().toString().trim();
                yanzheng = yan.getText().toString().trim();
                Toast.makeText(getApplicationContext(),pass+"---"+yanzheng,Toast.LENGTH_SHORT).show();
                Map<String,String>  map = new HashMap<>();

                OkHttp.postAsync(path + "?user_phone=" + num + "&user_pwd=" + pass + "&random=" + yanzheng, map, new OkHttp.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, IOException e) {
                        Toast.makeText(getApplicationContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
//                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        JSONObject object = new JSONObject(result);
                        String code = object.getString("code");
                        String message_code = object.getString("message_code");
                        Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                        if(code.equals("200")){
                            Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else if (code.equals("400")){
                            Toast.makeText(getApplicationContext(),"注册失败！"+message_code,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
    //发送验证码
    private void sendYan() {
        HashMap<String, Object> result = null;

        // 初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        // ******************************注释*********************************************
        // *初始化服务器地址和端口 *
        // *沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        // *生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883"); *
        // *******************************************************************************
        restAPI.init("app.cloopen.com", "8883");

        // ******************************注释*********************************************
        // *初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN *
        // *ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        // *参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。 *
        // *******************************************************************************
        restAPI.setAccount("8aaf070858cd982e0158d8e7523508d9","8bc4959659d2459894ed11383e5fd84c");

        // ******************************注释*********************************************
        // *初始化应用ID *
        // *测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID *
        // *应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        // *******************************************************************************
        restAPI.setAppId("8a216da858ce0b3c0158d8e92a86087d");

        // ******************************注释****************************************************************
        // *调用发送模板短信的接口发送短信 *
        // *参数顺序说明： *
        // *第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号 *
        // *第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。 *
        // *系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
        // *第三个参数是要替换的内容数组。 *
        // **************************************************************************************************

        // **************************************举例说明***********************************************************************
        // *假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为
        // *
        // *result = restAPI.sendTemplateSMS("13800000000","1" ,new
        // String[]{"6532","5"}); *
        // *则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入 *
        // *********************************************************************************************************************
        // 生成随机数 4位

        nums = (new Random().nextInt(8999) + 1000) + "";
        result = restAPI.sendTemplateSMS("15810203679", "1", new String[] {nums, "3" });

        System.out.println("SDKTestGetSubAccounts result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            // 正常返回输出data包体信息（map）
            @SuppressWarnings("unchecked")
            HashMap<String, Object> data = (HashMap<String, Object>) result
                    .get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            // 异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= "
                    + result.get("statusMsg"));
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        newBtn.onDestroy();
        super.onDestroy();
    }

}
