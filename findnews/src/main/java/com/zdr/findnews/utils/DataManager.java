package com.zdr.findnews.utils;

import android.content.Context;

import com.zdr.findnews.dbUtils.TypeDao;
import com.zdr.findnews.entity.NewsType;

import java.util.ArrayList;
import java.util.List;

/**
 * 将新闻类型与URL关联，用于管理新闻类型的数量，
 * Created by zdr on 16-8-19.
 */
public class DataManager {


    private static DataManager dm = null;

    private DataManager() {
    }

    public static DataManager getDM(){
        return dm != null ? dm : new DataManager();
    }

    public void initNewsType(Context context) {

        List<NewsType> types = new ArrayList<>();
        types.add(new NewsType(
                "头条",
                "http://apis.baidu.com/3023/news/channel?id=recomm&page=",
                Constans.FragmentType.TOPNEWS,
                NewsType.CHECKED_TRUE));

        types.add(new NewsType(
                "社会",
                "http://apis.baidu.com/txapi/social/social?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_FALSE));
        types.add(new NewsType(
                "体育",
                "http://apis.baidu.com/txapi/tiyu/tiyu?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_FALSE));
        types.add(new NewsType(
                "科技",
                "http://apis.baidu.com/txapi/keji/keji?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "国际",
                "http://apis.baidu.com/txapi/world/world?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "奇闻",
                "http://apis.baidu.com/txapi/qiwen/qiwen?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "娱乐",
                "http://apis.baidu.com/txapi/huabian/newtop?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "apple",
                "http://apis.baidu.com/txapi/apple/apple?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "美女",
                "http://apis.baidu.com/txapi/mvtp/meinv?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));
        types.add(new NewsType(
                "健康",
                "http://apis.baidu.com/txapi/health/health?num=10&page=",
                Constans.FragmentType.RECOMMEND,
                NewsType.CHECKED_TRUE));

        //以下数据来自聚合数据，使用TopNewsFragment

        TypeDao typeDao =TypeDao.getTypeDao(context);
        typeDao.addTypeAll(types);

    }
}
