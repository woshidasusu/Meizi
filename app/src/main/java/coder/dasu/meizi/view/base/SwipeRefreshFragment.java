package coder.dasu.meizi.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by sxq on 2016/9/25.
 */

public abstract class SwipeRefreshFragment extends BaseFragment{

    public SwipeRefreshFragment() {

    }

    public abstract void loadData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setRefreshEnable(true);
        }
    }

    public void setRefresh(boolean isRefresh) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setRefresh(isRefresh);
        }
    }

    public boolean isRefreshing() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).isRefreshing();
        }
        return false;
    }
}
