package com.dasu.gank.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by dasu on 2017/4/8.
 *
 * 获取App相关信息的工具类
 */
public class AppUtils {

    /**
     * 获取 app 版本号 versionCode eg: 1
     *
     * @param context
     * @return
     */
    public static String getAppVersionCode(Context context) {
        String packageName = context.getPackageName();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info != null ? String.valueOf(info.versionCode) : null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回 app 版本编号 versionName eg: 1.0
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String packageName = context.getPackageName();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            return info != null ? info.versionName : null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
