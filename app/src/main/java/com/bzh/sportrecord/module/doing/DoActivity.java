package com.bzh.sportrecord.module.doing;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.R;
import com.bzh.sportrecord.base.activity.BaseActivity;

import java.util.List;

import butterknife.BindView;

/**
 * 做运动的界面
 */
public class DoActivity extends BaseActivity {

    @BindView(R.id.do_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.do_count)
    TextView textView;

    private SensorManager mSenserManager;

    private int initVal = 0;
    private boolean isReception; //是否在前台
    private boolean initSign; //第一次

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_do;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar.setTitle("运动中");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initVal = Integer.parseInt(textView.getText().toString());
        mSenserManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);//获取感应器服务
        //TYPE_ACCELEROMETER:加速度传感器;TYPE_LIGHT:获取光线传感器;TYPE_GYROSCOPE:陀螺仪传感器;TYPE_AMBIENT_TEMPERATURE:温度传感器
        //TYPE_PROXIMITY:距离传感器;TYPE_STEP_COUNTER:计步统计;TYPE_STEP_DETECTOR:单次计步;
        Sensor mSensor = mSenserManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);//获取距离传感器
        mSenserManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL); //注册传感器事件
    }

    public static void open(Context context) {
        context.startActivity(new Intent(context, DoActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听器
        if (mSenserManager != null) {
            mSenserManager.unregisterListener(listener);
        }
    }

    //传感器监听事件
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) { //当感应器精度发生变化
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (isReception & initSign) {
                    if (event.values[0] > 0) {
                        initVal = initVal - 1;
                        textView.setText(String.valueOf(initVal));
                        if(initVal == 0){
                            mSenserManager.unregisterListener(listener);

                        }
                    }
                }
                initSign = true;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { //当传感器监测到的数值发生变化时

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        isReception = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isReception = false;
    }
}
