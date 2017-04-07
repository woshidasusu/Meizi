package coder.dasu.meizi.mode.net.update;

/**
 * Created by dasu on 2017/4/7.
 */

public interface onCheckUpdateListener {

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
    void onDownloadFinish(boolean isSucceed);

    /**
     * 当前处于最新版本
     */
    void onLatestVersion();
}
