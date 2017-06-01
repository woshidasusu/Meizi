package com.dasu.ganhuo.mode.okhttp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by dasu on 2016/8/25.
 *
 * gank.io 的 api
 * 干货包括：github开源项目、技术文章、视频、图片等
 */
interface GankApi {
    /**
     * 获取发过干货的日期接口
     */
    @Headers("Cache-Control: no-cache, no-store")
    @GET("day/history")
    Call<GankResEntity> getPublishDate();

    /**
     * 获取指定类型数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("data/{type}/{count}/{page}")
    Call<GankResEntity> getSpecifyGanHuo(@Path("type") String type, @Path("count") int count, @Path("page") int page);

    /**
     * 获取某一天的干货数据
     */
    @GET("day/{y}/{M}/{d}")
    Call<GankResEntity> getSomedayGanHuo(@Path("y") String year, @Path("M") String month, @Path("d") String day);

    @Headers("Cache-Control: no-cache, no-store")
    @GET("day/{y}/{M}/{d}")
    Call<GankResEntity> getTodayGanHuo(@Path("y") String year, @Path("M") String month, @Path("d") String day);

    /**
     * 获取某一天的网页数据
     */
    @GET("history/content/day/{y}/{M}/{d}")
    Call<GankResEntity> getSomedayHtmlData(@Path("y") String year, @Path("M") String month, @Path("d") String day);

    /**
     * 获取历史的网页数据
     */
    @GET("history/content/{count}/{page}")
    Call<GankResEntity> getHistoryHtmlData(@Path("count") int count, @Path("page") int page);
}
