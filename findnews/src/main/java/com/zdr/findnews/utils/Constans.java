package com.zdr.findnews.utils;

import com.google.gson.Gson;

/**
 * 常量类
 * Created by zdr on 16-8-20.
 */
public class Constans {
    public static Gson GSON =new Gson();
    public static String API_KEY = "6b68bca2d6662442fb1103b2427b4fed";
    public static final String SP_FILE = "settings";
    public static final String JUHE_APPKEY = "3e9a26ab2b03f807f84683147baf6ce0";
    public static String APP_ID = "1105643730";

    public static class FragmentType{
        public static final int RECOMMEND = 1;
        public static final int TOPNEWS = 2;
    }

    public static class Database{
        public static final String DB_NAME = "findnews.db";
        public static final int DB_VERSION = 1;
        public static final String KEEP_TYPE = "keep";
    }
}
