package com.dasu.ganhuo.ui.reading;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.dasu.ganhuo.mode.logic.reading.TypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/22.
 */
class ReadingPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<TypeEntity> mTabNameList;
    private Fragment mCurFragment;

    ReadingPagerAdapter(FragmentManager fm, List<TypeEntity> data) {
        super(fm);
        setData(data);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNameList.get(position).getName();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurFragment = (Fragment) object;
    }

    Fragment getCurrentFragment() {
        return mCurFragment;
    }

    public void setData(List<TypeEntity> data) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.clear();
        mTabNameList = data;
        for (TypeEntity entity : data) {
            Fragment f = new ReadingFragment().setArguments(entity.getType());
            mFragmentList.add(f);
        }
        notifyDataSetChanged();
    }
}
