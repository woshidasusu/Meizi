package com.dasu.gank.mode.net.retrofit;

import com.dasu.gank.BuildConfig;
import com.dasu.gank.mode.entity.GankDataResponse;
import com.dasu.gank.mode.entity.GankDayResponse;
import com.dasu.gank.mode.entity.GankHistoryResponse;

import retrofit2.Callback;


/**
 * Created by dasu on 2017/4/8.
 */
public class GankController {
    private static final String TAG = GankController.class.getSimpleName();

    private static GankApi getGankApi() {
        return GankApiSingleton.mInstance;
    }

    public static void getData(String type, int count, int page, Callback<GankDataResponse> callback) {
        getGankApi().getData(type, count, page).enqueue(callback);
    }

    public static void getGankDay(Callback<GankHistoryResponse> callback) {
        getGankApi().getGankDay().enqueue(callback);
    }

    public static void getDayGankData(String year, String month, String day, Callback<GankDayResponse> callback) {
        getGankApi().getDayGankData(year, month, day).enqueue(callback);
    }

    private static class GankApiSingleton{
        private static GankApi mInstance = RetrofitHelper.newRetrofit(BuildConfig.GAND_SERVICE).create(GankApi.class);
    }

}
