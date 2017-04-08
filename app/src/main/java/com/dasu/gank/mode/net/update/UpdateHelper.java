//package coder.dasu.meizi.mode.net.update;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.chinanetcenter.cloudgame.Config;
//import com.chinanetcenter.cloudgame.model.utils.AppUtils;
//import com.chinanetcenter.cloudgame.model.utils.StorageUtils;
//import com.chinanetcenter.cloudgame.model.vms.VersionInfoResEntity;
//import com.chinanetcenter.cloudgame.model.vms.VmsController;
//
//import java.io.File;
//
///**
// * Created by zhengyx on 2016/12/20.
// */
//
//public class UpdateHelper {
//    private static final String TAG = "UpdateHelper";
//
//    static final int HANDLER_PARAMS_COMMON_DOWNLOAD_SUCCESS = 1;
//    static final int HANDLER_PARAMS_FORCE_DOWNLOAD_SUCCESS = 2;
//    static final int HANDLER_PARAMS_DOWNLOAD_FAIL = 3;
//
//    private static class MyHandler extends Handler {
//        Context mContext;
//        Dialog mUpdateDialog;
//        VersionInfoResEntity mVersionInfoResEntity;
//
//        public MyHandler(Context context, Dialog updateDialog, VersionInfoResEntity
//                versionInfoResEntity) {
//            mContext = context;
//            mUpdateDialog = updateDialog;
//            mVersionInfoResEntity = versionInfoResEntity;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case HANDLER_PARAMS_COMMON_DOWNLOAD_SUCCESS://弹出普通提示升级框
//                    VersionInfoResEntity commonDownloadData = (VersionInfoResEntity) msg.obj;
//                    mUpdateDialog.show();
//                    break;
//                case HANDLER_PARAMS_FORCE_DOWNLOAD_SUCCESS://弹出安装框
//                    VersionInfoResEntity forceDownloadData = (VersionInfoResEntity) msg.obj;
//                    installApk(mContext, forceDownloadData.getVersionCode());
//                    mUpdateDialog.dismiss();
//                    break;
//                case HANDLER_PARAMS_DOWNLOAD_FAIL:
//                    mUpdateDialog.dismiss();
//                    // TODO: 2017/2/14 此处不应该有界面相关逻辑
//                    Toast.makeText(mContext,"网络异常", Toast.LENGTH_LONG).show();
//                    uploadDownErrorLog(mContext, (String) msg.obj, mVersionInfoResEntity);
//                    break;
//            }
//        }
//    }
//
//    private static void uploadDownErrorLog(Context context, String errorMsg, VersionInfoResEntity
//            versionInfoResEntity) {
//        StringBuilder stringBuilder = new StringBuilder("失败原因:");
//        stringBuilder.append(errorMsg);
//        stringBuilder.append("\n");
//        stringBuilder.append("当前版本:");
//        stringBuilder.append(AppUtils.getVersionCode(context));
//        stringBuilder.append("目标版本:");
//        stringBuilder.append(versionInfoResEntity.getVersionCode());
//
//        VmsController.singleUploadLog(context, TAG, "升级包下载失败", stringBuilder.toString(),
//                VmsController.UPLOAD_LOG_LEVEL_ERROR, null);
//    }
//
//    public static void downloadApkFile(Context context, final VersionInfoResEntity data,
//                                       Dialog updateDialog) {
//        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(context, data, new MyHandler
//                (context, updateDialog, data));
//        updateAsyncTask.execute(data.getUpdateUrl(), data.getVersionCode());
//    }
//
//    public static void forceDownloadApkFile(Context context, final VersionInfoResEntity data,
//                                            ProgressBar progressUpdatePb, TextView
//                                                    progressUpdateTv, Dialog updateDialog) {
//        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(context, data, new MyHandler
//                (context, updateDialog, data), progressUpdatePb, progressUpdateTv);
//        updateAsyncTask.execute(data.getUpdateUrl(), data.getVersionCode());
//    }
//
//    public static void installApk(Context context, String apkName) {
//        //如果打开系统安装界面，不管安装成功与否都将升级包置为无效，将无效的versionCode存起来
//        UpdateSp.setInvalidVersion(context, apkName);
//        if (StorageUtils.isExternalStorageWritable()) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.fromFile(new File(Config.getAppDir(context)
//                    , apkName)), "application/vnd.android.package-archive");
//            context.startActivity(intent);
//        } else {
//            StorageUtils.getFilesAuthority(context, apkName);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.parse("file://" + context
//                    .getFilesDir().getPath() + "/" + apkName), "application/vnd.android" + "" +
//                    ".package-archive");
//            context.startActivity(intent);
//        }
//    }
//
//    /**
//     * 目标版本是否存在有效安装包
//     *
//     * @param context
//     * @param apkName 以 versionCode 命名
//     * @return
//     */
//    static boolean isApkExist(Context context, String apkName) {
//        File localFile = new File(Config.getAppDir(context), apkName);
//        if (localFile.exists()) {
//            String invalidVersion = UpdateSp.getInvalidVersion(context);
//            if (invalidVersion.equals(apkName)) {
//                localFile.delete();
//                return false;
//            }
//        }
//        return localFile.exists();
//    }
//}
