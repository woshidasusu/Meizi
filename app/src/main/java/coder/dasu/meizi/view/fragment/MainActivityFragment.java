package coder.dasu.meizi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.Meizhi;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.view.adapter.MeizhiWallAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @InjectView(R.id.rv_meizi_photo_wall)
    RecyclerView mMeiziWallView;

    private List<Meizhi> mMeizhiList;
    private Context mContext;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
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
    }

    private void loadData() {
        mMeizhiList = new ArrayList<>();
        mMeizhiList.add(new Meizhi("http://", "asdg1", R.drawable.meizhi1));
        mMeizhiList.add(new Meizhi("http://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg", "asdg2", R.drawable.meizhi1));
        mMeizhiList.add(new Meizhi("http://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg", "asdg2", R.drawable.meizhi1));
        mMeizhiList.add(new Meizhi("http://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg", "asdg2", R.drawable.meizhi1));
        mMeizhiList.add(new Meizhi(null, "asdg3", R.drawable.meizhi1));
        mMeizhiList.add(new Meizhi(null, "asdg4", R.drawable.meizhi1));
        for (int i=0;i<1000;i++){
            mMeizhiList.add(new Meizhi("http://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg", "妹纸编号 " + i, R.drawable.meizhi1));
        }
    }

    private void initView() {
        mMeiziWallView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        MeizhiWallAdapter adapter = new MeizhiWallAdapter(mContext, mMeizhiList);
        mMeiziWallView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, View picture, View text, Meizhi meizhi) {
                Snackbar.make(view, view == picture ? "meizhi" : "text", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}

