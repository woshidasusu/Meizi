package com.dasu.gank.mode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.chinanetcenter.wscommontv.model.log.LogUtils;
import com.chinanetcenter.wscommontv.model.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengyx on 2017/3/15.
 */

public class DspDataHelper {
    private static final String TAG = "DspDataHelper";
    private static final Uri sUri = DatabaseConstValue.CONTENT_URI_DSP_DATA;


    public static final List<DspData> queryAll(final Context context) {
        String[] projection = new String[]{DatabaseConstValue.DSP_ID, DatabaseConstValue
                .DSP_TYPE, DatabaseConstValue.DSP_RECORD,DatabaseConstValue.INSERT_TIME};
        Cursor c = context.getContentResolver().query(sUri, projection, null, null, null);
        ArrayList<DspData> result = new ArrayList<DspData>();
        try {
            if (c.moveToFirst()) {
                LogUtils.d(TAG, "moveToFirst = " + c.getCount());
                for (int i = 0; i < c.getCount(); i++) {
                    c.moveToPosition(i);
                    DspData temp = new DspData();
                    temp.setType(c.getString(1));
                    temp.setDsp_record(c.getString(2));
                    temp.setInsert_time(c.getLong(3));
                    result.add(i, temp);
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

    public static final Uri insert(Context context, DspData dspData) {
        LogUtils.d(TAG, "insert = " + dspData);
        ContentValues values = new ContentValues();
        values.put(DatabaseConstValue.DSP_TYPE, dspData.getType());
        values.put(DatabaseConstValue.DSP_RECORD, dspData.getDsp_record());
        values.put(DatabaseConstValue.INSERT_TIME, DateUtils.getAdjustCurrentTimeByLocal(context));
        Uri uri = context.getContentResolver().insert(sUri, values);
        LogUtils.d(TAG, "uri = " + uri);
        return uri;
    }

    public static final int deleteAll(Context context) {
        int result = context.getContentResolver().delete(sUri, null, null);
        LogUtils.d(TAG, "delete result=" + result);
        return result;
    }

    public static final int deleteByType(Context context, String type) {
        int result = context.getContentResolver().delete(sUri, DatabaseConstValue.DSP_TYPE + "= " +
                "'" + type + "'", null);
        LogUtils.d(TAG, "delete result=" + result);
        return result;
    }
    public static final int deleteExpireData(Context context){
        long currentTime= DateUtils.getAdjustCurrentTimeByLocal(context);
        long expireTime=currentTime-3*24*60*60;
        int result = context.getContentResolver().delete(sUri, DatabaseConstValue.INSERT_TIME + "< " +
                "'" + expireTime + "'", null);
        LogUtils.d(TAG, "deleteExpireData result=" + result);
        return result;
    }
}
