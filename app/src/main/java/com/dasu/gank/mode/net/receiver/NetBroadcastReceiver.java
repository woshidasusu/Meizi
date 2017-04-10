package com.dasu.gank.mode.net.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dasu.gank.utils.NetworkUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dasu on 2017/4/7.
 * https://github.com/woshidasusu/Meizi
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    /**
     * 系统关于网络变化发出的广播action
     */
    public static final String NET_CHANGED_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    /**
     * 支持并发读
     */
    private static CopyOnWriteArrayList<NetStateListener> mListeners = new CopyOnWriteArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGED_ACTION)) {
            for (NetStateListener listener : mListeners) {
                listener.onNetChanged(NetworkUtils.isConnected(context.getApplicationContext()));
            }
        }
    }

    public static synchronized void addListener(NetStateListener listener) {
        if (mListeners.contains(listener)) {
            return;
        }
        mListeners.add(listener);
    }

    public static synchronized void removeListener(NetStateListener listener) {
        mListeners.remove(listener);
    }

}
