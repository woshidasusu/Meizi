package coder.dasu.meizi.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import coder.dasu.meizi.MeiziApp;
import coder.dasu.meizi.data.dao.DaoSession;

/**
 * Created by dasu on 2016/8/25.
 * https://github.com/woshidasusu/Meizi
 *
 * 1、提供唯一的 DaoSession数据库访问接口
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected static DaoSession mDaoSession = MeiziApp.getDaoSession();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());

        ButterKnife.inject(this);
    }

    public abstract int provideContentView();

}
