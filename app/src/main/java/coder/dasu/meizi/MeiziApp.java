package coder.dasu.meizi;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.database.Database;

import coder.dasu.meizi.data.dao.DaoMaster;
import coder.dasu.meizi.data.dao.DaoSession;

/**
 * Created by dasu on 2016/8/25.
 */
public class MeiziApp extends Application {

    private static final String TAG = "MeiziApp";

    private static DaoSession mDaoSession;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.with(getApplicationContext())
                .setDebugging(true);

        mContext = getApplicationContext();
        initGreenDao();
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

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
