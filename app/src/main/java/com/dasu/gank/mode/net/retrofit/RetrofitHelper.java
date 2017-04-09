package com.dasu.gank.mode.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dasu on 2017/4/8.
 */
class RetrofitHelper {
    private static final String TAG = RetrofitHelper.class.getSimpleName();

    private static OkHttpClient mOkHttpClient;
    private static Gson mGson;

    private static void init() {
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .serializeNulls()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(12, TimeUnit.SECONDS)
                .build();
    }

    static Retrofit newRetrofit(String baseUrl) {
        if (mOkHttpClient == null || mGson == null) {
            init();
        }
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }




}
