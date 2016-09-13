package coder.dasu.meizi.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sxq on 2016/9/13.
 */
public class GankRetrofit {

    final GankApi mGankApi;

    final Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    public GankRetrofit(){
        //init okHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.gankApiUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        mGankApi = retrofit.create(GankApi.class);
    }

    public static GankApi getGankService(){
        return GankApiSingleton.mInstance;
    }

    static class GankApiSingleton{
        private static GankApi mInstance = new GankRetrofit().mGankApi;
    }

}
