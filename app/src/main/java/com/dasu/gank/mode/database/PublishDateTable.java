package com.dasu.gank.mode.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dasu on 2017/4/12.
 *
 * 发布过干货文章的日期
 */
public class PublishDateTable extends BaseDbTable {
    public static final String TABLE_NAME = "publish_date_table";

    @Override
    String getName() {
        return TABLE_NAME;
    }

    @Override
    void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    private static PublishDateTable sInstance = new PublishDateTable();

    private PublishDateTable() {}

    public synchronized static PublishDateTable getInstance() {
        return sInstance;
    }
    //列属性
    private static final String DATE = "date";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DATE + " TEXT, "
                    + ");";

}
