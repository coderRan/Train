package com.zdr.findnews.netUtils;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 对volley请求的二次封装
 * Created by zdr on 16-8-20.
 */
public class NetNewsRequest extends StringRequest {
    private Map<String,String> headers;//请求头
    private Map<String,String> params;//参数列表
    private Priority priority;//优先级

    public NetNewsRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        init();
    }

    public NetNewsRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        init();
    }

    /**
     * 初始化请求头，优先级
     */
    private void init(){
        this.priority = Priority.NORMAL;
        this.headers = new HashMap<>();
        this.params = new HashMap<>();

    }

    /**
     * 设置优先级
     * @return 当前请求的优先级
     */
    @Override
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * 添加请求头
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers;
    }

    /**
     * post请求通过该方法获得请求参数
     * @return 当前请求的请求参数
     */
    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    /**
     * 设置当前请求的优先级
     * @param priority  优先级
     * @return this
     */
    public NetNewsRequest setPriority(Priority priority){
        this.priority = priority;

        return this;
    }

    /**
     *
     * 添加请求头
     */
    public NetNewsRequest addHeader(String key,String value){
        this.headers.put(key, value);
        return this;
    }

    /**
     *
     * 添加参数
     */
    public NetNewsRequest setParams(String key,String value){
        this.params.put(key, value);
        return this;
    }

    /**
     * 创建请求的便捷类
     */
    public static class Builder{
        private String url;
        private int method;
        private Callback cb;

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }
        public Builder setMethod(int method){
            this.method = method;
            return this;
        }
        public Builder setCallbacl(Callback cb){
            this.cb = cb;
            return this;
        }
        /**
         * 创建一个请求
         * @return volle请求
         */
        public NetNewsRequest buile(){

            NetNewsRequest nnr = new NetNewsRequest(
                    this.method,
                    this.url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            if (cb != null) {
                                cb.onSuccess(s);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            if(cb!=null){
                                cb.onError(volleyError.toString());
                            }
                        }
                    }
            );
            return nnr;
        }
    }
    public void start(){
        VolleyManager.start(this);
    }
}
