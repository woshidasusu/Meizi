package com.dasu.gank.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.chinanetcenter.wscommontv.model.log.LogUtils;
import com.chinanetcenter.wscommontv.model.utils.DateUtils;
import com.chinanetcenter.wstv.WsTVSdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangwy on 2016/9/27.
 */
public class PlayRecordHelper {
    private static final String TAG = "PlayRecordHelper";
    private static final Uri sUri = DatabaseConstValue.CONTENT_URI_PLAY_RECORD;

    public static final PlayRecord queryById(Context context, long videoId) {
        PlayRecord result = null;
        String[] projection = new String[]{
                DatabaseConstValue.ID,
                DatabaseConstValue.WSID,
                DatabaseConstValue.VIDEO_ID,
                DatabaseConstValue.NAME,
                DatabaseConstValue.COVER,
                DatabaseConstValue.TYPE,
                DatabaseConstValue.DRAMA_INDEX,
                DatabaseConstValue.SERIES_ID,
                DatabaseConstValue.PLAYED_SERIES,
                DatabaseConstValue.PLAYED_TIME,
                DatabaseConstValue.VIDEO_RELEASE_TIME
        };
        Cursor c = context.getContentResolver().query(sUri, projection, DatabaseConstValue.WSID +
                "= '" + WsTVSdk.getInstance().getWsTVAccountInfo(context).getUid() + "' AND " +
                DatabaseConstValue.VIDEO_ID + "= '" + videoId + "'", null, DatabaseConstValue
                .INSERT_TIME + " DESC");
        try {
            if (c.moveToFirst()) {
                result = new PlayRecord();
                result.setVideo_id(c.getLong(2));
                result.setName(c.getString(3));
                result.setCover(c.getString(4));
                result.setType(c.getInt(5));
                result.setDrama_index(c.getString(6));
                result.setSeries_id(c.getLong(7));
                result.setPlayed_series(c.getInt(8));
                result.setPlayed_time(c.getInt(9));
                result.setVideo_release_time(c.getLong(10));
            }
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        return result;
    }

    public static final List<PlayRecord> queryAllOrderByTime(final Context context) {
        String[] projection = new String[]{
                DatabaseConstValue.ID,
                DatabaseConstValue.WSID,
                DatabaseConstValue.VIDEO_ID,
                DatabaseConstValue.NAME,
                DatabaseConstValue.COVER,
                DatabaseConstValue.TYPE,
                DatabaseConstValue.DRAMA_INDEX,
                DatabaseConstValue.SERIES_ID,
                DatabaseConstValue.PLAYED_SERIES,
                DatabaseConstValue.PLAYED_TIME,
                DatabaseConstValue.VIDEO_RELEASE_TIME
        };
        Cursor c = context.getContentResolver().query(sUri, projection, DatabaseConstValue.WSID +
                "= '" + WsTVSdk.getInstance().getWsTVAccountInfo(context).getUid() + "'", null,
                DatabaseConstValue.INSERT_TIME + " DESC");
        ArrayList<PlayRecord> result = new ArrayList<PlayRecord>();
        try {
            if (c.moveToFirst()) {
                LogUtils.d(TAG, "moveToFirst = " + c.getCount());
                for (int i = 0; i < c.getCount() && i < 30; i++) {
                    c.moveToPosition(i);
                    PlayRecord temp = new PlayRecord();
                    temp.setVideo_id(c.getLong(2));
                    temp.setName(c.getString(3));
                    temp.setCover(c.getString(4));
                    temp.setType(c.getInt(5));
                    temp.setDrama_index(c.getString(6));
                    temp.setSeries_id(c.getLong(7));
                    temp.setPlayed_series(c.getInt(8));
                    temp.setPlayed_time(c.getInt(9));
                    temp.setVideo_release_time(c.getLong(10));
                    result.add(temp);
                }
                if (c.getCount() > 30) {
                    StringBuffer ids = new StringBuffer("(");
                    for (int i = 30; i < c.getCount(); i++) {
                        c.moveToPosition(i);
                        ids.append(c.getInt(0)).append(",");
                    }
                    ids.deleteCharAt(ids.length() - 1).append(")");
                    context.getContentResolver().delete(sUri, DatabaseConstValue.ID + " in " +
                            ids, null);
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

    public static final Uri insert(Context context, PlayRecord playRecord) {
        LogUtils.d(TAG, "insert = " + playRecord);
        if (queryById(context, playRecord.getVideo_id()) != null) {
            context.getContentResolver().delete(sUri, DatabaseConstValue.VIDEO_ID + "=" +
                    playRecord.getVideo_id(), null);
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseConstValue.VIDEO_ID, playRecord.getVideo_id());
        values.put(DatabaseConstValue.WSID, WsTVSdk.getInstance().getWsTVAccountInfo(context)
                .getUid());
        values.put(DatabaseConstValue.NAME, playRecord.getName());
        values.put(DatabaseConstValue.COVER, playRecord.getCover());
        values.put(DatabaseConstValue.TYPE, playRecord.getType());
        values.put(DatabaseConstValue.DRAMA_INDEX, playRecord.getDrama_index());
        values.put(DatabaseConstValue.SERIES_ID, playRecord.getSeries_id());
        values.put(DatabaseConstValue.VIDEO_RELEASE_TIME, playRecord.getVideo_release_time());
        values.put(DatabaseConstValue.PLAYED_SERIES, playRecord.getPlayed_series());
        values.put(DatabaseConstValue.PLAYED_TIME, playRecord.getPlayed_time());
        values.put(DatabaseConstValue.INSERT_TIME, DateUtils.getAdjustCurrentTime(context));

        Uri uri = context.getContentResolver().insert(sUri, values);
        LogUtils.d(TAG, "uri = " + uri);
        return uri;
    }
    public static final int deleteAll(Context context) {
        int result = context.getContentResolver().delete(sUri, DatabaseConstValue.WSID +
                "= '" + WsTVSdk.getInstance().getWsTVAccountInfo(context).getUid() + "'", null);
        LogUtils.d(TAG,"delete result="+result);
        return result;
    }

}
