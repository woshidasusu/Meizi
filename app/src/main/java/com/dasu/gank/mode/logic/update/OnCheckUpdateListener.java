package com.dasu.gank.mode.logic.update;

import com.dasu.gank.mode.entity.VersionResEntity;

/**
 * Created by dasu on 2017/4/7.
 */

public interface OnCheckUpdateListener {

    /**
     * apk下载的进度
     *
     * @param progress 取值范围 0--100
     */
    void onDownloading(int progress);

    /**
     * apk是否成功下载
     *
     * @param isSucceed ture: 下载成功
     */
    void onDownloadFinish(boolean isSucceed, String apkPath);

    /**
     * 准备升级，在这阶段做点升级前的准备，比如初始化弹窗等
     *
     * @param force 是否是强制性升级
     * @param versionInfo 新版本信息
     */
    void onPreUpdate(boolean force, VersionResEntity versionInfo);
}
