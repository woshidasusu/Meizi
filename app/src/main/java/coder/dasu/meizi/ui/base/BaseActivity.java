package coder.dasu.meizi.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import coder.dasu.meizi.R;

/**
 * Created by dasu on 2016/8/25.
 * https://github.com/woshidasusu/Meizi
 *
 * 1、提供唯一的 DaoSession数据库访问接口
 */
public abstract class BaseActivity extends AppCompatActivity {

    private long mFirstClickTime;

    @InjectView(R.id.toolbar)
    public Toolbar mToolbar;
    @InjectView(R.id.collaps_toolbar)
    public CollapsingToolbarLayout mCollapsToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());
        ButterKnife.inject(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mCollapsToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClickListener() {
        long secClick = System.currentTimeMillis();
        if ((secClick - mFirstClickTime) < 1000) {
            onToolbarDoubleClick();
        }
        mFirstClickTime = secClick;
    }

    protected void onToolbarDoubleClick(){}

    public abstract int provideContentView();

}
