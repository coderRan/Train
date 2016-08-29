package com.zdr.findnews.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 新闻类型实体，记录新闻类型，id，url
 * Created by zdr on 16-8-19.
 */
@DatabaseTable(tableName = "news_type")
public class NewsType implements Parcelable{

    public static final Parcelable.Creator<NewsType> CREATOR
            = new Parcelable.Creator<NewsType>(){

        @Override
        public NewsType createFromParcel(Parcel source) {
            return new NewsType(source);
        }

        @Override
        public NewsType[] newArray(int size) {
            return new NewsType[size];
        }
    };

    public static final int CHECKED_TRUE = 1;
    public static final int CHECKED_FALSE = 0;
    @DatabaseField(generatedId  = true)
    private int id = 0;
    @DatabaseField
    private String typeName;
    @DatabaseField
    private String url;
    @DatabaseField
    private int fragmentType;
    @DatabaseField
    private int isChecked = CHECKED_FALSE;

    public NewsType() {
    }

    public NewsType(Parcel source){
        id = source.readInt();
        typeName = source.readString();
        url = source.readString();
        fragmentType = source.readInt();
        isChecked = source.readInt();
    }

    public NewsType(String typeName, String url, int fragmentType, int isChecked) {
        this.typeName = typeName;
        this.url = url;
        this.fragmentType = fragmentType;
        this.isChecked = isChecked;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsType newsType = (NewsType) o;

        if (id != newsType.id) return false;
        if (fragmentType != newsType.fragmentType) return false;
        if (isChecked != newsType.isChecked) return false;
        if (!typeName.equals(newsType.typeName)) return false;
        return url.equals(newsType.url);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + typeName.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + fragmentType;
        result = 31 * result + isChecked;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
