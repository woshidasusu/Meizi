package coder.dasu.meizi.mode.net.update;

import android.content.Context;

import com.chinanetcenter.cloudgame.model.utils.SpUtils;


/**
 * Created by zhengyx on 2016/12/23.
 */

class UpdateSp {
    private static final String UPDATE_VERSION = "update_version";
    private static final String INVALID_VERSION = "invalid_version";
    //如果下载成功了升级包，则把该升级包的versionCode存起来，
    static void setUpdateVersion(Context context, String updateVersion) {
        SpUtils.putString(context, UPDATE_VERSION, updateVersion);
    }

    static String getUpdateVersion(Context context) {
        return SpUtils.getString(context, UPDATE_VERSION, "0");
    }
    //如果打开系统安装界面，不管安装成功与否都将升级包置为无效，将无效的versionCode存起来
    static void setInvalidVersion(Context context, String invalidVersion) {
        SpUtils.putString(context, INVALID_VERSION, invalidVersion);
    }

    static String getInvalidVersion(Context context) {
        return SpUtils.getString(context, INVALID_VERSION, "0");
    }

}
