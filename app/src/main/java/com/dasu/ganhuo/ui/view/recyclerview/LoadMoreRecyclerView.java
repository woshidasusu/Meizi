package com.dasu.ganhuo.ui.view.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.utils.LogUtils;

/**
 * Created by dasu on 2017/4/26.
 * <p>
 * 上拉加载更多数据的RecyclerView
 * 可配合 {@link LoadMoreRecycleAdapter} 一起使用，实现有默认上拉加载动画提示
 */

public class LoadMoreRecyclerView extends BaseRecyclerView implements OnPullUpRefreshListener {
    private static final String TAG = LoadMoreRecyclerView.class.getSimpleName();

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private static final int STATE_REFRESHING = 2;
    private static final int STATE_REFRESH_END = 3;

    private LoadMoreRecycleAdapter mLoadMoreAdapter;
    private boolean isAtBottom;
    private OnPullUpRefreshListener mUpRefreshListener;
    private int mRefreshState = STATE_REFRESH_END;

    private void init() {
        isAtBottom = false;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof LoadMoreRecycleAdapter) {
            mLoadMoreAdapter = (LoadMoreRecycleAdapter) adapter;
            mLoadMoreAdapter.setOnPullUpRefresh(this);
        }
    }

    @Override
    protected void onDataChange() {
        super.onDataChange();
        //确保footview只有当滑动到底端时才显示
        //数据不够一页时应该隐藏起来
        if (mLoadMoreAdapter != null) {
            mLoadMoreAdapter.setFootViewVisibility(GONE);
        }
        isAtBottom = false;
    }

    @Override
    public void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);
        if (mLoadMoreAdapter != null && mLoadMoreAdapter.getFootView() == child) {
            mLoadMoreAdapter.setFootIcon(R.drawable.icon_pull_up);
            mLoadMoreAdapter.setFootTip("继续上拉或点击此处加载更多");
            mRefreshState = STATE_REFRESH_END;
            LogUtils.d(TAG, "上拉加载完成");
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (dy > 0) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            int lastIndex = findLastVisibleItemPosition();
            int totalItems = layoutManager.getItemCount();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                //瀑布流的布局当滑动到阀值时直接自动触发加载更多
                //阀值设置为最后一行布局可见时
                int span = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
                if (lastIndex >= (totalItems - span)) {
                    onPullUpRefresh();
                }
            } else {
                //其它布局当滑到底端再次向上滑时才会触发加载更多
                int lastCompleteIndex = findLastCompletelyVisibleItemPosition();
                if (lastCompleteIndex >= totalItems - 1) {
                    if (mLoadMoreAdapter != null) {
                        mLoadMoreAdapter.setFootViewVisibility(VISIBLE);
                    }
                    isAtBottom = true;
                }
            }
        } else {
            isAtBottom = false;
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (isAtBottom && state == SCROLL_STATE_IDLE) {
            LogUtils.d(TAG, "开始上拉加载");
            isAtBottom = false;
            if (mRefreshState == STATE_REFRESH_END) {
                mLoadMoreAdapter.setFootIcon(R.drawable.rotate_loading);
                mLoadMoreAdapter.setFootTip("正在加载中...");
            }
            mRefreshState = STATE_REFRESHING;
            onPullUpRefresh();
        }
    }

    @Override
    public void onPullUpRefresh() {
        if (mUpRefreshListener != null) {
            mUpRefreshListener.onPullUpRefresh();
        }
    }

    public void nothingToRefresh() {
        mRefreshState = STATE_REFRESH_END;
        if (mLoadMoreAdapter != null) {
            mLoadMoreAdapter.setFootIcon(R.drawable.icon_pull_up);
            mLoadMoreAdapter.setFootTip("到底啦！");
        }
    }

    public void setOnPullUpRefreshListener(OnPullUpRefreshListener listener) {
        mUpRefreshListener = listener;
    }
}
