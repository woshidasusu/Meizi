package coder.dasu.meizi.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;

/**
 * Created by dasu on 2016/8/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements ISwipeRefreshListener {

    private static final String TAG = "BaseActivity";

    @InjectView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());

        ButterKnife.inject(this);
    }

    public abstract int provideContentView();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initSwipeRefresh();
    }


    private void initSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            Log.d("!!!!!!!!!!!","asdf");
            mSwipeRefreshLayout.setColorSchemeColors(R.color.deeppink, R.color.tomato, R.color.red);
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
}
