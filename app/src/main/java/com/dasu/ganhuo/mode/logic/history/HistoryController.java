package com.dasu.ganhuo.mode.logic.history;

import android.content.Context;

import com.dasu.ganhuo.mode.logic.home.HtmlDataEntity;
import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.history.HistoryActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.List;

/**
 * Created by suxq on 2017/4/18.
 */

public class HistoryController {
    private static final String TAG = HistoryController.class.getSimpleName();

    private Context mContext;
    private HistoryActivity mHistoryActivity;

    public HistoryController(Context context) {
        if (!(context instanceof HistoryActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mHistoryActivity = (HistoryActivity) context;
    }

    public void loadBaseData() {
        mHistoryPage = 1;
        GankController.getHistoryHtmlData(1, new RetrofitListener<List<HtmlDataEntity>>() {
            @Override
            public void onSuccess(List<HtmlDataEntity> data) {
                mHistoryActivity.updateHistory(data);
            }

            @Override
            public void onError(String description) {

            }
        });
    }

    private static final int STATE_REFRESH_END = 1;
    private static final int STATE_REFRESHING = 2;

    private int mRefreshState = STATE_REFRESH_END;
    private int mHistoryPage = 1;

    public void startPullUpRefresh() {
        if (mRefreshState == STATE_REFRESHING) {
            return;
        }
        mRefreshState = STATE_REFRESHING;
        GankController.getHistoryHtmlData(++mHistoryPage, new RetrofitListener<List<HtmlDataEntity>>() {
            @Override
            public void onSuccess(List<HtmlDataEntity> data) {
                mRefreshState = STATE_REFRESH_END;
                if (data != null && data.size() > 0) {
                    mHistoryActivity.refreshHistory(data);
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
