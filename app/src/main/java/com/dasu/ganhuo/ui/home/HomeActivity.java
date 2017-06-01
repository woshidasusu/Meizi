package com.dasu.ganhuo.ui.home;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.mode.logic.home.HomeController;
import com.dasu.ganhuo.mode.logic.home.SomedayGanHuoEntity;
import com.dasu.ganhuo.ui.base.DrawerActivity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.view.Error404View;
import com.dasu.ganhuo.ui.view.recyclerview.BaseRecyclerView;

/**
 * 今日推荐页面，只负责界面数据的展示，业务逻辑交由{@link HomeController} 负责
 * 双方互相持有引用，可直接交互
 * 建议Activity不主动从Controller拿数据，只能被动获取
 */
public class HomeActivity extends DrawerActivity implements OnItemClickListener<GanHuoEntity> {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected int bindMenuId() {
        return MENU_HOME;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVariable();
        initView();
        showLoadingView(mContentLayout);
        mHomeController.loadBaseData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //今日的干货数据
    private SomedayGanHuoEntity mSomedayGanHuo;
    private HomeController mHomeController;
    private String mSourceUrl;

    private void initVariable() {
        mSomedayGanHuo = new SomedayGanHuoEntity();
        mHomeController = new HomeController(this);
    }

    private BaseRecyclerView mGanhuoRv;
    private HomeRecycleAdapter mRecycleAdapter;
    private TextView mSubTitleTv;
    private AppBarLayout mAppBarLayout;
    private TextView mJumpSourceTv;
    private Error404View mError404View;
    private ViewGroup mContentLayout;

    private void initView() {
        //添加 toolbar
        addToolbar((Toolbar)findViewById(R.id.toolbar));
        //init view
        mAppBarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        mSubTitleTv = (TextView) findViewById(R.id.tv_home_subtitle);
        mJumpSourceTv = (TextView) findViewById(R.id.tv_home_jump_source);
        mGanhuoRv = (BaseRecyclerView) findViewById(R.id.rv_home_content);
        mError404View = (Error404View) findViewById(R.id.view_home_error_404);
        mContentLayout = (ViewGroup) findViewById(R.id.layout_content);
        mGanhuoRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleAdapter = new HomeRecycleAdapter(mSomedayGanHuo);
        mGanhuoRv.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnItemClickListener(this);
        mJumpSourceTv.setOnClickListener(onJumpSourceClick());
        mError404View.setOnRetryClickListener(onRetryClick());
    }

    private Error404View.OnRetryClickListener onRetryClick() {
        return new Error404View.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                showLoadingView(mContentLayout);
                mHomeController.retryLoad();
            }
        };
    }

    private View.OnClickListener onJumpSourceClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mSourceUrl)) {
                    WebViewActivity.startActivity(mContext, mSourceUrl, "Gank.io");
                }
            }
        };
    }

    /**
     * 与HomeController交互的接口，更新今日的干货列表
     *
     * @param somedayGanHuo
     */
    public void updateGanhuoRv(SomedayGanHuoEntity somedayGanHuo) {
        removeLoadingView();
        mError404View.setVisibility(View.GONE);
        mGanhuoRv.setVisibility(View.VISIBLE);
        if (somedayGanHuo == null) {
            mRecycleAdapter.setEmptyTip("今天干货还没有出来哦\r\n先到其他栏目逛逛吧");
        } else {
            mSomedayGanHuo = somedayGanHuo;
            mRecycleAdapter.setData(mSomedayGanHuo);
        }
    }

    public void update404() {
        removeLoadingView();
        mGanhuoRv.setVisibility(View.GONE);
        mError404View.setVisibility(View.VISIBLE);
    }

    public void updateSubTitle(String subTitle) {
        if (mSubTitleTv != null && mAppBarLayout != null) {
            mAppBarLayout.setExpanded(true, true);
            mSubTitleTv.setText(subTitle);
        }
    }

    public void updateSourceUrl(String sourceUrl) {
        if (mJumpSourceTv != null) {
            mJumpSourceTv.setVisibility(View.VISIBLE);
            //设置下划线
            mJumpSourceTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            mJumpSourceTv.getPaint().setAntiAlias(true);
        }
        mSourceUrl = sourceUrl;
    }

    @Override
    public void onItemClick(View view, GanHuoEntity data, int position) {
        WebViewActivity.startActivity(mContext, data.getUrl(), data.getDesc());
    }
}
