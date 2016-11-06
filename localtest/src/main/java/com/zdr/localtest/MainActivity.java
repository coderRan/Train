package com.zdr.localtest;

import android.graphics.BitmapFactory;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AMapLocationListener{
    AMapLocationClient locationClient;
    AMapLocationClientOption locationClientOption;
    MapView mapView;
    AMap aMap;
    MarkerOptions markerOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationClient = new AMapLocationClient(this);
        locationClientOption = new AMapLocationClientOption();
        setLocation();
        locationClient.setLocationOption(locationClientOption);
        locationClient.startLocation();
        locationClient.setLocationListener(this);
        mapView = (MapView) findViewById(R.id.mv_map);
        mapView.onCreate(savedInstanceState);

        aMap = mapView.getMap();

    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void setLocation(){
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setOnceLocation(false);
        locationClientOption.setNeedAddress(true);
        locationClientOption.setInterval(2000);
        locationClientOption.setHttpTimeOut(5000);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null&& aMapLocation.getErrorCode() == 0){
            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            aMapLocation.getLatitude();//获取纬度
            aMapLocation.getLongitude();//获取经度
            aMapLocation.getAccuracy();//获取精度信息
            aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            aMapLocation.getCountry();//国家信息
            aMapLocation.getProvince();//省信息
            aMapLocation.getCity();//城市信息
            aMapLocation.getDistrict();//城区信息
            aMapLocation.getStreet();//街道信息
            aMapLocation.getStreetNum();//街道门牌号信息
            aMapLocation.getCityCode();//城市编码
            aMapLocation.getAdCode();//地区编码
            aMapLocation.getAoiName();//获取当前定位点的AOI信息
            //获取定位时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(aMapLocation.getTime());
            df.format(date);
            Log.e("aMapLocation", aMapLocation.getAddress());
            LatLng latLng = new LatLng(aMapLocation.getLongitude(),aMapLocation.getLatitude());

            markerOption = new MarkerOptions();
            markerOption.position(new LatLng(aMapLocation.getLongitude(),aMapLocation.getLatitude()));
            markerOption.title(aMapLocation.getCity()).snippet("我的位置");

            markerOption.draggable(true);
            markerOption.icon(

                    BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),
                                    R.mipmap.ic_launcher)));
            // 将Marker设置为贴地显示，可以双指下拉看效果
            //markerOption.setFlat(true);

            aMap.addMarker(markerOption);


        }
        assert aMapLocation != null;
        if(aMapLocation.getErrorCode()!=0){
            Log.e("定位回调", aMapLocation.getAddress());


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.onDestroy();
        mapView.onDestroy();
    }
}
