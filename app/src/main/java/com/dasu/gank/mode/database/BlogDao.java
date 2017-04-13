package com.dasu.gank.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dasu.gank.mode.entity.BlogEntity;
import com.dasu.gank.mode.logic.log.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dasu on 2017/4/12.
 */
public class BlogDao {
    private static final String TAG = BlogDao.class.getSimpleName();

    private static final String URI = "content://" + DatabaseManager.AUTHORITY + BlogTable.getInstance().getName();

    public static final BlogEntity queryById(final Context context, long id) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
                BlogTable.DESC,
                BlogTable.PUBLISHEDAT,
                BlogTable.TYPE,
                BlogTable.URL,
                BlogTable.IMAGES,
                BlogTable.WHO,
                BlogTable._ID
        };
        String where = BlogTable._ID + " = '" + id + "'";
        Cursor c = context.getContentResolver().query(uri, projection, where, null, null);
        BlogEntity result = null;
        try {
            if (c.moveToFirst()) {
                result = new BlogEntity();
                result.setDesc(c.getString(0));
                result.setPublishedAt(c.getString(1));
                result.setType(c.getString(2));
                result.setUrl(c.getString(3));
                result.setImages(Arrays.asList(c.getString(4).split(",")));
                result.setWho(c.getString(5));
                result.set_id(c.getLong(6));
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        return result;
    }

    public static final List<BlogEntity> queryAll(final Context context) {
        Uri uri = Uri.parse(URI);
        String[] projection = new String[]{
                BlogTable.DESC,
                BlogTable.PUBLISHEDAT,
                BlogTable.TYPE,
                BlogTable.URL,
                BlogTable.IMAGES,
                BlogTable.WHO,
                BlogTable._ID

        };
        Cursor c = context.getContentResolver().query(uri, projection,
                null, null, BlogTable.PUBLISHEDAT + " DESC");
        List<BlogEntity> result = new ArrayList<>();
        try {
            if (c.moveToFirst()) {
                LogUtil.d(TAG, "BlogDao-->queryAll(): " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    c.moveToPosition(i);
                    BlogEntity blog = new BlogEntity();
                    blog.setDesc(c.getString(0));
                    blog.setPublishedAt(c.getString(1));
                    blog.setType(c.getString(2));
                    blog.setUrl(c.getString(3));
                    blog.setImages(Arrays.asList(c.getString(4).split(",")));
                    blog.setWho(c.getString(5));
                    blog.set_id(c.getLong(6));
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

    public static final Uri insert(Context context, BlogEntity blog) {
        Uri uri = Uri.parse(URI);
        //插入前先把旧数据删掉，如果有的话
        if (queryById(context, blog.get_id()) != null) {
            String where = BlogTable._ID + " = " + blog.get_id();
            context.getContentResolver().delete(uri, where, null);
        }
        ContentValues values = new ContentValues();
        values.put(BlogTable._ID, blog.get_id());
        values.put(BlogTable.CREATED_AT, blog.getCreatedAt());
        values.put(BlogTable.DESC, blog.getDesc());
        values.put(BlogTable.PUBLISHEDAT, blog.getPublishedAt());
        values.put(BlogTable.SOURCE, blog.getSource());
        values.put(BlogTable.IMAGES, Arrays.toString(blog.getImages().toArray()));
        values.put(BlogTable.TYPE, blog.getType());
        values.put(BlogTable.URL, blog.getUrl());
        values.put(BlogTable.USED, String.valueOf(blog.isUsed()));
        values.put(BlogTable.WHO, blog.getWho());

        Uri returnUri = context.getContentResolver().insert(uri, values);
        return returnUri;
    }

    public static final int deleteAll(Context context) {
        Uri uri = Uri.parse(URI);
        int result = context.getContentResolver().delete(uri, null, null);
        return result;
    }

}
