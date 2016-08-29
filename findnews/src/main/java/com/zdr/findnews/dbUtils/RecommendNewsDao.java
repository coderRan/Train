package com.zdr.findnews.dbUtils;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.utils.Constans;

import java.sql.SQLException;
import java.util.List;

/**
 * 操作新闻的Dao，对应RecommendFragment和News
 * Created by zdr on 16-8-23.
 */
public class RecommendNewsDao {
    private Dao<News, Integer> dao;
    private static RecommendNewsDao newsDao = null;

    private RecommendNewsDao(Context context){
        try {
            dao = DatabaseHelper.getHelper(context).getDao(News.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static RecommendNewsDao getNewsDao(Context context){
        if(newsDao == null){
            newsDao = new RecommendNewsDao(context);
        }
        return newsDao;
    }
    public int addNews(News news){
        try {
            return dao.create(news);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int addAllNews(List<News> list){
        try {
            return dao.create(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<News> findAllNewsByType(String type){
        try {
            return dao.queryForEq("newsTypeName", type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<News> findNewsByType(String type, String url) {
        QueryBuilder<News,Integer> builder = dao.queryBuilder();
        try {
            builder.where().eq("newsTypeName", type).and().eq("url", url);
            return builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deleteByType(String type){
        DeleteBuilder<News, Integer> builder = dao.deleteBuilder();

        try {
            builder.where().eq("newsTypeName", type);
            return builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteByType(String type,String url){
        DeleteBuilder<News, Integer> builder = dao.deleteBuilder();

        try {
            builder.where().eq("newsTypeName", type).and().eq("url",url);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByNews(News news){
        try {
            dao.delete(news);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll(List<News> list){
        try {
            dao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updataKeepDelState(String url,boolean isKeepDel){
        UpdateBuilder<News, Integer> builder
                = dao.updateBuilder();
        try {
            builder.where().eq("newsTypeName", Constans.Database.KEEP_TYPE)
            .and().eq("url",url);
            builder.updateColumnValue("isCheckDel", isKeepDel);
            builder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updataAllKeepDelState(){
        UpdateBuilder<News, Integer> builder
                = dao.updateBuilder();
        try {
            builder.where().eq("newsTypeName", Constans.Database.KEEP_TYPE);
            builder.updateColumnValue("isCheckDel", false);
            builder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
