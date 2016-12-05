package com.hhzmy.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.honghaizidemo.R;
import com.google.gson.Gson;
import com.hhzmy.bean.CarData;
import com.hhzmy.bean.ShopCarDataBean;
import com.hhzmy.httputils.OkHttp;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Request;


public class ShopCarActivity extends Activity implements View.OnClickListener {

    private ListView shop_lv_view;
    private CheckBox checkall;
    private TextView shop_editbutton;
    private RelativeLayout rela_allprice;
    private Button shop_bayall;
    private String shop_bayall_text;
    private String shop_editbutton_text;
    private TextView allprice;
    private String json;
    private double sum = 0.00;
    private shop_lv_adapter adapter;
    private List<CarData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopcar_frag_layout);
        //获取控件ID
        infoview();
        //得到购物车数据
        getCarData();
        checkall.setOnClickListener(this);
        shop_editbutton.setOnClickListener(this);
        shop_bayall.setOnClickListener(this);
    }

    private void infoview() {
        shop_lv_view = (ListView) findViewById(R.id.shop_lv_view);
        checkall = (CheckBox) findViewById(R.id.checkall);
        shop_editbutton = (TextView) findViewById(R.id.shop_editbutton);
        rela_allprice = (RelativeLayout) findViewById(R.id.rela_allprice);
        shop_bayall = (Button) findViewById(R.id.shop_bayall);
        allprice = (TextView) findViewById(R.id.allprice);
    }

    //请求数据
    public void getCarData() {
        OkHttp.getAsync("http://mock.eoapi.cn/success/megQ2CBFAeFzJzIwTSVdNnpQYZCrsrIq", new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                json = result;
                //Toast.makeText(ShopCarActivity.this, json + "", Toast.LENGTH_SHORT).show();
                showData(json);
            }
        });
    }

    //展示数据
    public void showData(String json) {
        //Toast.makeText(ShopCarActivity.this, json + "", Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        ShopCarDataBean shopping = gson.fromJson(json, ShopCarDataBean.class);
        //商品型号
        List<ShopCarDataBean.DataBeanX.Data1Bean.DataBean.ItemClusterDisplayVOBean.ColorListBean> cl = shopping.getData().getData1().getData().getItemClusterDisplayVO().getColorList();
        //商品名称
        ShopCarDataBean.DataBeanX.Data1Bean.DataBean.ItemInfoVoBean name = shopping.getData().getData1().getData().getItemInfoVo();

        //价格
        ShopCarDataBean.DataBeanX.PriceBean.SaleInfoBean jia = shopping.getData().getPrice().getSaleInfo().get(0);
        //jia1.setText(jia.getNetPrice());现价
        //jia2.setText(jia.getRefPrice());原价
        list = new ArrayList<>();
        CarData cardata = new CarData();
        cardata.setGoodName(name.getItemName() + "");
        cardata.setNowPrice(Double.parseDouble(jia.getNetPrice()));
        cardata.setOriginalPrice(Double.parseDouble(jia.getRefPrice()));
        cardata.setGoodCL(cl.get(0).getCharacterValueName() + "");
        list.add(cardata);
        //Toast.makeText(ShopCarActivity.this, list.get(0).getNowPrice() + "", Toast.LENGTH_SHORT).show();
        adapter = new shop_lv_adapter(ShopCarActivity.this, list);
        shop_lv_view.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkall:
                boolean flag = checkall.isChecked();
                double f1 = 0;
                for (int i = 0; i < adapter.getSelect().size(); i++) {
                    adapter.getSelect().set(i, flag);
                }
                if (flag == true) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0) {
                            sum = 0.00;
                        }
                        sum = sum + (list.get(i).getNowPrice()) * 1;
                        BigDecimal b1 = new BigDecimal(Double.toString(sum));
                        BigDecimal b2 = new BigDecimal(Double.toString(1));
                        f1 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    allprice.setText("总计:￥" + f1);
                } else if (flag == false) {
                    sum = 0.00;
                    allprice.setText("总计:￥" + sum);
                }
                // shop_lv_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.shop_editbutton:
                shop_editbutton_text = shop_editbutton.getText().toString();
                shop_bayall_text = shop_bayall.getText().toString();
                if (shop_editbutton_text.equals("编辑")) {
                    shop_editbutton.setText("完成");
                    rela_allprice.setVisibility(View.GONE);
                    if (shop_bayall_text.equals("结算")) {
                        shop_bayall.setText("删除");
                    }
                } else if (shop_editbutton_text.equals("完成")) {
                    shop_editbutton.setText("编辑");
                    rela_allprice.setVisibility(View.VISIBLE);
                    if (shop_bayall_text.equals("删除")) {
                        shop_bayall.setText("结算");
                    }
                }
                break;
            case R.id.shop_bayall:
                shop_bayall_text = shop_bayall.getText().toString();
                if (shop_bayall_text.equals("删除")) {
                    /*for (int i = 0; i < list.size(); i++) {
                        sum = sum - (list.get(i).getNowPrice());
                        shop_lv_view.setAdapter(new shop_lv_adapter(ShopCarActivity.this, null));
                        adapter.notifyDataSetChanged();
                    }
                    allprice.setText("总计:￥" + sum);*/
                    Toast.makeText(ShopCarActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else if (shop_bayall_text.equals("结算")) {
                    Toast.makeText(ShopCarActivity.this, "准备结算", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ShopCarActivity.this, PayDemoActivity.class);
//                    startActivity(intent);

                }
                break;
        }
    }

    // 适配器
    class shop_lv_adapter extends BaseAdapter {
        private Context context;
        private List<CarData> list;
        private LinkedList<Boolean> linkedList = new LinkedList<Boolean>();

        public shop_lv_adapter(Context context, List<CarData> list) {
            super();
            this.context = context;
            this.list = list;
            // 初始化
            for (int i = 0; i < list.size(); i++) {
                getSelect().add(false);
            }
        }

        private List<Boolean> getSelect() {
            return linkedList;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                convertView = convertView.inflate(context, R.layout.shop_lv_item, null);
                vh = new ViewHolder();
                vh.shop_checkbox = (CheckBox) convertView.findViewById(R.id.shop_checkbox);
                vh.shop_goodimg = (ImageView) convertView.findViewById(R.id.shop_goodimg);
                vh.shop_goodname = (TextView) convertView.findViewById(R.id.shop_goodname);
                vh.shop_price = (TextView) convertView.findViewById(R.id.shop_price);
                vh.shop_count = (TextView) convertView.findViewById(R.id.shop_count);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.shop_goodname.setText(list.get(position).getGoodName() + "");
            vh.shop_count.setText("数量:1");
            vh.shop_price.setText("现价:￥" + list.get(position).getNowPrice() + "元");

            vh.shop_checkbox.setChecked(linkedList.get(position));
            // 不能用onCheckChangedListner()复用的时候
            vh.shop_checkbox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    linkedList.set(position, !linkedList.get(position));
                    if (linkedList.contains(false)) {
                        checkall.setChecked(false);
                    } else {
                        checkall.setChecked(true);
                    }
                    if (vh.shop_checkbox.isChecked() == true) {
                        sum = sum + (list.get(position).getNowPrice());
                        //goodid_list.add(list.get(position).getGoodid());
                    } else if (vh.shop_checkbox.isChecked() == false) {
                        sum = sum - (list.get(position).getNowPrice());
                        //list.remove(list.get(position));
                    }
                    //计算
                    //BigDecimal b1 = new BigDecimal(Double.toString(sum));
                    //BigDecimal b2 = new BigDecimal(Double.toString(1));
                    //double f1 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    allprice.setText("总计:￥" + sum);
                    // double
                    // sum=(list.get(position).getPrice())*(list.get(position).getCount());
                    // Toast.makeText(context, "点击了"+position+"的checkbox",
                    // 0).show();
                    // 刷新
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            CheckBox shop_checkbox;
            ImageView shop_goodimg;
            TextView shop_goodname, shop_price, shop_count;
        }
    }
}

