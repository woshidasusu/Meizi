//package coder.dasu.meizi.mode.net.update;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.chinanetcenter.cloudgame.Config;
//import com.chinanetcenter.cloudgame.model.log.LogUtils;
//import com.chinanetcenter.cloudgame.model.utils.AppUtils;
//import com.chinanetcenter.cloudgame.model.utils.FileUtils;
//import com.chinanetcenter.cloudgame.model.utils.ImageFileDownloadUtils;
//import com.chinanetcenter.cloudgame.model.vms.VersionInfoResEntity;
//import com.chinanetcenter.cloudgame.model.vms.VmsController;
//import com.chinanetcenter.cloudgame.model.volley.VolleyListener;
//
//import java.io.File;
//
///**
// * Created by zhengyx on 2016/12/7.
// */
//
//public class UpdateController {
//    private static final String TAG = "UpdateController";
//    /**
//     * 启动页
//     **/
//    public static final String HOME_PAGE_IMAGE = "home_page_image";
//    public static final String HOME_PAGE_TEMP = "home_page_temp";
//
//    /**
//     * homeActivity背景图
//     **/
//    public static final String HOME_BACKGROUND_IMAGE = "home_background_image";
//    public static final String HOME_BACKGROUND_TEMP = "home_background_temp";
//
//    /**
//     * 标示是否处于全屏播放
//     */
//    private static boolean mIsOnFullScreen;
//
//    public static boolean getIsOnFullScreen() {
//        return mIsOnFullScreen;
//    }
//
//    public static void setIsOnFullScreen(boolean isOnFullScreen) {
//        mIsOnFullScreen = isOnFullScreen;
//    }
//
//    /**
//     * 个人中心检测更新调用时要通过监听回调展示相应的toast
//     *
//     * @param context
//     * @param OnCheckUpdateListener
//     */
//    public static void checkUpdate(final Context context, final OnCheckUpdateListener
//            OnCheckUpdateListener) {
//        checkApkFile(context);
//        VmsController.checkVersion(context, context, new VolleyListener<VersionInfoResEntity>() {
//            @Override
//            public void onSuccess(VersionInfoResEntity data) {
//                if (data != null && data.getResult().equals("1")) {
//                    doUpdate(context, data, OnCheckUpdateListener.getUpdateDialog(data), OnCheckUpdateListener);
//                } else {
//                    OnCheckUpdateListener.noUpdateVersion();
//                }
//            }
//
//            @Override
//            public void onError(int code, Exception description) {
//                LogUtils.e(TAG, "onError=" + code + " des=" + description);
//                OnCheckUpdateListener.requestError(code, description);
//            }
//        });
//    }
//
//    private static void doUpdate(Context context, final VersionInfoResEntity data, Dialog
//            updateDialog, OnCheckUpdateListener OnCheckUpdateListener) {
//        if (data.getUpdateType().equals("1")) {
//            updateDialog.show();
//        } else if (data.getUpdateType().equals("0")) {
//            if (UpdateHelper.isApkExist(context, data.getVersionCode())) {
//                updateDialog.show();
//            } else {
//                OnCheckUpdateListener.downloadingUpdateVersion();
//                UpdateHelper.downloadApkFile(context, data, updateDialog);
//            }
//        }
//    }
//
//    //启动页和首页背景下载相关
//
//    public static void downloadImage(Context context, String url, String imageName) {
//        if (TextUtils.isEmpty(url)) {
//            return;
//        }
//        String tempName = null;
//        if (imageName.equals(HOME_PAGE_IMAGE)) {
//            tempName = HOME_PAGE_TEMP;
//        } else if (imageName.equals(HOME_BACKGROUND_IMAGE)) {
//            tempName = HOME_BACKGROUND_TEMP;
//        }
//        ImageFileDownloadUtils.download(context, url, imageName, tempName);
//    }
//
//    /**
//     * 判断图片是否下载完成，没有的话直接去下载
//     *
//     * @param url
//     * @param imageName
//     */
//    public static void checkImage(Context context, String url, String imageName) {
//        if (FileUtils.isFileExists(context, imageName)) {
//            downloadImage(context, url, imageName);
//        }
//    }
//
//    /**
//     * 检查是否有已经安装过的APK升级包，有的话就删除
//     */
//    private static void checkApkFile(final Context context) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (UpdateSp.getUpdateVersion(context).equals(AppUtils.getVersionCode(context))) {
//                    int currentVersionCode = Integer.valueOf(AppUtils.getVersionCode(context));
//                    while (currentVersionCode != 1) {
//                        File file = new File(Config.getAppDir(context), "" + currentVersionCode);
//                        if (file.exists()) {
//                            file.delete();
//                        }
//                        currentVersionCode--;
//                    }
//                }
//            }
//        }).start();
//    }
//}
