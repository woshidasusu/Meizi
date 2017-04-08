package com.dasu.gank.ui.base;

import android.content.Context;

/**
 * Created by dasu on 2016/9/25.
 * https://github.com/woshidasusu/Meizi
 *
 */
public abstract class SwipeRefreshFragment extends BaseFragment {

    private ISwipeRefreshListener mRefreshProxy;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof ISwipeRefreshListener)) {
            throw new UnsupportedOperationException(context.getClass().getSimpleName() + " 没有实现 ISwipeRefreshListener 接口");
        }
        mRefreshProxy = (ISwipeRefreshListener) context;
    }

    protected void setRefresh(boolean enable) {
        if (mRefreshProxy != null) {
            mRefreshProxy.setRefresh(enable);
        }
    }

    protected boolean isRefreshing() {
        if (mRefreshProxy != null) {
            return mRefreshProxy.isRefreshing();
        }
        return false;
    }

    protected void dismissAnimation() {
        if (mRefreshProxy != null) {
            mRefreshProxy.dismissAnimation();
        }
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当触发下拉刷新手势时会回调该方法
     */
    public abstract void loadData();

}
