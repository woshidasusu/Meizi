package com.dasu.gank.mode.database;

import android.content.UriMatcher;

import java.util.LinkedHashMap;

/**
 * Created by suxq on 2017/4/12.
 *
 * 数据库管理类，管理数据库名、版本号、数据库表、URI匹配等信息
 * 每次增删表都必须对该类进行同步维护
 */

class DatabaseManager {

    static final String DB_NAME = "gank.db";

    static final int DB_VERSION = 1;

    static final String AUTHORITY = "com.dasu.gank.authority";

    private DatabaseManager() {}

    /**
     * 当前数据库版本对应的所有数据库表，只有添加到该集合里的表才会被创建
     */
    static LinkedHashMap<String, BaseDbTable> sAllTables = new LinkedHashMap<>();
    static {
        sAllTables.put(BlogTable.getInstance().getName(), BlogTable.getInstance());
        sAllTables.put(PublishDateTable.getInstance().getName(), PublishDateTable.getInstance());
    }

    /**
     * 暴露给ContentProvider的表，只有在这里添加匹配规则的表，才能通过ContentProvider访问
     */
    static final int BLOG_TABLE = 1;
    static final int PUBLISH_DATE_TABLE = 2;

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, BlogTable.getInstance().getName(), BLOG_TABLE);
        sUriMatcher.addURI(AUTHORITY, PublishDateTable.getInstance().getName(), PUBLISH_DATE_TABLE);
    }


}
