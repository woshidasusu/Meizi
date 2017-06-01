package com.dasu.ganhuo.mode.okhttp;

import android.text.TextUtils;

import com.dasu.ganhuo.BuildConfig;
import com.dasu.ganhuo.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dasu on 2017/4/8.
 */
public class VMSController {
    private static final String TAG = VMSController.class.getSimpleName();

    private static VMSApi getVMSApi() {
        return VMSApiSingleton.sInstance;
    }


    public static void queryVersion(final RetrofitListener<VersionResEntity> callback) {
        LogUtils.d(TAG, "[queryVersion] 检查版本更新...");
        String id = BuildConfig.APPID;
        String apiToken = BuildConfig.APITOKEN;
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(apiToken)) {
            LogUtils.e(TAG, "[queryVersion] 检查失败，没有 fir.im 的appId 和 API TOKEN");
            callback.onError("没有 fir.im 的appId 和 API TOKEN");
            return;
        }
        getVMSApi().queryVersion(id, apiToken).enqueue(new Callback<VersionResEntity>() {
            @Override
            public void onResponse(Call<VersionResEntity> call, Response<VersionResEntity> response) {
                if (response.isSuccessful()) {
                    LogUtils.d(TAG, "[queryVersion] 版本信息： " + response.body().toString());
                    callback.onSuccess(response.body());
                }else {
                    LogUtils.e(TAG, "[queryVersion] 检查失败，code: " + response.code());
                    callback.onError("检查失败，code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<VersionResEntity> call, Throwable t) {
                LogUtils.e(TAG, "[queryVersion] 检查失败", t);
                callback.onError(t.getMessage());
            }
        });
    }

    private static class VMSApiSingleton {
        private static VMSApi sInstance = RetrofitHelper.newRetrofit(BuildConfig.VMS).create(VMSApi.class);
    }
}
