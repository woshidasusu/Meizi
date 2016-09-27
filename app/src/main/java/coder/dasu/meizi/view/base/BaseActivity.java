package coder.dasu.meizi.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.MeiziApp;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.dao.DaoSession;
import coder.dasu.meizi.listener.ISwipeRefreshListener;

/**
 * Created by dasu on 2016/8/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements ISwipeRefreshListener {

    private static final String TAG = "BaseActivity";

    protected static DaoSession mDaoSession = MeiziApp.getDaoSession();

    protected boolean mRefreshEnable = false;

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());

        ButterKnife.inject(this);
        initSwipeRefresh();
    }

    public abstract int provideContentView();

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

            mSwipeRefreshLayout.setEnabled(mRefreshEnable);
        }
    }

    public void setRefreshEnable(boolean enable) {
        mSwipeRefreshLayout.setEnabled(enable);
    }

    @Override
    public void setRefresh(boolean enable) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!enable) {
            //手动延长刷新时间
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public boolean isRefreshing(){
        return mSwipeRefreshLayout.isRefreshing();
    }
}
