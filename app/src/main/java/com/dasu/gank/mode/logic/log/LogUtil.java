package com.dasu.gank.mode.logic.log;

import android.util.Log;

/**
 * Created by dasu on 2017/4/10.
 */
public class LogUtil {
    private static final String TAG = LogUtil.class.getSimpleName();

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

}
