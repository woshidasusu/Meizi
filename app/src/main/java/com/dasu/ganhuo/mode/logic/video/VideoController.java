package com.dasu.ganhuo.mode.logic.video;

import android.content.Context;

import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.video.VideoActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.List;

/**
 * Created by dasu on 2017/4/18.
 */

public class VideoController {
    private static final String TAG = VideoController.class.getSimpleName();

    private Context mContext;
    private VideoActivity mVideoActivity;

    public VideoController(Context context) {
        if (!(context instanceof VideoActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mVideoActivity = (VideoActivity) context;
    }

    private final String VIDEO_TYPE = GankController.TYPE_VIDEO;

    public void loadBaseData() {
        mVideoPage = 1;
        GankController.getSpecifyGanHuo(VIDEO_TYPE, 1, new RetrofitListener<List<GanHuoEntity>>() {
            @Override
            public void onSuccess(List<GanHuoEntity> data) {
                mVideoActivity.updateVideo(data);
            }

            @Override
            public void onError(String description) {

            }
        });
    }

    private static final int STATE_REFRESH_END = 1;
    private static final int STATE_REFRESHING = 2;

    private int mRefreshState = STATE_REFRESH_END;
    private int mVideoPage = 1;

    public void startPullUpRefresh() {
        if (mRefreshState == STATE_REFRESHING) {
            return;
        }
        mRefreshState = STATE_REFRESHING;
        GankController.getSpecifyGanHuo(VIDEO_TYPE, ++mVideoPage, new RetrofitListener<List<GanHuoEntity>>() {
            @Override
            public void onSuccess(List<GanHuoEntity> data) {
                mRefreshState = STATE_REFRESH_END;
                if (data != null && data.size() > 0) {
                    mVideoActivity.refreshVideo(data);
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
