package com.dasu.gank.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dasu.gank.mode.entity.PublishDateEntity;
import com.dasu.gank.mode.logic.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/12.
 */
public class PublishDateDao {
    private static final String TAG = PublishDateDao.class.getSimpleName();

    private static final String URI = "content://" + DatabaseManager.AUTHORITY + PublishDateTable.getInstance().getName();


    public static final List<PublishDateEntity> queryAll(final Context context) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
            PublishDateTable.DATE
        };
        Cursor c = context.getContentResolver().query(uri, projection,
                null, null, PublishDateTable.DATE + " DESC");
        List<PublishDateEntity> result = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                LogUtil.d(TAG, "PublishDateDao-->queryAll(): " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    c.moveToPosition(i);
                    PublishDateEntity date = new PublishDateEntity();
                    date.setDate(c.getString(0));
                    result.add(date);
                }
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        return result;
    }

    public static final Uri insert(Context context, PublishDateEntity date) {
        Uri uri = Uri.parse(URI);
        ContentValues values = new ContentValues();
        values.put(PublishDateTable.DATE, date.getDate());

        Uri returnUri = context.getContentResolver().insert(uri, values);
        return returnUri;
    }

    public static final int deleteAll(Context context) {
        Uri uri = Uri.parse(URI);
        int result = context.getContentResolver().delete(uri, null, null);
        return result;
    }

}
