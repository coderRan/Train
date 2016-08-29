package com.zdr.findnews.entity;

/**
 * 新闻头条
 * Created by zdr on 16-8-21.
 */
public class TopNews {
    /**
     * title : 这可比“把梳子卖给和尚”牛逼多了!
     * url : http://www.3023.com/28/43916607069538311.html
     * img : http://image.zzd.sm.cn/17615768583836184261.jpg
     * author : 魔鬼营销学
     * time : 1463224307
     */

    private String title;
    private String url;
    private String img;
    private String author;
    private int time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
