package com.dasu.ganhuo.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.base.GankSp;
import com.dasu.ganhuo.mode.logic.history.HistoryController;
import com.dasu.ganhuo.mode.logic.home.HtmlDataEntity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.base.SubpageWithToolbarActivity;
import com.dasu.ganhuo.ui.view.recyclerview.LoadMoreRecyclerView;
import com.dasu.ganhuo.ui.view.recyclerview.OnPullUpRefreshListener;
import com.dasu.ganhuo.utils.TimeUtils;
import com.dasu.ganhuo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/18.
 * <p>
 * 往期推荐界面，二级界面
 */

public class HistoryActivity extends SubpageWithToolbarActivity implements OnItemClickListener<HtmlDataEntity> {

    @Override
    public String getToolbarTitle() {
        return "往期推荐";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initVariable();
        initView();
        showLoadingView();
        mHistoryController.loadBaseData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private List<HtmlDataEntity> mHistoryList;
    private HistoryController mHistoryController;

    private void initVariable() {
        mHistoryList = new ArrayList<>();
        mHistoryController = new HistoryController(this);
    }

    private LoadMoreRecyclerView mHistoryRv;
    private HistoryRecycleAdapter mRecycleAdapter;

    private void initView() {
        mHistoryRv = (LoadMoreRecyclerView) findViewById(R.id.rv_history_content);
        mHistoryRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleAdapter = new HistoryRecycleAdapter(mHistoryList);
        mRecycleAdapter.setOnItemClickListener(this);
        mHistoryRv.setAdapter(mRecycleAdapter);
        mHistoryRv.setOnPullUpRefreshListener(onPullUpRefresh());
    }

    private OnPullUpRefreshListener onPullUpRefresh() {
        return new OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                int counts = GankSp.getGankDateCounts(mContext);
                if (mHistoryList.size() >= counts) {
                    ToastUtils.show(mContext, "干货到底啦！");
                    mHistoryRv.nothingToRefresh();
                } else {
                    mHistoryController.startPullUpRefresh();
                }
            }
        };
    }

    public void updateHistory(List<HtmlDataEntity> data) {
        removeLoadingView();
        if (mHistoryList == null) {
            mHistoryList = new ArrayList<>();
        }
        mHistoryList.clear();
        mHistoryList.addAll(data);
        mRecycleAdapter.notifyDataSetChanged();
    }

    public void refreshHistory(List<HtmlDataEntity> data) {
        if (mHistoryList == null) {
            mHistoryList = new ArrayList<>();
        }
        int oldSize = mHistoryList.size();
        mHistoryList.addAll(data);
        mRecycleAdapter.notifyItemRangeInserted(oldSize, data.size());
        ToastUtils.show(mContext, "加载成功，新增" + data.size() + "项干货哦！");
    }

    @Override
    public void onItemClick(View view, HtmlDataEntity data, int position) {
        SomedayActivity.startActivity(mContext, data.getTitle(), data.getContent(), TimeUtils.adjustDate(data.getPublishedAt()));
    }
}
