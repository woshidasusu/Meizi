package coder.dasu.meizi.mode.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dasu on 2016/9/13.
 * https://github.com/woshidasusu/Meizi
 */
public class GankRetrofit {

    final GankApi mGankApi;

    final Gson mGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .serializeNulls()
            .create();

    public GankRetrofit(){
        //init okHttpClient
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(12, TimeUnit.SECONDS)
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
