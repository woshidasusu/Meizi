package coder.dasu.meizi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.bean.Meizi;
import coder.dasu.meizi.listener.IMainAF;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.net.GankApi;
import coder.dasu.meizi.net.GankRetrofit;
import coder.dasu.meizi.net.response.GankDataResponse;
import coder.dasu.meizi.view.adapter.MeiziWallAdapter;
import coder.dasu.meizi.view.base.SwipeRefreshFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends SwipeRefreshFragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    @InjectView(R.id.rv_meizi_photo_wall)
    RecyclerView mMeiziWallView;

    private MeiziWallAdapter mMeiziAdapter;
    private Context mContext;
    private IMainAF mControler;

    private int mLoadPage;
    private List<Meizi> mMeiziList;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof IMainAF)) {
            throw new UnsupportedOperationException(context.getClass().getSimpleName()
                    + " does not implements IMainAF interface");
        }
        mControler = (IMainAF) context;
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mLoadPage = 1;
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * initialization widgets' state. such as check box's select state
     * call after {@link #onActivityCreated(Bundle)} before {@link #onStart()}
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLocalData();
        loadServiceData(true);
    }

    private void initView() {
        mMeiziList = new ArrayList<>();
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMeiziWallView.setLayoutManager(layoutManager);
        mMeiziAdapter = new MeiziWallAdapter(mContext, mMeiziList);
        mMeiziWallView.setAdapter(mMeiziAdapter);
        mMeiziAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Meizi meizhi) {
                Snackbar.make(view, view == picture ? "meizi" : "text", Snackbar.LENGTH_SHORT).show();
            }
        });
        mMeiziWallView.addOnScrollListener(getMeiziWallOnScroll(layoutManager));

    }

    private RecyclerView.OnScrollListener getMeiziWallOnScroll(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = layoutManager.findLastVisibleItemPositions(null)[1] >= mMeiziList.size() - 1;
                Log.d(TAG, "isRefresh() : " + isRefreshing() + "   isBottom():" + isBottom);
                if (!isRefreshing() && isBottom) {
                    mLoadPage++;
                    loadServiceData(false);
                }
            }
        };
    }

    private void loadLocalData() {

    }

    /**
     * 加载服务器数据
     *
     * @param clearCache true 重新加载服务器数据
     *                   false 加载下一页数据
     */
    private void loadServiceData(final boolean clearCache) {
        setRefresh(true);
        GankApi gankApi = GankRetrofit.getGankService();
        Call<GankDataResponse<Meizi>> call = gankApi.getMeizhi(GankApi.DEFAULT_COUNT, mLoadPage);
        call.enqueue(new Callback<GankDataResponse<Meizi>>() {
            @Override
            public void onResponse(Call<GankDataResponse<Meizi>> call, Response<GankDataResponse<Meizi>> response) {
                if (clearCache) {
                    mMeiziList.clear();
                }
                mMeiziList.addAll(response.body().results);
                mMeiziAdapter.notifyDataSetChanged();
                setRefresh(false);
                Log.w(TAG, "onResponse(): mMeiziList.size():" + mMeiziList.size());
            }

            @Override
            public void onFailure(Call<GankDataResponse<Meizi>> call, Throwable t) {
                Log.d(TAG, "onFailure()" + t.getClass() + " ");
                setRefresh(false);
                Snackbar.make(mMeiziWallView, "加载失败,请重试", Snackbar.LENGTH_INDEFINITE)
                        .setAction("重试", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadServiceData(false);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void loadData() {
        mLoadPage = 1;
        loadServiceData(true);
    }

    @Override
    public void onToolbarDoubleClick() {
        mMeiziWallView.smoothScrollToPosition(0);
    }
}

