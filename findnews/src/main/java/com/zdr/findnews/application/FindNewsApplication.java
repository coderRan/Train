package com.zdr.findnews.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.zdr.findnews.netUtils.VolleyManager;
import com.zdr.findnews.utils.Constans;
import com.zdr.findnews.utils.DataManager;

/**
 * 应用启动时执行初始化资源
 * Created by zdr on 16-8-20.
 */
public class FindNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化volley
        VolleyManager.initVolleyManager(this);

        SharedPreferences sp = getSharedPreferences(Constans.SP_FILE, MODE_PRIVATE);
        if(sp.getBoolean("first", true)){
            //向数据库添加新闻类型数据
            DataManager.getDM().initNewsType(this);

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", false);
            editor.apply();
        }else {

        }

    }
}
