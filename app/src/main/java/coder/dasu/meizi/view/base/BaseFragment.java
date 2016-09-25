package coder.dasu.meizi.view.base;

import android.support.v4.app.Fragment;

import coder.dasu.meizi.MeiziApp;
import coder.dasu.meizi.data.dao.DaoSession;

/**
 * Created by sxq on 2016/9/13.
 */
public abstract class BaseFragment extends Fragment {

    protected static DaoSession mDaoSession = MeiziApp.getDaoSession();

}
