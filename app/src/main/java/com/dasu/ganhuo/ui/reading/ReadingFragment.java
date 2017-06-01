package com.dasu.ganhuo.ui.reading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.reading.BlogEntity;
import com.dasu.ganhuo.mode.logic.reading.ReadingFController;
import com.dasu.ganhuo.ui.base.BaseFragment;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.base.OnSwipeRefreshListener;
import com.dasu.ganhuo.ui.home.WebViewActivity;
import com.dasu.ganhuo.ui.view.MorePageView;
import com.dasu.ganhuo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/22.
 */

public class ReadingFragment extends BaseFragment implements IReadingController{

    private static final String TAG = ReadingFragment.class.getSimpleName();

    @Override
    public String getReadingType() {
        Bundle bundle = getArguments();
        return bundle.getString("_type", "");
    }

    public ReadingFragment() {
    }

    //刷新的状态
    private static final int STATE_REFRESHING = 1;
    private static final int STATE_REFRESH_FINISH = 2;
    private int mRefreshState = STATE_REFRESH_FINISH;

    private ReadingFController mReadingController;
    private Context mContext;
    private OnSwipeRefreshListener mRefreshListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnSwipeRefreshListener) {
            mRefreshListener = (OnSwipeRefreshListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        initView(view);
        return view;
    }

    private List<BlogEntity> mBlogList = new ArrayList<>();
    private String mCurPage = "1";
    private RecyclerView mReadingRv;
    private ReadingRecycleAdapter mRecycleAdapter;

    private void initView(View view) {
        mReadingRv = (RecyclerView) view.findViewById(R.id.rv_reading_content);
        mReadingRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleAdapter = new ReadingRecycleAdapter(mBlogList);
        mRecycleAdapter.setOnItemClickListener(onItemClick());
        mReadingRv.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnPageClickListener(onPageClick());
    }

    private MorePageView.OnPageClickListener onPageClick() {
        return new MorePageView.OnPageClickListener() {
            @Override
            public void onNextPageClick() {
                mBlogList.clear();
                notifyDataSetChanged();
                mRefreshState = STATE_REFRESHING;
                mRefreshListener.onRefreshing();
                int page = Integer.parseInt(mCurPage);
                mReadingController.loadPageData(page + 1);
                ToastUtils.show(mContext, "" + (page + 1));
            }

            @Override
            public void onPrePageClick() {
                mBlogList.clear();
                notifyDataSetChanged();
                mRefreshState = STATE_REFRESHING;
                mRefreshListener.onRefreshing();
                int page = Integer.parseInt(mCurPage);
                mReadingController.loadPageData(page - 1);
                ToastUtils.show(mContext, "" + (page - 1));
            }

            @Override
            public void onPageSelected(String page) {
                mBlogList.clear();
                notifyDataSetChanged();
                mRefreshState = STATE_REFRESHING;
                mRefreshListener.onRefreshing();
                int p = Integer.parseInt(page);
                mReadingController.loadPageData(p);
                ToastUtils.show(mContext, page);
            }
        };
    }

    private OnItemClickListener<BlogEntity> onItemClick() {
       return new OnItemClickListener<BlogEntity>() {
           @Override
           public void onItemClick(View view, BlogEntity data, int position) {
               WebViewActivity.startActivity(mContext, data.getBlogUrl(), data.getTitle());
           }
       };
    }

    private void notifyDataSetChanged() {
        if (mRecycleAdapter != null) {
            mRecycleAdapter.notifyDataSetChanged();
        }
    }

    public ReadingFragment setArguments(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("_type", type);
        setArguments(bundle);
        return this;
    }

    @Override
    public void updateBlogs(List<BlogEntity> data) {
        mRefreshState = STATE_REFRESH_FINISH;
        mRefreshListener.onRefreshFinish();
        if (data == null || data.size() == 0) {
            return;
        }
        if (mBlogList == null) {
            mBlogList = new ArrayList<>();
        }
        mBlogList.clear();
        mBlogList.addAll(data);
        if (isFragmentVisible()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onLoadFailed() {
        mRefreshState = STATE_REFRESH_FINISH;
        mRefreshListener.onRefreshFinish();
        ToastUtils.show(mContext, "数据加载失败，请重试");
    }

    @Override
    public void updatePages(List<String> pages) {
        if (mRecycleAdapter != null) {
            mRecycleAdapter.updatePages(pages);
        }
    }

    @Override
    public void updateCurPage(String page) {
        mCurPage = page;
        if (mRecycleAdapter != null) {
            mRecycleAdapter.updateCurPage(page);
        }
    }

    public void retryLoadData() {
        mRefreshState = STATE_REFRESHING;
        mReadingController.loadBaseData();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            notifyDataSetChanged();
            if (mRefreshState == STATE_REFRESHING) {
                mRefreshListener.onRefreshing();
            }
        } else {
            mRefreshListener.onRefreshFinish();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        mReadingController = new ReadingFController(this);
        mRefreshState = STATE_REFRESHING;
        mReadingController.loadBaseData();
    }

}
