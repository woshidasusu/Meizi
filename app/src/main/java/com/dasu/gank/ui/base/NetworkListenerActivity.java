package com.dasu.gank.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.gank.R;
import com.dasu.gank.mode.net.receiver.NetBroadcastReceiver;
import com.dasu.gank.mode.net.receiver.NetStateListener;
import com.dasu.gank.utils.NetworkUtils;

import static android.provider.Settings.ACTION_WIRELESS_SETTINGS;

/**
 * Created by dasu on 2017/4/10.
 *
 */

public abstract class NetworkListenerActivity extends BaseActivity implements NetStateListener {
    private View mNoNetworkTipView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetBroadcastReceiver.addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetBroadcastReceiver.removeListener(this);
    }

    protected void addNoNetworkTipView(ViewGroup viewGroup) {
        mNoNetworkTipView = LayoutInflater.from(this).inflate(R.layout.view_no_network_tip, null);
        ViewGroup mainGroup = viewGroup;
        mNoNetworkTipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNetworkSetting();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (mainGroup != null) {
            if (isNetworkAvailable()) {
                mNoNetworkTipView.setVisibility(View.GONE);
            } else {
                mNoNetworkTipView.setVisibility(View.VISIBLE);
            }
            mainGroup.addView(mNoNetworkTipView, 0, params);
        }
    }

    private void openNetworkSetting() {
        Intent intent = new Intent(ACTION_WIRELESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void onNetChanged(boolean isConnected) {
        if (mNoNetworkTipView == null) {
            return;
        }
        if (isConnected) {
            mNoNetworkTipView.setVisibility(View.GONE);
        } else {
            mNoNetworkTipView.setVisibility(View.VISIBLE);
        }
    }

    protected boolean isNetworkAvailable() {
        return NetworkUtils.isConnected(this);
    }
}
