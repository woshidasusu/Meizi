package com.dasu.ganhuo.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dasu.ganhuo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/12.
 */
public class PublishDateDao {
    private static final String TAG = PublishDateDao.class.getSimpleName();

    private static final String URI = "content://" + DatabaseManager.AUTHORITY + PublishDateTable.getInstance().getName();


    public static final List<String> queryAll(final Context context) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
            PublishDateTable.DATE
        };
        Cursor c = context.getContentResolver().query(uri, projection,
                null, null, PublishDateTable.DATE + " DESC");
        List<String> result = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                LogUtils.d(TAG, "PublishDateDao-->queryAll(): " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    c.moveToPosition(i);
                    result.add(c.getString(0));
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

    public static final Uri insert(Context context, String date) {
        Uri uri = Uri.parse(URI);
        ContentValues values = new ContentValues();
        values.put(PublishDateTable.DATE, date);

        Uri returnUri = context.getContentResolver().insert(uri, values);
        return returnUri;
    }

    public static final int deleteAll(Context context) {
        Uri uri = Uri.parse(URI);
        int result = context.getContentResolver().delete(uri, null, null);
        return result;
    }

}
