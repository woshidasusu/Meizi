package com.dasu.ganhuo.mode.okhttp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dasu on 2017/4/8.
 *
 * fir.im 的 api，VMS指版本管理服务器
 */
interface VMSApi {

    @Headers("Cache-Control: no-cache, no-store")
    @GET("apps/latest/{id}")
    Call<VersionResEntity> queryVersion(@Path("id") String id, @Query("api_token") String apiToken);

}
