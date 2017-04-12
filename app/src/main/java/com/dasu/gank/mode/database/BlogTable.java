package com.dasu.gank.mode.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dasu on 2017/4/12.
 *
 * 干货文章表，每篇文章对应表中一行数据
 */
public class BlogTable extends BaseDbTable {
    public static final String TABLE_NAME = "blog_table";

    @Override
    String getName() {
        return TABLE_NAME;
    }

    @Override
    void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    private static BlogTable sInstance = new BlogTable();

    private BlogTable() {}

    public synchronized static BlogTable getInstance() {
        return sInstance;
    }
    //列属性
    private static final String CREATED_AT = "created_at";
    private static final String DESC = "description";
    private static final String PUBLISHEDAT = "published_at";
    private static final String IMAGES = "images";
    private static final String TYPE = "type";
    private static final String SOURCE = "source";
    private static final String URL = "url";
    private static final String WHO = "who";
    private static final String USED = "used";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY, "
            + CREATED_AT + " TEXT, "
            + DESC + " TEXT, "
            + PUBLISHEDAT + " TEXT, "
            + IMAGES + " TEXT, "
            + TYPE + " TEXT, "
            + SOURCE + " TEXT, "
            + URL + " TEXT, "
            + WHO + " TEXT, "
            + USED + " TEXT, "
            + ");";

}
