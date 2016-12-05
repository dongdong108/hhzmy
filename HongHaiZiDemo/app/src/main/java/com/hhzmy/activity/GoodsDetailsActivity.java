package com.hhzmy.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.honghaizidemo.R;
import com.hhzmy.adapter.GDViewPagerAdapter;
import com.hhzmy.fragment.GDCommentFragment;
import com.hhzmy.fragment.GDDetailsFragment;
import com.hhzmy.fragment.GDGoodsFragment;
import com.hhzmy.tools.Tools;
import com.hhzmy.zhufubao.AuthResult;
import com.hhzmy.zhufubao.PayResult;
import com.hhzmy.zhufubao.util.OrderInfoUtil2_0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetailsActivity extends FragmentActivity implements View.OnClickListener {

    @Bind(R.id.store_back)
    ImageView storeBack;
    @Bind(R.id.goods)
    TextView goods;
    @Bind(R.id.details)
    TextView details;
    @Bind(R.id.assess)
    TextView assess;
    @Bind(R.id.btn_shopcar)
    ImageView btnShopcar;
    @Bind(R.id.btn_)
    ImageView btn;
    @Bind(R.id.goods_vp)
    ViewPager goodsVp;
    @Bind(R.id.goods_kefu)
    Button goodsKefu;
    @Bind(R.id.goods_shoucang)
    Button goodsShoucang;
    @Bind(R.id.goods_shop)
    Button goodsShop;
    @Bind(R.id.goods_lijigoumai)
    TextView goodsLijigoumai;
    //储存fragment的集合
    private List<Fragment> mFragments;
    private TextView addShopCar;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(GoodsDetailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(GoodsDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(GoodsDetailsActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(GoodsDetailsActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);

        addShopCar = (TextView) findViewById(R.id.goods_addShopCar);
        addShopCar.setOnClickListener(this);
        //添加数据
        addDatas();
        goods.setOnClickListener(this);
        details.setOnClickListener(this);
        assess.setOnClickListener(this);

        goods.setTextColor(Color.RED);
        details.setTextColor(Color.BLACK);
        assess.setTextColor(Color.BLACK);
        //viewpager的适配和选择
        vpsetAdapter();

        goodsLijigoumai.setOnClickListener(this);
    }

    private void vpsetAdapter() {
        //给viewpager设置适配器
        goodsVp.setAdapter(new GDViewPagerAdapter(getSupportFragmentManager(), mFragments));
        //给viewpager设置滑动选择事件
        goodsVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        goods.setTextColor(Color.RED);
                        details.setTextColor(Color.BLACK);
                        assess.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        goods.setTextColor(Color.BLACK);
                        details.setTextColor(Color.RED);
                        assess.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        goods.setTextColor(Color.BLACK);
                        details.setTextColor(Color.BLACK);
                        assess.setTextColor(Color.RED);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void addDatas() {
        mFragments = new ArrayList<>();

        mFragments.add(new GDGoodsFragment());
        mFragments.add(new GDDetailsFragment());
        mFragments.add(new GDCommentFragment());
    }


    @Override
    public void onClick(View view) {
        //判断点击的是哪个textView
        switch (view.getId()) {
            case R.id.goods:
                goodsVp.setCurrentItem(0);
                goods.setTextColor(Color.RED);
                details.setTextColor(Color.BLACK);
                assess.setTextColor(Color.BLACK);
                break;
            case R.id.details:
                goodsVp.setCurrentItem(1);
                goods.setTextColor(Color.BLACK);
                details.setTextColor(Color.RED);
                assess.setTextColor(Color.BLACK);
                break;
            case R.id.assess:
                goodsVp.setCurrentItem(2);
                goods.setTextColor(Color.BLACK);
                details.setTextColor(Color.BLACK);
                assess.setTextColor(Color.RED);
                break;
            case R.id.goods_lijigoumai:
//                startActivity(new Intent(this, PayDemoActivity.class));
                if (TextUtils.isEmpty(Tools.APPID) || TextUtils.isEmpty(Tools.RSA_PRIVATE)) {
                    new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
                    return;
                }

                /**
                 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
                 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
                 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
                 *
                 * orderInfo的获取必须来自服务端；
                 */
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Tools.APPID);
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                String sign = OrderInfoUtil2_0.getSign(params, Tools.RSA_PRIVATE);
                final String orderInfo = orderParam + "&" + sign;

                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(GoodsDetailsActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
                break;
            case R.id.goods_addShopCar:
                startActivity(new Intent(this, ShopCarActivity.class));
                break;
        }
    }

}
