package coder.dasu.meizi.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import coder.dasu.meizi.R;
import coder.dasu.meizi.listener.IMainAF;
import coder.dasu.meizi.view.base.BaseActivity;
import coder.dasu.meizi.view.base.SwipeRefreshFragment;

public class MainActivity extends BaseActivity implements IMainAF{

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initVariable();
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_main;
    }

    public void initVariable() {
        mFragmentList = new ArrayList<>();
        mFragmentList.addAll(getSupportFragmentManager().getFragments());

    }

    @Override
    public void loadData() {
        FragmentManager manager = getSupportFragmentManager();
        for (Fragment fragment : manager.getFragments()) {
            if (fragment instanceof SwipeRefreshFragment) {
                ((SwipeRefreshFragment) fragment).loadData();
            }
        }
    }
}
