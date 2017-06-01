package com.dasu.ganhuo.mode.logic.reading;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.dasu.ganhuo.mode.okhttp.ReadingController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.reading.ReadingFragment;
import com.dasu.ganhuo.utils.LogUtils;

/**
 * Created by dasu on 2017/4/22.
 */

public class ReadingFController {

    private static final String TAG = ReadingFController.class.getSimpleName();

    private Context mContext;
    private ReadingFragment mReadingFragment;
    private String mReadingType;
    private int mCurPage;

    public ReadingFController(Fragment fragment) {
        if (!(fragment instanceof ReadingFragment)) {
            LogUtils.e(TAG, TAG + "绑定错误的Fragment");
            throw new UnsupportedOperationException(TAG + "绑定错误的Fragment");
        }
        mContext = fragment.getContext();
        mReadingFragment = (ReadingFragment) fragment;
        mReadingType = mReadingFragment.getReadingType();
        if (TextUtils.isEmpty(mReadingType)) {
            LogUtils.e(TAG, "ReadingFragment 必须实现IReadingType接口，指定返回某一type类型");
            throw new UnsupportedOperationException("ReadingFragment 必须实现IReadingType接口，指定返回某一type类型");
        }
    }

    public void loadBaseData() {
        mCurPage = 1;
        ReadingController.getSpecifyType(mReadingType, 1, new RetrofitListener<ReadingEntity>() {

            @Override
            public void onSuccess(ReadingEntity data) {
                mReadingFragment.updateBlogs(data.getBlogEntitys());
                mReadingFragment.updatePages(data.getPages());
                mReadingFragment.updateCurPage("1");
            }

            @Override
            public void onError(String description) {
                mReadingFragment.onLoadFailed();
            }
        });
    }

    public void loadPageData(int page) {
        mCurPage = page;
        ReadingController.getSpecifyType(mReadingType, page, new RetrofitListener<ReadingEntity>() {
            @Override
            public void onSuccess(ReadingEntity data) {
                mReadingFragment.updateBlogs(data.getBlogEntitys());
                mReadingFragment.updateCurPage(String.valueOf(mCurPage));
            }

            @Override
            public void onError(String description) {
                mReadingFragment.onLoadFailed();
            }
        });
    }


}
