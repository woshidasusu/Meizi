package com.dasu.gank.mode.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chinanetcenter.wscommontv.model.log.LogUtils;

class QiyiTvDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "QiyiTvDatabaseHelper";
    private static QiyiTvDatabaseHelper sInstance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "qiyitv.db";
    private static Context mContext;

    private QiyiTvDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static QiyiTvDatabaseHelper getInstance(Context context) {
        mContext = context.getApplicationContext();
        if (sInstance == null) {
            sInstance = new QiyiTvDatabaseHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_PLAY_RECORD_TABLE = "CREATE TABLE "
                + DatabaseConstValue.PLAY_RECORD_TABLE_NAME
                + " ("
                + DatabaseConstValue.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseConstValue.WSID + " TEXT,"
                + DatabaseConstValue.VIDEO_ID + " INTEGER,"
                + DatabaseConstValue.NAME + " TEXT,"
                + DatabaseConstValue.COVER + " TEXT,"
                + DatabaseConstValue.TYPE + " INTEGER,"
                + DatabaseConstValue.DRAMA_INDEX + " TEXT,"
                + DatabaseConstValue.SERIES_ID + " INTEGER,"
                + DatabaseConstValue.PLAYED_SERIES + " INTEGER,"
                + DatabaseConstValue.PLAYED_TIME + " INTEGER,"
                + DatabaseConstValue.INSERT_TIME + " INTEGER,"
                + DatabaseConstValue.VIDEO_RELEASE_TIME + " INTEGER"
                + ")";
        db.execSQL(CREATE_PLAY_RECORD_TABLE);
        final String CREATE_DSP_DATA_RECORD_TABLE = "CREATE TABLE "
                + DatabaseConstValue.DSP_DATA_TABLE_NAME
                + " ("
                + DatabaseConstValue.DSP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseConstValue.DSP_TYPE + " TEXT,"
                + DatabaseConstValue.INSERT_TIME + " INTEGER,"
                + DatabaseConstValue.DSP_RECORD + " TEXT"
                + ")";

        db.execSQL(CREATE_DSP_DATA_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "oldVersion = " + oldVersion + ";newVersion = " + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
