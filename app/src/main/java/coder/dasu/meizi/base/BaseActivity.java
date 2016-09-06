package coder.dasu.meizi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import coder.dasu.meizi.MeiziApp;
import okhttp3.OkHttpClient;

/**
 * Created by dasu on 2016/8/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected OkHttpClient mOkHttpClient = MeiziApp.getOkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
