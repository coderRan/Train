package com.zdr.findnews.netUtils;

/**
 * vooley请求的回调方法实现接口
 * Created by zdr on 16-8-20.
 */
public interface Callback {

    /**
     * 请求成功时回调该方法
     * @param respone 请求返回的结果
     */
    void onSuccess(String respone);

    /**
     * 请求失败时回调该方法
     * @param err 失败的错误
     */
    void onError(String err);

}
