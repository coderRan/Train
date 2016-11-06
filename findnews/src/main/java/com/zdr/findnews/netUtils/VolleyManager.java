package com.zdr.findnews.netUtils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 对volley请求 NetNewsRequest 进行管理。对volley的二次封装
 * Created by zdr on 16-8-20.
 */
public class VolleyManager {
    private static RequestQueue requestQueue;

    /**
     * 初始化vollye请求队列
     */
    public static void initVolleyManager(Context context){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    /**
     * 返回一个get请求  NetNewsRequest 的便捷类 NetNewsRequest.Builder
     * 用于创建get请求
     * @param url 请求的url
     * @return 创建该请求的便捷类
     */
    public static NetNewsRequest.Builder get(String url){

        return new NetNewsRequest.Builder()
                .setMethod(Request.Method.GET)
                .setUrl(url);
    }
    /**
     * 返回一个post请求 NetNewsRequest 的便捷类 NetNewsRequest.Builder
     * 用于创建post请求
     * @param url 请求的url
     * @return 创建该请求的便捷类
     */
    public static NetNewsRequest.Builder post(String url){
        return new NetNewsRequest.Builder()
                .setMethod(Request.Method.POST)
                .setUrl(url);

    }

    /**
     * 启动一个请求，将该请求加入到请求队列
     * @param request 待加入的请求
     */
    public static void start(NetNewsRequest request){
        if(request!=null)
            requestQueue.add(request);
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
