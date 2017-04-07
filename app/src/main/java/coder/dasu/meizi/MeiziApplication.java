package coder.dasu.meizi;

import android.app.Application;
import android.content.IntentFilter;

import org.greenrobot.greendao.database.Database;

import coder.dasu.meizi.mode.dao.DaoMaster;
import coder.dasu.meizi.mode.dao.DaoSession;
import coder.dasu.meizi.mode.net.receiver.NetBroadcastReceiver;
import coder.dasu.meizi.utils.SPUtils;


/**
 * Created by dasu on 2016/8/25.
 */
public class MeiziApplication extends Application {

    private static final String TAG = "MeiziApplication";

    private static DaoSession mDaoSession;

    private static SPUtils mConfigSP;

    private NetBroadcastReceiver mNetBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDao();
        mConfigSP = new SPUtils(getApplicationContext(), "config");
        registerNetStateListener();
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterNetStateListener();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }

    public static SPUtils getConfigSP() {
        return mConfigSP;
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "meizhi.db");
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    private void registerNetStateListener() {
        IntentFilter filter = new IntentFilter(NetBroadcastReceiver.NET_CHANGED_ACTION);
        mNetBroadcastReceiver = new NetBroadcastReceiver();
        registerReceiver(mNetBroadcastReceiver, filter);
    }

    private void unregisterNetStateListener() {
        if (mNetBroadcastReceiver != null) {
            unregisterReceiver(mNetBroadcastReceiver);
            mNetBroadcastReceiver = null;
        }
    }
}
