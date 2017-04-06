package coder.dasu.meizi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import coder.dasu.meizi.ui.fragment.GankDataFragment;

/**
 * Created by dasu on 2016/9/29.
 * https://github.com/woshidasusu/Meizi
 */
public class GankPagerFragmentAdapter extends FragmentPagerAdapter {

    private GankDataFragment mCurrentFragment;
    private List<GankDataFragment> mFragmentList;


    public GankPagerFragmentAdapter(FragmentManager fm, List<GankDataFragment> data) {
        super(fm);
        mFragmentList = data;
    }

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
        return mFragmentList.get(position).getType();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = (GankDataFragment) object;
    }

    public GankDataFragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
