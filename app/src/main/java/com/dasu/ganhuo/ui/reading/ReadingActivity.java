package com.dasu.ganhuo.ui.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.reading.ReadingAController;
import com.dasu.ganhuo.mode.logic.reading.TypeEntity;
import com.dasu.ganhuo.ui.base.BaseActivity;
import com.dasu.ganhuo.ui.base.OnSwipeRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/14.
 *
 * 闲读页面，该类主要管理着多个Fragment，页面具体Ui由Fragment实现
 */

public class ReadingActivity extends BaseActivity implements OnSwipeRefreshListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initVariable();
        initView();
        showLoadingView(mContentLayout);
        mReadingController.getReadingTypes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private ReadingAController mReadingController;
    private ReadingPagerAdapter mPagerAdapter;

    private void initVariable() {
        mReadingController = new ReadingAController(this);
        List<TypeEntity> data = new ArrayList<>();
        mPagerAdapter = new ReadingPagerAdapter(getSupportFragmentManager(), data);
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mBackBtn;
    private SwipeRefreshLayout mRefreshLayout;
    private ViewGroup mContentLayout;

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.layout_reading_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_reading_content);
        mContentLayout = (ViewGroup) findViewById(R.id.layout_content);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mBackBtn = (ImageView) findViewById(R.id.ibtn_reading_back);
        mBackBtn.setOnClickListener(onBackBtnClick());
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_reading_content);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.red),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.pink));
        mRefreshLayout.setOnRefreshListener(onRefresh());
    }

    private SwipeRefreshLayout.OnRefreshListener onRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCurRefreshFragment().retryLoadData();
            }
        };
    }

    private ReadingFragment getCurRefreshFragment() {
        return (ReadingFragment) mPagerAdapter.getCurrentFragment();
    }

    private View.OnClickListener onBackBtnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    public void updateTypes(List<TypeEntity> data) {
        removeLoadingView();
        if (data == null || data.size() == 0) {
            return;
        }
        mPagerAdapter.setData(data);
    }

    @Override
    public void onRefreshing() {
        if (mRefreshLayout != null && !mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onRefreshFinish() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
