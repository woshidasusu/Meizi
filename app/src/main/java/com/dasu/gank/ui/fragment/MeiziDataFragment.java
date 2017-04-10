package com.dasu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.entity.Data;
import com.dasu.gank.ui.adapter.MeiziWallAdapter;
import com.dasu.gank.ui.base.OnItemClickListener;
import com.dasu.gank.utils.ListUtils;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class MeiziDataFragment extends GankDataFragment {

    private static final String TAG = MeiziDataFragment.class.getSimpleName();

    private MeiziWallAdapter mMeiziAdapter;

    private RecyclerView mMeiziWallView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_meizi, container, false);
            findView(rootView);
            bindWidgets();
        }
        return rootView;
    }



    public MeiziDataFragment(String type) {
        mType = type;
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (ListUtils.isEmpty(mDataList) && !isLoadingData()) {
                setRefresh(true);
                loadServiceData(false);
            }
        }
    }

    private void findView(View view) {
        mMeiziWallView = (RecyclerView) view.findViewById(R.id.rv_meizi_photo_wall);
    }

    private void bindWidgets() {
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMeiziWallView.setLayoutManager(layoutManager);
        mMeiziAdapter = new MeiziWallAdapter(getActivity(), mDataList);
        mMeiziWallView.setAdapter(mMeiziAdapter);
        mMeiziAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Data meizhi) {
                Snackbar.make(view, view == picture ? "meizi" : "text", Snackbar.LENGTH_SHORT).show();
            }
        });
        mMeiziWallView.addOnScrollListener(getMeiziWallOnScroll(layoutManager));

    }

    private RecyclerView.OnScrollListener getMeiziWallOnScroll(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = layoutManager.findLastVisibleItemPositions(null)[1] >= mDataList.size() - 1;
                Log.d(TAG, "isRefresh() : " + isRefreshing() + "   isBottom():" + isBottom);
                if (!isRefreshing() && isBottom) {
                    loadNextPageData();
                }
            }
        };
    }

    public synchronized void loadNextPageData() {
        //TODO 加载失败时，loadpager不应该+1
        mLoadPage++;
        setRefresh(true);
        loadServiceData(false);
    }

    @Override
    protected void onLoadServiceDataSuccess() {
        mMeiziAdapter.notifyDataSetChanged();
    }



    @Override
    public String getType() {
        return mType;
    }

    @Override
    public String getLocalLatestIssue() {
        return null;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
