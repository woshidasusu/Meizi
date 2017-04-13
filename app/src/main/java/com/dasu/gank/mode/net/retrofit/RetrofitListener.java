package com.dasu.gank.mode.net.retrofit;

/**
 * Created by suxq on 2017/4/13.
 */

public interface RetrofitListener<T> {

    /**
     * 请求成功时回调
     *
     * @param data 网络请求的数据对象
     */
    void onSuccess(T data);

    /**
     * 请求失败时回调
     * @param description
     */
    void onError(Exception description);

}
