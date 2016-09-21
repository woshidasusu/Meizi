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
import coder.dasu.meizi.view.adapter.MeizhiWallAdapter;
import coder.dasu.meizi.view.base.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    @InjectView(R.id.rv_meizi_photo_wall)
    RecyclerView mMeiziWallView;

    private MeizhiWallAdapter mMeiziAdapter;
    private Context mContext;
    private IMainAF mControler;

    private int mLoadPage;
    private List<Meizi> mMeizhiList;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof IMainAF)){
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
        loadLocalData();
        loadServiceData();
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
    }

    private void initView() {
        mMeizhiList = new ArrayList<>();
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMeiziWallView.setLayoutManager(layoutManager);
        mMeiziAdapter = new MeizhiWallAdapter(mContext, mMeizhiList);
        mMeiziWallView.setAdapter(mMeiziAdapter);
        mMeiziAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Meizi meizhi) {
                Snackbar.make(view, view == picture ? "meizhi" : "text", Snackbar.LENGTH_SHORT).show();
            }
        });
        mMeiziWallView.addOnScrollListener(getMeiziWallOnScroll(layoutManager));

    }

    private RecyclerView.OnScrollListener getMeiziWallOnScroll(final StaggeredGridLayoutManager layoutManager){
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = layoutManager.findLastVisibleItemPositions(null)[1] >= mMeizhiList.size() - 1;
                if (isBottom) {
                    mLoadPage++;
                    loadServiceData();
                }
            }
        };
    }

    private void loadLocalData() {

    }

    private void loadServiceData() {
        GankApi gankApi = GankRetrofit.getGankService();
        Call<GankDataResponse<Meizi>> call = gankApi.getMeizhi(10,mLoadPage);
        call.enqueue(new Callback<GankDataResponse<Meizi>>() {
            @Override
            public void onResponse(Call<GankDataResponse<Meizi>> call, Response<GankDataResponse<Meizi>> response) {
                mMeizhiList.addAll(response.body().results);
                mMeiziAdapter.notifyDataSetChanged();
                Log.d("!!!!!","success");
            }

            @Override
            public void onFailure(Call<GankDataResponse<Meizi>> call, Throwable t) {

            }
        });
    }

}

