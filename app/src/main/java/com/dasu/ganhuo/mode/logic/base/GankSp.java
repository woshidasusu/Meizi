package com.dasu.ganhuo.mode.logic.base;

import android.content.Context;

import com.dasu.ganhuo.utils.SPUtils;

/**
 * Created by dasu on 2017/4/27.
 */

public class GankSp {
    /**
     * gank.io 所有发布过干货的天数总计
     */
    public static final String GANK_DATE_COUNTS = "gank_date_counts";
    private static String spName = "gank";
    private static SPUtils gankSpUtils;

    private static SPUtils getSpUtils(Context context) {
        if (gankSpUtils == null) {
            gankSpUtils = new SPUtils(context, spName);
        }
        return gankSpUtils;
    }

    public static void putGankDateCounts(Context context, int counts) {
        getSpUtils(context).putInt(GANK_DATE_COUNTS, counts);
    }

    public static int getGankDateCounts(Context context) {
        return getSpUtils(context).getInt(GANK_DATE_COUNTS, 100);
    }
}
