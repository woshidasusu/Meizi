package com.dasu.gank.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dasu.gank.AppConstant;
import com.dasu.gank.GankApplication;
import com.dasu.gank.mode.entity.Data;
import com.dasu.gank.mode.entity.GankDataResponse;
import com.dasu.gank.mode.net.retrofit.GankController;
import com.dasu.gank.ui.base.SwipeRefreshFragment;
import com.dasu.gank.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 * <p>
 * 1、Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义一个新的回调方法，当fragment可见状态改变时会触发的回调方法，介绍看下面
 * <p>
 * 2、因为干货都是技术博客或Github项目，一下基本数据实时性要求不高，因为基本不会发生变化，所以数据加载的策略采用：
 * 优先从本地数据库中加载，如果本地数据库没有，再从服务器加载
 *
 * @see #onFragmentVisibleChange(boolean)
 */
public abstract class GankDataFragment extends SwipeRefreshFragment {

    private static final String TAG = GankDataFragment.class.getSimpleName();

    protected String mType;
    protected int mLoadPage;
    protected String mServerLatestIssue;
    protected String mLocalLatestIssue;
    private boolean hasCreateView;
    private boolean isLoadingData;
    private boolean isFragmentVisible;

    protected List<Data> mDataList;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        mDataList = new ArrayList<>();
        mLoadPage = 1;
        hasCreateView = false;
        isLoadingData = false;
        isFragmentVisible = false;
        mLocalLatestIssue = getLocalLatestIssue();
        mServerLatestIssue = GankApplication.getConfigSP().getString(AppConstant.GANK_SERVER_LATEST_ISSUE_TIME);
    }

    /*********************************************************************
     * 子类的公共方法，子类均可直接调用，有些公有方法已默认执行，子类如不需要则覆盖重写
     *********************************************************************/

    /**
     * 判断是否需要从服务器获取数据
     */
    public boolean isNeedLoadServerData() {
        if (TextUtils.isEmpty(mLocalLatestIssue)) {
            return true;
        }
        long server = TimeUtils.string2Milliseconds(mServerLatestIssue, TimeUtils.DAY_SDF);
        long local = TimeUtils.string2Milliseconds(mLocalLatestIssue, TimeUtils.DAY_SDF);
        return server > local;
    }

    public abstract String getLocalLatestIssue();

    public abstract String getType();

    /**
     * 加载服务器数据
     *
     * @param clearCache true 重新加载服务器数据
     *                   false 加载下一页数据
     */
    protected void loadServiceData(final boolean clearCache) {
        isLoadingData = true;
        GankController.getData(getType(), 10, mLoadPage, new Callback<GankDataResponse>() {
            @Override
            public void onResponse(Call<GankDataResponse> call, Response<GankDataResponse> response) {
                Log.d(TAG, "[" + getType() + "] loadServiceData() -> onResponse(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    if (clearCache) {
                        mDataList.clear();
                    }
                    mDataList.addAll(response.body().results);
                    GankApplication.getConfigSP().putString(AppConstant.GANK_ANDROID_LATEST_UPDATE_TIME, mServerLatestIssue);
                    mDaoSession.startAsyncSession().insertOrReplaceInTx(Data.class, mDataList);
                    if (isFragmentVisible) {
                        setRefresh(false);
                    }
                    isLoadingData = false;
                    onLoadServiceDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<GankDataResponse> call, Throwable t) {
                Log.d(TAG, "[" + getType() + "] loadServiceData() -> onFailure(): " + t.getMessage());
                if (isFragmentVisible) {
                    setRefresh(false);
                }
                isLoadingData = false;
                onLoadServiceDataFailure();
            }
        });
    }

    /**
     * 加载本地数据库数据
     *
     * @param clearCache true 重新加载服务器数据
     *                   false 加载下一页数据
     */
    protected void loadLoaclData(final boolean clearCache) {
        isLoadingData = true;
    }

    public boolean isLoadingData() {
        return isLoadingData;
    }


    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当触发下拉刷新手势时会回调该方法
     *
     * @see SwipeRefreshFragment#loadData()
     */
    @Override
    public void loadData() {
        mLoadPage = 1;
        loadServiceData(true);
    }

    /**
     * 当从服务器加载数据失败后会回调该方法
     */
    protected void onLoadServiceDataFailure() {
    }

    /**
     * 当从服务器加载数据失败后会回调该方法
     */
    protected void onLoadServiceDataSuccess() {

    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        Log.d(getTAG(), "onFragmentVisibleChange -> isVisible: " + isVisible);
        if (isVisible) {
            if (isLoadingData) {
                setRefresh(true);
            } else {

            }
        } else {
            dismissAnimation();
        }
    }
}
