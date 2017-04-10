package com.dasu.gank.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.dasu.gank.R;
import com.dasu.gank.ui.base.BaseActivity;

/**
 * Created by dasu on 2016/10/9.
 * ttps://github.com/woshidasusu/Meizi
 */
public class DayGankDetailActivity extends BaseActivity {

    private static final String TAG = DayGankDetailActivity.class.getSimpleName();

    private RecyclerView mDayDetaiView;

    @Override
    public int provideContentView() {
        return R.layout.activity_day_gank_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
    }

    private void findView() {
        mDayDetaiView = (RecyclerView) findViewById(R.id.rv_day_detail);
    }
}
