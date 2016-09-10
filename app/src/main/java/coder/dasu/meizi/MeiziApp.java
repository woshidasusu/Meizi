package coder.dasu.meizi;

import android.app.Application;

import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by dasu on 2016/8/25.
 */
public class MeiziApp extends Application {

    private static final String TAG = "MeiziApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.with(getApplicationContext())
                .setDebugging(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static OkHttpClient getOkHttpClient(){
        return SingleOkClient.mOkHttpClient;
    }

    private static class SingleOkClient{
        private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
    }

}
