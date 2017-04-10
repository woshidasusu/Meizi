package com.dasu.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.dasu.gank.R;

/**
 * Created by dasu on 2016/8/25.
 * https://github.com/woshidasusu/Meizi
 */
public abstract class BaseActivity extends AppCompatActivity {

    private long mFirstClickTime;

    protected Toolbar mToolbar;
    protected CollapsingToolbarLayout mCollapsToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());
        findView();
        bindWidgets();
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mCollapsToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        ActivityStack.getInstance().pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().popActivity(this);
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsToolbar = (CollapsingToolbarLayout) findViewById(R.id.collaps_toolbar);
    }

    private void bindWidgets() {
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick();
            }
        });
    }

    protected void onToolbarClick() {
        long secClick = System.currentTimeMillis();
        if ((secClick - mFirstClickTime) < 1000) {
            onToolbarDoubleClick();
        }
        mFirstClickTime = secClick;
    }

    protected void onToolbarDoubleClick() {
    }

    protected abstract int provideContentView();

}
