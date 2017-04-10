package com.dasu.gank.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.entity.Data;
import com.dasu.gank.ui.adapter.AndroidDataAdapter;
import com.dasu.gank.ui.base.OnItemClickListener;
import com.dasu.gank.utils.ListUtils;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class PushDataFragment extends GankDataFragment {

    private static final String TAG = PushDataFragment.class.getSimpleName();

    private AndroidDataAdapter mDataAdapter;
    private Context mContext;

    private RecyclerView mAndroidDataView;

    public PushDataFragment(String type) {
        mType = type;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_push, container, false);
            findView(rootView);
            bindWidgets();
        }
        return rootView;
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
        mAndroidDataView = (RecyclerView) view.findViewById(R.id.rv_push);
    }

    private void bindWidgets() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAndroidDataView.setLayoutManager(manager);
        mDataAdapter = new AndroidDataAdapter(getActivity(), mDataList);
        mAndroidDataView.setAdapter(mDataAdapter);

        mDataAdapter.setOnItemClickListener(getOnItemClick());
    }

    @Override
    protected void onLoadServiceDataSuccess() {
        mDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onLoadServiceDataFailure() {

    }

    public OnItemClickListener getOnItemClick() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Data data) {
                Snackbar.make(view, data.getWho(), Snackbar.LENGTH_SHORT).show();
            }
        };
    }

}
