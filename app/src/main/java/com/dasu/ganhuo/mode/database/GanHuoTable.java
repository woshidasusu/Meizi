package com.dasu.ganhuo.mode.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dasu on 2017/4/12.
 * <p>
 * 存储Gank.io上的干货，干货包括github开源项目，技术文章，视频，图片等
 */
public class GanHuoTable extends BaseDbTable {
    public static final String TABLE_NAME = "gan_huo_table";

    @Override
    String getName() {
        return TABLE_NAME;
    }

    @Override
    void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    private static GanHuoTable sInstance = new GanHuoTable();

    private GanHuoTable() {
    }

    public synchronized static GanHuoTable getInstance() {
        return sInstance;
    }

    //列属性
    static final String _ID = "_id";
    static final String CREATED_AT = "created_at";
    static final String DESC = "description";
    static final String PUBLISHEDAT = "published_at";
    static final String IMAGES = "images";
    static final String TYPE = "type";  //区分干货类型
    static final String SOURCE = "source";
    static final String URL = "url";  //干货跳转链接
    static final String WHO = "who";
    static final String USED = "used";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + _ID + " TEXT PRIMARY KEY, "
                    + CREATED_AT + " INTEGER, "
                    + DESC + " TEXT, "
                    + PUBLISHEDAT + " INTEGER, "
                    + IMAGES + " TEXT, "
                    + TYPE + " TEXT, "
                    + SOURCE + " TEXT, "
                    + URL + " TEXT, "
                    + WHO + " TEXT, "
                    + USED + " TEXT, "
                    + ");";

}
