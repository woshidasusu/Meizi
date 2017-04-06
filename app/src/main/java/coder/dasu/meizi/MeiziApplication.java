package coder.dasu.meizi;

import android.app.Application;
import android.content.Context;

import org.greenrobot.greendao.database.Database;

import coder.dasu.meizi.mode.dao.DaoMaster;
import coder.dasu.meizi.mode.dao.DaoSession;
import coder.dasu.meizi.utils.SPUtils;


/**
 * Created by dasu on 2016/8/25.
 */
public class MeiziApplication extends Application {

    private static final String TAG = "MeiziApplication";

    private static DaoSession mDaoSession;
    private static Context mContext;

    private static SPUtils mConfigSP;

    private String spConfigName = "config";

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        initGreenDao();
        mConfigSP = new SPUtils(mContext, spConfigName);
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(mContext, "meizhi.db");
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }

    public static Context getAppContext(){
        return mContext;
    }

    public static SPUtils getConfigSP() {
        return mConfigSP;
    }

}
