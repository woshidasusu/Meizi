package com.dasu.ganhuo.mode.logic.load;

import android.content.Context;

import com.dasu.ganhuo.mode.logic.base.GankSp;
import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.load.LoadActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.List;

/**
 * Created by dasu on 2017/4/27.
 */

public class LoadController {
    private static final String TAG = LoadController.class.getSimpleName();

    private Context mContext;
    private LoadActivity mLoadActivity;

    public LoadController(Context context) {
        if (!(context instanceof LoadActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mLoadActivity = (LoadActivity) context;
    }

    public void loadBaseData() {
        GankController.getPublishDate(new RetrofitListener<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                LogUtils.d(TAG, "发布过干货的总天数: " + (data != null ? data.size() : "null"));
                int counts = GankSp.getGankDateCounts(mContext);
                if (data != null && counts < data.size()) {
                    GankSp.putGankDateCounts(mContext, data.size());
                }
                mLoadActivity.onLoadFinish();
            }

            @Override
            public void onError(String description) {
                mLoadActivity.onLoadFinish();
            }
        });
    }

}
