package coder.dasu.meizi.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coder.dasu.meizi.MeiziApp;
import coder.dasu.meizi.data.dao.DaoSession;

/**
 * Created by dasu on 2016/9/13.
 * https://github.com/woshidasusu/Meizi
 */
public abstract class BaseFragment extends Fragment {

    protected static DaoSession mDaoSession = MeiziApp.getDaoSession();

    protected View rootView;

    public abstract String getTAG();

    public BaseFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(getTAG(), " -> onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(getTAG(), " -> onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(getTAG(), " -> onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(getTAG(), " -> onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(getTAG(), " -> onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(getTAG(), " -> onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(getTAG(), " -> onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(getTAG(), " -> onStop()");
    }

    /**
     * 已经有父容器的view不能再添加到其他容器中去，所以必须得先移除它的容器才能再添加。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(getTAG(), " -> onDestroyView()");
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(getTAG(), " -> onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(getTAG(), " -> onDetach()");
    }
}
