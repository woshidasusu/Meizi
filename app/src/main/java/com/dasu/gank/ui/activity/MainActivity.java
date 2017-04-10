package com.dasu.gank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.logic.update.UpdateController;
import com.dasu.gank.ui.GankDataFragmentFactory;
import com.dasu.gank.ui.GankDataFragmentFactory.FragmentKey;
import com.dasu.gank.ui.adapter.GankPagerFragmentAdapter;
import com.dasu.gank.ui.base.SwipeRefreshActivity;
import com.dasu.gank.ui.fragment.GankDataFragment;
import com.dasu.gank.ui.view.UpdateDialog;
import com.dasu.gank.utils.ListUtils;
import com.dasu.gank.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SwipeRefreshActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GankPagerFragmentAdapter mFragmentAdapter;
    private List<GankDataFragment> mFragmentList;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewGroup mContentLayout;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        findView();
        bindWidgets();
        UpdateController.checkUpdate(this, new UpdateDialog(this));
        addNoNetworkTipView(mContentLayout);
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_main;
    }

    private void initVariable() {
        mFragmentList = new ArrayList<>();
        GankDataFragmentFactory factory = new GankDataFragmentFactory();
        for (FragmentKey key: FragmentKey.values()) {
            mFragmentList.add(factory.newFragment(key.getValue()));
        }
        mFragmentAdapter = new GankPagerFragmentAdapter(getSupportFragmentManager(), mFragmentList);

    }

    private void findView() {
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.main_content_viewpager);
        mContentLayout = (ViewGroup) findViewById(R.id.layout_content);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void bindWidgets() {
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
    }

    private void onFabClick() {
        ToastUtils.show(this, "test.....");
    }


    @Override
    public void loadData() {
        if (!ListUtils.isEmpty(mFragmentList)) {
            mFragmentAdapter.getCurrentFragment().loadData();
        }
    }
}
