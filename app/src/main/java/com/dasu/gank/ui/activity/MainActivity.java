package com.dasu.gank.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.net.update.UpdateController;
import com.dasu.gank.ui.GankDataFragmentFactory;
import com.dasu.gank.ui.GankDataFragmentFactory.FragmentKey;
import com.dasu.gank.ui.adapter.GankPagerFragmentAdapter;
import com.dasu.gank.ui.base.SwipeRefreshActivity;
import com.dasu.gank.ui.fragment.GankDataFragment;
import com.dasu.gank.ui.view.UpdateDialog;
import com.dasu.gank.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends SwipeRefreshActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GankPagerFragmentAdapter mFragmentAdapter;
    private List<GankDataFragment> mFragmentList;

    @InjectView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.main_content_viewpager)
    ViewPager mViewPager;
    @InjectView(R.id.layout_content)
    ViewGroup mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        initVariable();
        bindWidgets();
        UpdateController.checkUpdate(this, new UpdateDialog(this));
        addNoNetworkTipView(mContentLayout);
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_main;
    }

    public void initVariable() {
        mFragmentList = new ArrayList<>();
        GankDataFragmentFactory factory = new GankDataFragmentFactory();
        for (FragmentKey key: FragmentKey.values()) {
            mFragmentList.add(factory.newFragment(key.getValue()));
        }
        mFragmentAdapter = new GankPagerFragmentAdapter(getSupportFragmentManager(), mFragmentList);

    }

    public void bindWidgets() {
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void loadData() {
        if (!ListUtils.isEmpty(mFragmentList)) {
            mFragmentAdapter.getCurrentFragment().loadData();
        }
    }
}
