package com.dasu.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.entity.DayGank;
import com.dasu.gank.mode.entity.DayPublish;
import com.dasu.gank.ui.adapter.DayGankAdapter;
import com.dasu.gank.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.dasu.gank.R.id.rv_day;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class DayGankFragment extends GankDataFragment {

    private static final String TAG = DayGankFragment.class.getSimpleName();

    private DayGankAdapter mDataAdapter;
    private Context mContext;
    private List<DayPublish> mDataList;

    private RecyclerView mAndroidDataView;

    public DayGankFragment(String type) {
        mType = type;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_day_gank, container, false);
            findView(rootView);
            bindWidgets();
        }
        return rootView;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            //todo isLoading?
            if (ListUtils.isEmpty(mDataList)) {
                setRefresh(true);
                loadServiceData(false);
            }
        }
    }

    private void initVariable() {
        mDataList = new ArrayList<>();
        mDaoSession.startAsyncSession().runInTx(new Runnable() {
            @Override
            public void run() {
                mDataList.addAll(mDaoSession.getDayPublishDao().queryBuilder().list());
                Collections.sort(mDataList);
            }
        });
    }

    private void findView(View view) {
        mAndroidDataView = (RecyclerView) view.findViewById(rv_day);
    }

    private void bindWidgets() {
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAndroidDataView.setLayoutManager(layoutManager);
        mDataAdapter = new DayGankAdapter(getActivity(), mDataList);
        mAndroidDataView.setAdapter(mDataAdapter);

        mDataAdapter.setOnItemClickListener(getOnItemClick());
    }


    @Override
    public void loadData() {

    }

    @Override
    protected void loadServiceData(boolean clearCache) {

    }

    @Override
    public String getLocalLatestIssue() {
        return null;
    }

    @Override
    protected void onLoadServiceDataSuccess() {
        mDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onLoadServiceDataFailure() {

    }

    public DayGankAdapter.OnItemClickListener getOnItemClick() {
        return new DayGankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, DayGank dayGank) {
                Snackbar.make(view, dayGank.getDate(), Snackbar.LENGTH_SHORT).show();
            }
        };
    }

}
