package com.dasu.gank.mode.database;

import java.util.LinkedHashMap;

/**
 * Created by suxq on 2017/4/12.
 *
 * 数据库管理类，管理数据库名、版本号、数据库表等信息
 */

class DatabaseManager {

    static final String DB_NAME = "gank.db";

    static final int DB_VERSION = 1;

    private DatabaseManager() {
    }

    /**
     * 当前数据库版本对应的所有数据库表，只有添加到该集合里的表才会被创建
     */
    static LinkedHashMap<String, BaseDbTable> sAllTables = new LinkedHashMap<>();
    static {
//        sAllTables.put("xxxxTable", xxxTable.getInstance());
    }

}
