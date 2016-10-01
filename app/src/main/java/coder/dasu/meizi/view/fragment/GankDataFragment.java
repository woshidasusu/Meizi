package coder.dasu.meizi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import coder.dasu.meizi.data.bean.Data;
import coder.dasu.meizi.net.GankApi;
import coder.dasu.meizi.net.GankRetrofit;
import coder.dasu.meizi.net.response.GankDataResponse;
import coder.dasu.meizi.view.base.SwipeRefreshFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 *
 * Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义一个新的回调方法，当fragment可见状态改变时会触发的回调方法，介绍看下面
 *
 * @see #onFragmentVisibleChange(boolean)
 */
public abstract class GankDataFragment extends SwipeRefreshFragment {

    private static final String TAG = GankDataFragment.class.getSimpleName();

    protected String mType;

    public abstract String getType();

    protected int mLoadPage;
    private boolean hasCreateView;
    private boolean isLoadingData;
    private boolean isFragmentVisible;

    protected List<Data> mDataList;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(getTAG(), "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
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
        isLoadingData =false;
        isFragmentVisible = false;
    }

    /**
     * 加载服务器数据
     *
     * @param clearCache true 重新加载服务器数据
     *                   false 加载下一页数据
     */
    protected void loadServiceData(final boolean clearCache) {
        isLoadingData = true;
        GankApi gankApi = GankRetrofit.getGankService();
        Call<GankDataResponse<Data>> call = gankApi.getData(getType(), GankApi.DEFAULT_COUNT, mLoadPage);
        call.enqueue(new Callback<GankDataResponse<Data>>() {
            @Override
            public void onResponse(Call<GankDataResponse<Data>> call, Response<GankDataResponse<Data>> response) {
                Log.d(TAG, "[" + getType() + "] loadServiceData() -> onResponse(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    if (clearCache) {
                        mDataList.clear();
                    }
                    mDataList.addAll(response.body().results);
                    isLoadingData = false;
                    onLoadServiceDataSuccess();
                    if (isFragmentVisible) {
                        setRefresh(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<GankDataResponse<Data>> call, Throwable t) {
                Log.d(TAG, "[" + getType() + "] loadServiceData() -> onFailure(): " + t.getMessage());
                if(isFragmentVisible) {
                    setRefresh(false);
                }
                isLoadingData = false;
                onLoadServiceDataFailure();
            }
        });
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
//        mLoadPage = 1;
//       loadServiceData(true);
        isLoadingData = true;
        Toast.makeText(getActivity(), "开始加载..." ,Toast.LENGTH_SHORT).show();
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
        Log.w(getTAG(), "onFragmentVisibleChange -> isVisible: " + isVisible);
        if (isVisible) {
            if(isLoadingData) {
                setRefresh(true);
            } else {

            }
        } else {
            dismissAnimation();
        }
    }
}
