package com.zdr.findnews.entity;

import java.util.List;

/**
 * 将json数据转换成object，用于适配json数据的适配类，
 * 适配json中的无用的数据
 * Created by zdr on 16-8-20.
 */
public class NewsJSON {


    /**
     * code : 200
     * msg : success
     * newslist : [{},{}]
     */

    private int code;
    private String msg;
    private List<News> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<News> getNewsList() {
        return newslist;
    }

    public void setNewsList(List<News> newslist) {
        this.newslist = newslist;
    }

    @Override
    public String toString() {
        return "NewsJSON{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", newslist.size()=" + newslist.size() +
                '}';
    }
}
