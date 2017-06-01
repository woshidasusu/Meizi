package com.dasu.ganhuo.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.CategoryFController;
import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.ui.base.BaseFragment;
import com.dasu.ganhuo.ui.base.OnSwipeRefreshListener;
import com.dasu.ganhuo.ui.home.WebViewActivity;
import com.dasu.ganhuo.ui.meizi.ImageActivity;
import com.dasu.ganhuo.ui.view.recyclerview.LoadMoreRecyclerView;
import com.dasu.ganhuo.ui.view.recyclerview.OnPullUpRefreshListener;
import com.dasu.ganhuo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/20.
 *
 * 各类型干货的数据展示界面，
 */

public class CategoryFragment extends BaseFragment implements ICategoryController {
    private static final String TAG = CategoryFragment.class.getSimpleName();

    @Override
    public String getCategoryType() {
        Bundle bundle = getArguments();
        return bundle.getString("_type", "");
    }

    public CategoryFragment() {
    }

    //刷新的状态
    private static final int STATE_REFRESHING = 1;
    private static final int STATE_REFRESH_FINISH = 2;
    private int mRefreshState = STATE_REFRESH_FINISH;

    private CategoryFController mCategoryController;
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        return view;
    }

    private List<GanHuoEntity> mGanHuoList = new ArrayList<>();

    private LoadMoreRecyclerView mCategoryRv;
    private CategoryRecycleAdapter mRecycleAdapter;

    private void initView(View view) {
        mCategoryRv = (LoadMoreRecyclerView) view.findViewById(R.id.rv_category_content);
        mCategoryRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleAdapter = new CategoryRecycleAdapter(mGanHuoList);
        mRecycleAdapter.setOnItemClickListener(onItemClick());
        mCategoryRv.setAdapter(mRecycleAdapter);
        mCategoryRv.setOnPullUpRefreshListener(onPullUpRefresh());
    }

    private OnPullUpRefreshListener onPullUpRefresh() {
        return new OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                //正在刷新的话，就不加载下拉刷新了
                if (mRefreshState == STATE_REFRESHING) {
                    return;
                }
                mRefreshState = STATE_REFRESHING;
                mRefreshListener.onRefreshing();
                mCategoryController.startPullUpRefresh();
            }
        };
    }

    private OnItemClickListener<GanHuoEntity> onItemClick() {
        return new OnItemClickListener<GanHuoEntity>() {
            @Override
            public void onImageClick(List<String> imgUrls) {
                ImageActivity.startActivity(mContext, 0, (ArrayList<String>) imgUrls);
            }

            @Override
            public void onItemClick(GanHuoEntity data) {
                WebViewActivity.startActivity(mContext, data.getUrl(), data.getDesc());
            }
        };
    }

    private void notifyDataSetChanged() {
        if (mRecycleAdapter != null) {
            mRecycleAdapter.notifyDataSetChanged();
        }
    }

    public CategoryFragment setArguments(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("_type", type);
        setArguments(bundle);
        return this;
    }

    @Override
    public void updateGanHuo(List<GanHuoEntity> data) {
        mRefreshState = STATE_REFRESH_FINISH;
        mRefreshListener.onRefreshFinish();
        if (data == null || data.size() == 0) {
            return;
        }
        if (mGanHuoList == null) {
            mGanHuoList = new ArrayList<>();
        }
        mGanHuoList.clear();
        mGanHuoList.addAll(data);
        if (isFragmentVisible()) {
            notifyDataSetChanged();
        }
    }

    public void refreshData(List<GanHuoEntity> data) {
        mRefreshState = STATE_REFRESH_FINISH;
        mRefreshListener.onRefreshFinish();
        if (mGanHuoList == null) {
            mGanHuoList = new ArrayList<>();
        }
        int oldSize = mGanHuoList.size();
        mGanHuoList.addAll(data);
        mRecycleAdapter.notifyItemRangeInserted(oldSize, data.size());
        ToastUtils.show(mContext, "加载成功，新增" + data.size() + "项干货哦！");
    }

    @Override
    public void onLoadFailed() {
        mRefreshState = STATE_REFRESH_FINISH;
        mRefreshListener.onRefreshFinish();
        ToastUtils.show(mContext, "数据加载失败，请重试");
    }

    /**
     * 重新加载数据
     */
    public void retryLoadData() {
        mRefreshState = STATE_REFRESHING;
        mCategoryController.loadBaseData();
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
        mCategoryController = new CategoryFController(this);
        mRefreshState = STATE_REFRESHING;
        mCategoryController.loadBaseData();
    }
}
