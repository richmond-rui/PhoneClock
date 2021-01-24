package com.lanlengran.photoclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Network;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LockClockActivity extends AppCompatActivity {
    private static final String TAG = "LockClockActivity";
    private static String weathUrl="http://wthrcdn.etouch.cn/weather_mini?city=";
    public static String area="南京";
    private TextView tv_time;
    private TextView tv_temperature;
    private TextView tv_tips;
    private TextView tv_weather;
    private TextView tv_extra_tips;
    private ImageView img_weather;
    private ImageView img_backgroud;
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
    private long lastFetchWeathTime=0;
    private long fetchTimeInterval=4*60*60*1000;
    private String extraTips="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_clock);
        tv_time = findViewById(R.id.tv_time);
        tv_temperature = findViewById(R.id.tv_temperature);
        img_weather = findViewById(R.id.img_weather);
        img_backgroud = findViewById(R.id.img_backgroud);
        tv_tips = findViewById(R.id.tv_tips);
        tv_weather = findViewById(R.id.tv_weather);
        tv_extra_tips = findViewById(R.id.tv_extra_tips);
        freshTime();
        freshWeather(area);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void freshWeather(String area) {
        NetWorkUtils.get(weathUrl+NetWorkUtils.toURLEncoded(area), new NetWorkUtils.NetWorkCallBack() {
            @Override
            public void onSuccuss(String string) {
                Log.d(TAG, "onSuccuss: "+string);
                try {
                    final WeathResp weathResp = new Gson().fromJson(string, WeathResp.class);
                    if (weathResp != null && weathResp.data != null) {
                        LockClockActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setFreshWeather(weathResp);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError() {

            }
        });

    }

    private void setFreshWeather(WeathResp weathResp) {
        if (!TextUtils.isEmpty(weathResp.data.wendu)){
            tv_temperature.setText(weathResp.data.wendu + "℃");
        }
        if (weathResp.data.forecast!=null&&weathResp.data.forecast.size()>0){
            WeathResp.DataBean.ForecastBean forecastBean=weathResp.data.forecast.get(0);
            tv_weather.setText(forecastBean.type);
        }

        tv_extra_tips.setText(weathResp.data.ganmao);
        lastFetchWeathTime=System.currentTimeMillis();
        freshDate();
    }

    private void freshTime() {

        String dateString = timeFormatter.format(System.currentTimeMillis());
        tv_time.setText(dateString);
        freshDate();
        if ((System.currentTimeMillis()-lastFetchWeathTime)>fetchTimeInterval){
            freshWeather(area);
        }
    }

    private void freshDate() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E  MM月dd日");
        if (TextUtils.isEmpty(extraTips)){
            tv_tips.setText(format.format(date));
        }else {
            tv_tips.setText(format.format(date)+"    "+extraTips);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(timeReciver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(timeReciver);
    }

    private final BroadcastReceiver timeReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK)) {
                freshTime();
            }
        }
    };
}