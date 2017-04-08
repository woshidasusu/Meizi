package com.dasu.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.dasu.gank.R;

/**
 * Created by dasu on 2016/10/1.
 * https://github.com/woshidasusu/Meizi
 */
public abstract class SwipeRefreshActivity extends BaseActivity implements ISwipeRefreshListener{

    private static final String TAG = SwipeRefreshActivity.class.getSimpleName();

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.deeppink),
                    getResources().getColor(R.color.tomato),
                    getResources().getColor(R.color.red));
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData();
                }
            });
        }
    }

    @Override
    public void setRefresh(boolean enable) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!mSwipeRefreshLayout.isRefreshing() && !enable) {
            return;
        }
        if (!enable) {
            //手动延长刷新时间
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        } else {
            //手动推迟动画的显示，防止取消/显示间隔时间短，造成的一些问题
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }, 10);
        }
    }

    public boolean isRefreshing() {
        if (mSwipeRefreshLayout == null) {
            return false;
        }
        return mSwipeRefreshLayout.isRefreshing();
    }

    @Override
    public void dismissAnimation() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
