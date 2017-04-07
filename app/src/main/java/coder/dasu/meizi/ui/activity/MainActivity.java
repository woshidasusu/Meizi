package coder.dasu.meizi.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.ui.GankDataFragmentFactory;
import coder.dasu.meizi.ui.GankDataFragmentFactory.FragmentKey;
import coder.dasu.meizi.ui.adapter.GankPagerFragmentAdapter;
import coder.dasu.meizi.ui.base.SwipeRefreshActivity;
import coder.dasu.meizi.ui.fragment.GankDataFragment;
import coder.dasu.meizi.utils.ListUtils;

public class MainActivity extends SwipeRefreshActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GankPagerFragmentAdapter mFragmentAdapter;
    private List<GankDataFragment> mFragmentList;

    @InjectView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @InjectView(R.id.main_content_viewpager)
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        initVariable();
        bindWidgets();
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
