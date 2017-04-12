package com.dasu.gank.mode.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class QiyiTvContentProvider extends ContentProvider {
    private static final String TAG = "CacheProvider";
    private QiyiTvDatabaseHelper mQiyiTvDatabaseHelper;

    @Override
    public boolean onCreate() {
        mQiyiTvDatabaseHelper = QiyiTvDatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mQiyiTvDatabaseHelper.getReadableDatabase();
        switch (DatabaseConstValue.sUriMatcher.match(uri)) {
            case DatabaseConstValue.PLAY_RECORD:
                cursor = db.query(DatabaseConstValue.PLAY_RECORD_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case DatabaseConstValue.DSP_DATA:
                cursor = db.query(DatabaseConstValue.DSP_DATA_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri = null;
        SQLiteDatabase db = mQiyiTvDatabaseHelper.getWritableDatabase();
        long rowId = -1;
        switch (DatabaseConstValue.sUriMatcher.match(uri)) {
            case DatabaseConstValue.PLAY_RECORD:
                rowId = db.replace(DatabaseConstValue.PLAY_RECORD_TABLE_NAME, null, values);
                if (rowId > 0) {
                    returnUri = ContentUris.withAppendedId(DatabaseConstValue.CONTENT_URI_PLAY_RECORD, rowId);
                } else {
                    returnUri = ContentUris.withAppendedId(DatabaseConstValue.CONTENT_URI_PLAY_RECORD, -1);
                }
                break;
            case DatabaseConstValue.DSP_DATA:
                rowId = db.replace(DatabaseConstValue.DSP_DATA_TABLE_NAME, null, values);
                if (rowId > 0) {
                    returnUri = ContentUris.withAppendedId(DatabaseConstValue.CONTENT_URI_DSP_DATA, rowId);
                } else {
                    returnUri = ContentUris.withAppendedId(DatabaseConstValue.CONTENT_URI_DSP_DATA, -1);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mQiyiTvDatabaseHelper.getWritableDatabase();
        int count = 0;
        switch (DatabaseConstValue.sUriMatcher.match(uri)) {
            case DatabaseConstValue.PLAY_RECORD:
                count = db.delete(DatabaseConstValue.PLAY_RECORD_TABLE_NAME, selection, selectionArgs);
                break;
            case DatabaseConstValue.DSP_DATA:
                count = db.delete(DatabaseConstValue.DSP_DATA_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mQiyiTvDatabaseHelper.getWritableDatabase();
        int count = 0;
        switch (DatabaseConstValue.sUriMatcher.match(uri)) {
            case DatabaseConstValue.PLAY_RECORD:
                count = db.update(DatabaseConstValue.PLAY_RECORD_TABLE_NAME, values, selection, selectionArgs);
                break;
            case DatabaseConstValue.DSP_DATA:
                count = db.update(DatabaseConstValue.DSP_DATA_TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }
}
