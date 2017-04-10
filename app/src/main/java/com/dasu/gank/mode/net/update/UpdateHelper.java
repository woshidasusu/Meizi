package com.dasu.gank.mode.net.update;

import android.content.Context;
import android.text.TextUtils;

import com.dasu.gank.mode.entity.VersionResEntity;
import com.dasu.gank.utils.FileUtils;

import java.io.File;

/**
 * Created by dasu on 2017/4/8.
 *
 */

class UpdateHelper {
    private static final String TAG  = UpdateHelper.class.getSimpleName();

    private OnCheckUpdateListener mUpdateListener;


    /**
     * 下载前先检查本地是否有已经下载好的apk
     *
     * @param context
     * @param versionInfo
     * @param listener
     */
    static void downloadApk(Context context, VersionResEntity versionInfo, OnCheckUpdateListener listener) {
        boolean hasApk = checkLocalApk(context, versionInfo, listener);
        if (hasApk) {
            return;
        }
        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(context, versionInfo, listener);
        updateAsyncTask.execute();
    }

    /**
     * 检查本地是否有已经下载好的 apk，并删除无效 apk，只保留最新版本的 apk
     *
     * @param context
     * @param versionInfo
     * @param listener
     * @return true 本地有最新版本的 apk
     */
    private static boolean checkLocalApk(Context context, VersionResEntity versionInfo, OnCheckUpdateListener listener) {
        if (TextUtils.isEmpty(FileUtils.getAppDownloadDirectory())) {
            return false;
        }
        File apkDir = new File(FileUtils.getAppDownloadDirectory());
        for (File f : apkDir.listFiles()) {
            if (!f.isDirectory() && f.getName().endsWith(".apk")) {
                String v = f.getName().substring(0, f.getName().lastIndexOf('.'));
                if (v.equals(versionInfo.getVersion())) {
                    listener.onDownloadFinish(true, f.getAbsolutePath());
                    return true;
                } else {
                    f.delete();
                }
            }
        }
        return false;
    }





}
