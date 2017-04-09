package com.dasu.gank.mode.net.update;

import android.content.Context;

import com.dasu.gank.mode.entity.VersionResEntity;

/**
 * Created by dasu on 2017/4/8.
 */

class UpdateHelper {
    private static final String TAG  = UpdateHelper.class.getSimpleName();

    private OnCheckUpdateListener mUpdateListener;


    static void downloadApk(Context context, VersionResEntity versionInfo, OnCheckUpdateListener listener) {
        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(context, versionInfo, listener);
        updateAsyncTask.execute();
    }





}
