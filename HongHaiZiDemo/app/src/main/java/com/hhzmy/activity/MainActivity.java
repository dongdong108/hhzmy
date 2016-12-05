package com.hhzmy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.bw.honghaizidemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Timer计时器
        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent mIntent = new Intent(MainActivity.this, PagerLandActivity.class);
                startActivity(mIntent);
                finish();
            }
        };

        timer.schedule(task, 3000);
    }

    // ActiVity生命周期 判断并关闭计时器Timer
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (timer != null) {
            // 关闭计时器
            timer.cancel();
        }
    }
}
