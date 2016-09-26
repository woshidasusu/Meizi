package coder.dasu.meizi.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import coder.dasu.meizi.R;
import coder.dasu.meizi.listener.IMainAF;
import coder.dasu.meizi.view.FragmentFactory;
import coder.dasu.meizi.view.FragmentFactory.FragmentKey;
import coder.dasu.meizi.view.base.BaseActivity;
import coder.dasu.meizi.view.base.BaseFragment;
import coder.dasu.meizi.view.base.SwipeRefreshFragment;

public class MainActivity extends BaseActivity implements IMainAF {

    private static final String TAG = MainActivity.class.getSimpleName();

    private long mFirstClickTime;
    private List<BaseFragment> mFragmentList;

    @InjectView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.main_content_viewpager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVariable();
        bindWidgets();
    }


    @Override
    public int provideContentView() {
        return R.layout.activity_main;
    }

    public void initVariable() {
        mFragmentList = new ArrayList<>();
        FragmentFactory factory = new FragmentFactory();
        for (FragmentKey key: FragmentKey.values()) {
            mFragmentList.add(factory.newFragment(key.getValue()));
        }
    }

    public void bindWidgets() {
        mViewPager.setAdapter(getViewPagerAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(getTabSelectedListener());
    }
    @OnClick(R.id.toolbar)
    public void onToolbarDoubleClick() {
        long secClick = System.currentTimeMillis();
        if ((secClick - mFirstClickTime) < 1000) {
            FragmentManager manager = getSupportFragmentManager();
            for (Fragment fragment : manager.getFragments()) {
                if (fragment instanceof SwipeRefreshFragment && fragment.isVisible()) {
                    ((SwipeRefreshFragment) fragment).onToolbarDoubleClick();
                }
            }
        }
        mFirstClickTime = secClick;
    }

    @Override
    public void loadData() {
        FragmentManager manager = getSupportFragmentManager();
        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof SwipeRefreshFragment && fragment.isVisible()) {
                ((SwipeRefreshFragment) fragment).loadData();
            }
        }
    }

    public TabLayout.OnTabSelectedListener getTabSelectedListener() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    public PagerAdapter getViewPagerAdapter() {
        return new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentList.get(position).getValue();
            }
        };
    }
}
