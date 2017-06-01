package com.dasu.ganhuo.mode.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dasu.ganhuo.utils.NetworkUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dasu on 2017/4/7.
 * https://github.com/woshidasusu/Meizi
 *
 * 手机网络状态变化的监听
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    /**
     * 系统关于网络变化发出的广播action
     */
    public static final String NET_CHANGED_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    /**
     * 支持并发读，为什么选择使用这个，因为这个支持在读的过程中对 list 进行修改，
     * 它是线程安全的
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
