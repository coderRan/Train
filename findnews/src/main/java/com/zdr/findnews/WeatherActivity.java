package com.zdr.findnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.zdr.findnews.entity.Weather;
import com.zdr.findnews.netUtils.Callback;
import com.zdr.findnews.netUtils.VolleyManager;
import com.zdr.findnews.utils.Constans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {
    String httpUrl = "http://apis.baidu.com/apistore/weatherservice/recentweathers?cityname=烟台";
    @BindView(R.id.iv_weather_back)
    ImageView ivWeatherBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        getJSON();
    }

    public void getJSON() {
        VolleyManager.get(httpUrl)
                .setMethod(Request.Method.GET)
                .setCallbacl(new WeatherCallback())
                .buile()
                .setParams("cityname", "烟台")
                .addHeader("apikey", Constans.API_KEY)
                .start();


    }

    @OnClick(R.id.iv_weather_back)
    public void onClick() {
        finish();
    }

    private class WeatherCallback implements Callback {

        @Override
        public void onSuccess(String respone) {
            Weather weather = Constans.GSON.fromJson(respone, Weather.class);
            Log.e("CITY", weather.getRetData().getToday().getType());
        }

        @Override
        public void onError(String err) {

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
