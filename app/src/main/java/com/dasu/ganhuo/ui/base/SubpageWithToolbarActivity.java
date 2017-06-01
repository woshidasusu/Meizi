package com.dasu.ganhuo.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.ganhuo.R;

/**
 * Created by dasu on 2017/4/20.
 * 带有toolbar的二级页面，即toolbar有返回icon
 */

public abstract class SubpageWithToolbarActivity extends BaseActivity{

    private ViewGroup mContentView;

    public abstract String getToolbarTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getToolbarTitle());
        }
    }

    private void initContentView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        View rootView = LayoutInflater.from(this).inflate(R.layout.base_activity_subpage, null);
        viewGroup.addView(rootView);
        mContentView = (ViewGroup) findViewById(R.id.layout_content);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, mContentView, true);
    }

    @Override
    public void setContentView(View view) {
        mContentView.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentView.addView(view, params);
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

    protected void showLoadingView() {
        showLoadingView(mContentView);
    }
}
