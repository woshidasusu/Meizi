package com.dasu.ganhuo.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2017/4/12.
 */
public class GanHuoDao {
    private static final String TAG = GanHuoDao.class.getSimpleName();

    private static final String URI = "content://" + DatabaseManager.AUTHORITY + GanHuoTable.getInstance().getName();

    public static final GanHuoEntity queryById(final Context context, String id) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
                GanHuoTable.DESC,
                GanHuoTable.PUBLISHEDAT,
                GanHuoTable.TYPE,
                GanHuoTable.URL,
                GanHuoTable.IMAGES,
                GanHuoTable.WHO,
                GanHuoTable._ID
        };
        String where = GanHuoTable._ID + " = '" + id + "'";
        Cursor c = context.getContentResolver().query(uri, projection, where, null, null);
        GanHuoEntity result = null;
        try {
            if (c.moveToFirst()) {
                result = new GanHuoEntity();
                result.setDesc(c.getString(0));
                result.setPublishedAt(new Date(c.getLong(1)));
                result.setType(c.getString(2));
                result.setUrl(c.getString(3));
                result.setImages(Arrays.asList(c.getString(4).split(",")));
                result.setWho(c.getString(5));
                result.set_id(c.getString(6));
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        return result;
    }

    public static final List<GanHuoEntity> queryAll(final Context context) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
                GanHuoTable.DESC,
                GanHuoTable.PUBLISHEDAT,
                GanHuoTable.TYPE,
                GanHuoTable.URL,
                GanHuoTable.IMAGES,
                GanHuoTable.WHO,
                GanHuoTable._ID

        };
        Cursor c = context.getContentResolver().query(uri, projection,
                null, null, GanHuoTable.PUBLISHEDAT + " DESC");
        List<GanHuoEntity> result = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                LogUtils.d(TAG, "GanHuoDao-->queryAll(): " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    c.moveToPosition(i);
                    GanHuoEntity blog = new GanHuoEntity();
                    blog.setDesc(c.getString(0));
                    blog.setPublishedAt(new Date(c.getLong(1)));
                    blog.setType(c.getString(2));
                    blog.setUrl(c.getString(3));
                    blog.setImages(Arrays.asList(c.getString(4).split(",")));
                    blog.setWho(c.getString(5));
                    blog.set_id(c.getString(6));
                    result.add(blog);
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

    public static final Uri insert(Context context, GanHuoEntity blog) {
        Uri uri = Uri.parse(URI);
        //插入前先把旧数据删掉，如果有的话
        if (queryById(context, blog.get_id()) != null) {
            String where = GanHuoTable._ID + " = " + blog.get_id();
            context.getContentResolver().delete(uri, where, null);
        }
        ContentValues values = new ContentValues();
        values.put(GanHuoTable._ID, blog.get_id());
        values.put(GanHuoTable.CREATED_AT, blog.getCreatedAt().getTime());
        values.put(GanHuoTable.DESC, blog.getDesc());
        values.put(GanHuoTable.PUBLISHEDAT, blog.getPublishedAt().getTime());
        values.put(GanHuoTable.SOURCE, blog.getSource());
        values.put(GanHuoTable.IMAGES, Arrays.toString(blog.getImages().toArray()));
        values.put(GanHuoTable.TYPE, blog.getType());
        values.put(GanHuoTable.URL, blog.getUrl());
        values.put(GanHuoTable.USED, String.valueOf(blog.isUsed()));
        values.put(GanHuoTable.WHO, blog.getWho());

        Uri returnUri = context.getContentResolver().insert(uri, values);
        return returnUri;
    }

    public static final int deleteAll(Context context) {
        Uri uri = Uri.parse(URI);
        int result = context.getContentResolver().delete(uri, null, null);
        return result;
    }

}
