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

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.bean.Data;
import coder.dasu.meizi.listener.IMainAF;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.utils.ListUtils;
import coder.dasu.meizi.view.adapter.MeiziWallAdapter;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class MeiziDataFragment extends GankDataFragment {

    private static final String TAG = MeiziDataFragment.class.getSimpleName();

    private MeiziWallAdapter mMeiziAdapter;

    private Context mContext;
    private IMainAF mControler;

    @InjectView(R.id.rv_meizi_photo_wall)
    RecyclerView mMeiziWallView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meizi, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }



    public MeiziDataFragment(String type) {
        mType = type;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (ListUtils.isEmpty(mDataList)) {
                setRefresh(true);
                loadServiceData(false);
            }
        }
    }

    private void initView() {
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mMeiziWallView.setLayoutManager(layoutManager);
        mMeiziAdapter = new MeiziWallAdapter(mContext, mDataList);
        mMeiziWallView.setAdapter(mMeiziAdapter);
        mMeiziAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Data meizhi) {
                Snackbar.make(view, view == picture ? "meizi" : "text", Snackbar.LENGTH_SHORT).show();
            }
        });
        mMeiziWallView.addOnScrollListener(getMeiziWallOnScroll(layoutManager));

    }

    private RecyclerView.OnScrollListener getMeiziWallOnScroll(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = layoutManager.findLastVisibleItemPositions(null)[1] >= mDataList.size() - 1;
                Log.d(TAG, "isRefresh() : " + isRefreshing() + "   isBottom():" + isBottom);
                if (!isRefreshing() && isBottom) {
                    loadNextPageData();
                }
            }
        };
    }

    public synchronized void loadNextPageData() {
        //TODO 加载失败时，loadpager不应该+1
        mLoadPage++;
        setRefresh(true);
        loadServiceData(false);
    }

    @Override
    protected void onLoadServiceDataSuccess() {
        mMeiziAdapter.notifyDataSetChanged();
    }



    @Override
    public void onToolbarDoubleClick() {
        mMeiziWallView.smoothScrollToPosition(0);
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
