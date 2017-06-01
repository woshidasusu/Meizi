package com.dasu.ganhuo.ui.history;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.history.SomedayController;
import com.dasu.ganhuo.mode.logic.history.SomedayEntity;
import com.dasu.ganhuo.ui.base.ActivityStack;
import com.dasu.ganhuo.ui.base.BaseActivity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.home.WebViewActivity;
import com.dasu.ganhuo.ui.view.recyclerview.BaseRecyclerView;
import com.dasu.ganhuo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/27.
 */

public class SomedayActivity extends BaseActivity{
    private static final String TAG = SomedayActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_someday);
        initVariable();
        initView();
        mSomedayController.parseContent(mContent);
        mSomedayController.parseOthers(mTitle, mPublishDate);
    }

    private String mTitle;
    private String mContent;
    private String mSourceUrl;
    private long mPublishDate;
    private SomedayController mSomedayController;
    private List<SomedayEntity> mSomedayList;
    private SomedayRecycleAdapter mRecycleAdapter;

    private void initVariable() {
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra("_title");
            mContent = intent.getStringExtra("_content");
            mPublishDate = intent.getLongExtra("_publish_date", TimeUtils.getCurTimeMills());
        }
        mSomedayController = new SomedayController(this);
        mSomedayList = new ArrayList<>();
        mRecycleAdapter = new SomedayRecycleAdapter(mSomedayList);
    }
    private BaseRecyclerView mRecyclerView;
    private TextView mSubTitleTv;
    private AppBarLayout mAppBarLayout;
    private TextView mJumpSourceTv;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSubTitleTv = (TextView) findViewById(R.id.tv_someday_subtitle);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.layout_appbar);
        mJumpSourceTv = (TextView) findViewById(R.id.tv_someday_jump_source);
        mRecyclerView = (BaseRecyclerView) findViewById(R.id.rv_someday_content);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar_layout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnItemClickListener(onItemClick());
        mJumpSourceTv.setOnClickListener(onJumpSourceClick());
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

    private OnItemClickListener<String> onItemClick() {
        return new OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String data, int position) {
                WebViewActivity.startActivity(mContext, data, "网页");
            }
        };
    }

    public void updateSomeday(List<SomedayEntity> data) {
        if (mSomedayList == null) {
            mSomedayList = new ArrayList<>();
        }
        mSomedayList.clear();
        mSomedayList.addAll(data);
        mRecycleAdapter.notifyDataSetChanged();
    }

    public void updateSubTitle(String subTitle) {
        if (mSubTitleTv != null && mAppBarLayout != null) {
            mAppBarLayout.setExpanded(true, true);
            mSubTitleTv.setText(subTitle);
        }
    }

    public void updateTitle(String title) {
        if (mCollapsingToolbarLayout != null) {
            mCollapsingToolbarLayout.setTitle(title);
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

    public static void startActivity(Context context, String title, String content, long publishDate) {
        Intent intent = new Intent(context, SomedayActivity.class);
        intent.putExtra("_title", title);
        intent.putExtra("_content", content);
        intent.putExtra("_publish_date", publishDate);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityStack.getInstance().popAndFinishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
