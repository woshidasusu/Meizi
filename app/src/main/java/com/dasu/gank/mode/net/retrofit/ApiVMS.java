package com.dasu.gank.mode.net.retrofit;

import com.dasu.gank.mode.entity.VersionResEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dasu on 2017/4/8.
 *
 * fir.im 的 api，VMS指版本管理服务器
 */
interface ApiVMS {

    @GET("apps/latest/{id}")
    Call<VersionResEntity> queryVersion(@Path("id") String id, @Query("api_token") String apiToken);


}
