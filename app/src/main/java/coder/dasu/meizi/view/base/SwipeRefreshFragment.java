package coder.dasu.meizi.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.listener.ISwipeRefreshListener;

/**
 * Created by dasu on 2016/9/25.
 * https://github.com/woshidasusu/Meizi
 *
 * 1、提供SwipeRefresh功能，通过{@link ISwipeRefreshListener#setRefresh(boolean)} 来 启动/关闭 加载动画
 *   启动加载动画时，会触发{@link ISwipeRefreshListener#loadData()} 方法，在子类里重写该方法实现具体加载任务
 */
public abstract class SwipeRefreshFragment extends BaseFragment implements ISwipeRefreshListener{

    public SwipeRefreshFragment() {

    }

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.inject(this, getView());
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.deeppink),
                    getResources().getColor(R.color.tomato),
                    getResources().getColor(R.color.red));
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData();
                }
            });
        }
    }

    @Override
    public void setRefresh(boolean enable) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (mSwipeRefreshLayout.isRefreshing() && enable) {
            return;
        }
        if (!mSwipeRefreshLayout.isRefreshing() && !enable) {
            return;
        }
        if (!enable) {
            //手动延长刷新时间
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 5000);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public boolean isRefreshing(){
        return mSwipeRefreshLayout.isRefreshing();
    }

}
