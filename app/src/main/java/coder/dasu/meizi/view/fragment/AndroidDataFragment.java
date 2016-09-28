package coder.dasu.meizi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.bean.Data;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.utils.ListUtils;
import coder.dasu.meizi.view.adapter.AndroidDataAdapter;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class AndroidDataFragment extends GankDataFragment {

    private static final String TAG = AndroidDataFragment.class.getSimpleName();

    private AndroidDataAdapter mDataAdapter;
    private Context mContext;

    @InjectView(R.id.rv_android)
    RecyclerView mAndroidDataView;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.inject(this, view);
        bindWidgets();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isRefreshing()) {
                setRefresh(true);
                return;
            }
            if (ListUtils.isEmpty(mDataList)) {
                setRefresh(true);
                loadServiceData(false);
            }
        } else {
            setRefresh(false);
        }
    }

    private void bindWidgets() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAndroidDataView.setLayoutManager(manager);
        mDataAdapter = new AndroidDataAdapter(getActivity(), mDataList);
        mAndroidDataView.setAdapter(mDataAdapter);

        mDataAdapter.setOnItemClickListener(getOnItemClick());
    }

    @Override
    public void onToolbarDoubleClick() {
        super.onToolbarDoubleClick();
        if (ListUtils.isEmpty(mDataList)) {
            return;
        }
        mAndroidDataView.smoothScrollToPosition(0);
    }

    @Override
    protected void onLoadServiceDataSuccess() {
        super.onLoadServiceDataSuccess();
        mDataAdapter.notifyDataSetChanged();
    }

    public OnItemClickListener getOnItemClick() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Data data) {
                Snackbar.make(view, data.getWho(), Snackbar.LENGTH_SHORT).show();
            }
        };
    }
}
