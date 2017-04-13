package com.dasu.gank.mode.net.retrofit;

import com.dasu.gank.BuildConfig;
import com.dasu.gank.mode.entity.BlogEntity;
import com.dasu.gank.mode.entity.GankDataResponse;
import com.dasu.gank.mode.entity.GankDayResponse;
import com.dasu.gank.mode.entity.GankHistoryResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    public static void getTodayBlogs(RetrofitListener<List<BlogEntity>> listener) {
        Date date = new Date(System.currentTimeMillis());
        int y = Calendar.getInstance().get(Calendar.YEAR);
        int m = Calendar.getInstance().get(Calendar.MONTH);
        int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    }

    private static class GankApiSingleton{
        private static GankApi mInstance = RetrofitHelper.newRetrofit(BuildConfig.GAND_SERVICE).create(GankApi.class);
    }

}
