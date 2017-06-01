package com.dasu.ganhuo.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.CategoryAController;
import com.dasu.ganhuo.ui.base.BaseActivity;
import com.dasu.ganhuo.ui.base.OnSwipeRefreshListener;

/**
 * Created by dasu on 2017/4/14.
 *
 * 分类浏览的主界面，该类只负责管理多个Fragment，具体的ui和mode交互交由相应的Fragment和controller负责
 */

public class CategoryActivity extends BaseActivity implements OnSwipeRefreshListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initVariable();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private CategoryAController mCategoryController;
    private CategoryPagerAdapter mPagerAdapter;

    private void initVariable() {
        mCategoryController = new CategoryAController(this);
        mPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), mCategoryController.getCategoryList());
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mBackBtn;
    private SwipeRefreshLayout mRefreshLayout;

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.layout_category_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_category_content);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mBackBtn = (ImageView) findViewById(R.id.ibtn_category_back);
        mBackBtn.setOnClickListener(onBackBtnClick());
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_category_content);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.red),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.pink));
        mRefreshLayout.setOnRefreshListener(onPullDownRefresh());
    }

    private SwipeRefreshLayout.OnRefreshListener onPullDownRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCurRefreshFragment().retryLoadData();
            }
        };
    }

    private CategoryFragment getCurRefreshFragment() {
        return (CategoryFragment) mPagerAdapter.getCurrentFragment();
    }

    private View.OnClickListener onBackBtnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
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
