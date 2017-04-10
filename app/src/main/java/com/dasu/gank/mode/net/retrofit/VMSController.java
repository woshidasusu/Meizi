package com.dasu.gank.mode.net.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.dasu.gank.BuildConfig;
import com.dasu.gank.mode.entity.VersionResEntity;

import retrofit2.Callback;

/**
 * Created by dasu on 2017/4/8.
 */
public class VMSController {
    private static final String TAG = VMSController.class.getSimpleName();

    private static VMSApi getVMSApi() {
        return VMSApiSingleton.sInstance;
    }


    public static void queryVersion(Callback<VersionResEntity> callback) {
        String id = BuildConfig.APPID;
        String apiToken = BuildConfig.APITOKEN;
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(apiToken)) {
            Log.e(TAG, "没有 fir.im 的appId 和 API TOKEN");
            callback.onFailure(null, new Throwable("没有 fir.im 的appId 和 API TOKEN"));
            return;
        }
        getVMSApi().queryVersion(id, apiToken).enqueue(callback);
    }

    private static class VMSApiSingleton {
        private static VMSApi sInstance = RetrofitHelper.newRetrofit(BuildConfig.VMS).create(VMSApi.class);
    }

}
