package com.zdr.findnews.dbUtils;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.zdr.findnews.entity.NewsType;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据库存储，存储新闻类型
 * Created by zdr on 16-8-22.
 */
public class TypeDao {
    private Dao<NewsType, Integer> dao;
    private static TypeDao typeDao = null;

    private TypeDao(Context context) {
        try {
            dao = DatabaseHelper.getHelper(context).getDao(NewsType.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TypeDao getTypeDao(Context context) {
        if (typeDao == null) {
            typeDao = new TypeDao(context);
        }
        return typeDao;
    }

    public int addTypeAll(List<NewsType> list) {
        try {
            return dao.create(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<NewsType> findCheckedType() {
        try {
            return dao.queryForEq("isChecked", 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NewsType> findUnCheckedType() {
        try {
            return dao.queryForEq("isChecked", 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void upCheckedByName(String name, int checked) {
        UpdateBuilder<NewsType, Integer> builder
                = dao.updateBuilder();
        try {
            builder.where().eq("typeName", name);
            builder.updateColumnValue("isChecked", checked);
            builder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
