package com.zdr.findnews.dbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zdr.findnews.entity.News;
import com.zdr.findnews.entity.NewsType;
import com.zdr.findnews.utils.Constans;

import java.sql.SQLException;

/**
 * 数据库操作对象
 * Created by zdr on 16-8-22.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static DatabaseHelper helper = null;

    private DatabaseHelper(Context context) {
        super(context, Constans.Database.DB_NAME, null, Constans.Database.DB_VERSION);
    }

    /**
     * 生成数据库连接的单例模式
     *
     * @param context 上下文
     * @return DatabaseHelper
     */
    public static DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, NewsType.class);
            TableUtils.createTableIfNotExists(connectionSource, News.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
