package coder.dasu.meizi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.bean.Data;
import coder.dasu.meizi.net.GankRetrofit;
import coder.dasu.meizi.net.response.GankDataResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class AndroidDataFragment extends GankDataFragment {

    private static final String TAG = AndroidDataFragment.class.getSimpleName();

    private int mLoadPage;

    public AndroidDataFragment(String type) {
        mType = type;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void initVariable() {
        mLoadPage = 1;
    }



    @Override
    public void loadData() {
        setRefresh(true);
        Call<GankDataResponse<Data>> call = GankRetrofit.getGankService().getData(mType, 10, mLoadPage++);
        call.enqueue(new Callback<GankDataResponse<Data>>() {
            @Override
            public void onResponse(Call<GankDataResponse<Data>> call, Response<GankDataResponse<Data>> response) {
                Log.d(TAG, "loadData() -> onResponse() " );
                setRefresh(false);
            }

            @Override
            public void onFailure(Call<GankDataResponse<Data>> call, Throwable t) {
                Log.d(TAG, "loadData() -> onFailure() " );
                setRefresh(false);
            }
        });

    }

}
