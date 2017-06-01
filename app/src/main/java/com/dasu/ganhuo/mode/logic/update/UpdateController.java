package com.dasu.ganhuo.mode.logic.update;

import android.content.Context;

import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.mode.okhttp.VMSController;
import com.dasu.ganhuo.mode.okhttp.VersionResEntity;
import com.dasu.ganhuo.utils.AppUtils;
import com.dasu.ganhuo.utils.LogUtils;


/**
 * Created by dasu on 2017/4/8.
 *
 * 控制Update流程，外部与Update模块交互只需知道该类即可
 * Update流程:
 *      1. 向服务器（fir.im)发起查询版本信息请求
 *      2.1 如果是最新版本，则通知无需更新
 *      2.2 如果需要更新，那么客户端判断更新类型是强制还是可选择更新（这项工作应该由服务端完成，由于使用的是第三方服务器托管apk，所以只能由客户端完成）
 *      2.2.1 强制更新
 *      2.2.2 可选择更新
 */
public class UpdateController {
    private static final String TAG = UpdateController.class.getSimpleName();

    public static void checkUpdate(final Context context, final OnCheckUpdateListener listener) {
        LogUtils.d(TAG, "=====发起版本更新检查请求");
        VMSController.queryVersion(new RetrofitListener<VersionResEntity>() {
            @Override
            public void onSuccess(VersionResEntity data) {
                VersionResEntity version = data;
                if (version != null) {
                    String newVersionCode = version.getVersion();
                    if (!newVersionCode.equals(AppUtils.getAppVersionCode(context))) {
                        LogUtils.d(TAG, "有新版，需要更新");
                        //需要更新，小版本更新时可选择，大版本更新时强制更新
                        //versionShort : 1.2  ===>  newVer : 1
                        int newVer = Integer.parseInt(version.getVersionShort().substring(0,1));
                        //versionName : 1.0  ===> curVer : 1
                        int curVer = Integer.parseInt(AppUtils.getAppVersionName(context).substring(0,1));
                        if (newVer > curVer) {
                            //强制更新
                            listener.onPreUpdate(true, version);
                        } else {
                            //选择更新
                            listener.onPreUpdate(false, version);
                        }
                    } else {
                        LogUtils.d(TAG, "当前已是最新版本，无需更新");
                    }
                }
            }

            @Override
            public void onError(String description) {

            }
        });
    }

    public static void downloadApk(Context context, VersionResEntity version, OnCheckUpdateListener listener) {
        UpdateHelper.downloadApk(context, version, listener);
    }
}
