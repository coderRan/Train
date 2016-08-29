package com.zdr.findnews.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 新闻实体类，存储新闻的属性
 * Created by zdr on 16-8-19.
 */
@DatabaseTable(tableName = "recommendNews")
public class News  implements Serializable {

    /**
     * ctime : 2016-08-20 12:47
     * title : 江西南昌市老城区八一桥附近一栋高楼发生火灾
     * description : 网易社会
     * picUrl : http://s.cimg.163.com/catchpic/6/68/68399F6F3A191CF1C2893B3CA38366ED.jpg.119x83.jpg
     * url : http://news.163.com/16/0820/12/BUTOPSEV000146BE.html#f=slist
     */
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String ctime;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String picUrl;
    @DatabaseField
    private String url;
    @DatabaseField
    private String newsTypeName;

    private int isKeepDel = 0;
    public News() {
    }

    public News(int id, String ctime, String title, String description, String picUrl, String url, String newsTypeName) {
        this.id = id;
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        this.newsTypeName = newsTypeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }



    public News copySelf(){
        return new News(id, ctime, title, description, picUrl, url, newsTypeName);
    }

    public int getIsKeepDel() {
        return isKeepDel;
    }

    public void setIsKeepDel(int isKeepDel) {
        this.isKeepDel = isKeepDel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (id != news.id) return false;
        if (isKeepDel != news.isKeepDel) return false;
        if (!ctime.equals(news.ctime)) return false;
        if (!title.equals(news.title)) return false;
        if (!description.equals(news.description)) return false;
        if (!picUrl.equals(news.picUrl)) return false;
        if (!url.equals(news.url)) return false;
        return newsTypeName.equals(news.newsTypeName);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ctime.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + picUrl.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + newsTypeName.hashCode();
        result = 31 * result + isKeepDel;
        return result;
    }
}