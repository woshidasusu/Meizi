package com.dasu.gank.mode.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dasu on 2017/4/12.
 * <p>
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

    private BlogTable() {
    }

    public synchronized static BlogTable getInstance() {
        return sInstance;
    }

    //列属性
    static final String CREATED_AT = "created_at";
    static final String DESC = "description";
    static final String PUBLISHEDAT = "published_at";
    static final String IMAGES = "images";
    static final String TYPE = "type";
    static final String SOURCE = "source";
    static final String URL = "url";
    static final String WHO = "who";
    static final String USED = "used";

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
