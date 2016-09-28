package coder.dasu.meizi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

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
 */
public abstract class GankDataFragment extends SwipeRefreshFragment {

    private static final String TAG = GankDataFragment.class.getSimpleName();

    protected String mType;

    public abstract String getType();

    public GankDataFragment() {}

    protected int mLoadPage;

    protected List<Data> mDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    private void initVariable() {
        mDataList = new ArrayList<>();
        mLoadPage = 1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void loadLocalData() {

    }

    /**
     * 加载服务器数据
     *
     * @param clearCache true 重新加载服务器数据
     *                   false 加载下一页数据
     */
    protected void loadServiceData(final boolean clearCache) {
        GankApi gankApi = GankRetrofit.getGankService();
        Call<GankDataResponse<Data>> call = gankApi.getData(getType(), GankApi.DEFAULT_COUNT, mLoadPage);
        call.enqueue(new Callback<GankDataResponse<Data>>() {
            @Override
            public void onResponse(Call<GankDataResponse<Data>> call, Response<GankDataResponse<Data>> response) {
                Log.d(TAG,"[" + getType() + "] loadServiceData() -> onResponse(): " + response.isSuccessful());
                if (response.isSuccessful()) {
                    if (clearCache) {
                        mDataList.clear();
                    }
                    mDataList.addAll(response.body().results);
                    onLoadServiceDataSuccess();
                    setRefresh(false);
                }
            }

            @Override
            public void onFailure(Call<GankDataResponse<Data>> call, Throwable t) {
                Log.d(TAG,"[" + getType() + "] loadServiceData() -> onFailure(): " + t.getMessage());
                setRefresh(false);
                onLoadServiceDataFailure();
            }
        });
    }

    protected void onLoadServiceDataFailure() {
        Snackbar.make(getView(), "加载失败,请重试", Snackbar.LENGTH_INDEFINITE)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadServiceData(false);
                    }
                })
                .show();
    }

    protected void onLoadServiceDataSuccess() {

    }

    @Override
    public void loadData() {
        mLoadPage = 1;
        loadServiceData(true);
    }
}
