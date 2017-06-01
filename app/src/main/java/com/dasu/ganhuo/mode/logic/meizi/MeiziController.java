package com.dasu.ganhuo.mode.logic.meizi;

import android.content.Context;

import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.meizi.MeiziActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.List;

/**
 * Created by dasu on 2017/4/18.
 */

public class MeiziController {
    private static final String TAG = MeiziController.class.getSimpleName();

    private Context mContext;
    private MeiziActivity mMeiziActivity;

    public MeiziController(Context context) {
        if (!(context instanceof MeiziActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mMeiziActivity = (MeiziActivity) context;
    }

    private final String MEIZI_TYPE = GankController.TYPE_MEIZI;

    public void loadBaseData() {
        mMeiziPage = 1;
        GankController.getSpecifyGanHuo(MEIZI_TYPE, 1, new RetrofitListener<List<GanHuoEntity>>() {
            @Override
            public void onSuccess(List<GanHuoEntity> data) {
                mMeiziActivity.updateMeizi(data);
            }

            @Override
            public void onError(String description) {

            }
        });
    }

    private static final int STATE_REFRESH_END = 1;
    private static final int STATE_REFRESHING = 2;

    private int mRefreshState = STATE_REFRESH_END;
    private int mMeiziPage = 1;

    public void startPullUpRefresh() {
        //防止重复发起请求
        if (mRefreshState == STATE_REFRESHING) {
            return;
        }
        mRefreshState = STATE_REFRESHING;
        GankController.getSpecifyGanHuo(MEIZI_TYPE, ++mMeiziPage, new RetrofitListener<List<GanHuoEntity>>() {
            @Override
            public void onSuccess(List<GanHuoEntity> data) {
                mRefreshState = STATE_REFRESH_END;
                if (data != null && data.size() > 0) {
                    mMeiziActivity.refreshMeizi(data);
                } else {
                    //todo 加载失败，activity是否应该开个接口显示
                }
            }

            @Override
            public void onError(String description) {
                mRefreshState = STATE_REFRESH_END;
            }
        });
    }

}
