package com.dasu.ganhuo.mode.logic.reading;

import android.content.Context;

import com.dasu.ganhuo.mode.okhttp.ReadingController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.reading.ReadingActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.List;


/**
 * Created by dasu on 2017/4/22.
 *
 * ReadingActivity对应的Controller，用于获取闲读所有栏目分类名称等逻辑处理
 */

public class ReadingAController {
    private static final String TAG = ReadingController.class.getSimpleName();

    private Context mContext;
    private ReadingActivity mReadingActivity;

    public ReadingAController(Context context) {
        if (!(context instanceof ReadingActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mReadingActivity = (ReadingActivity) context;
    }

    public void getReadingTypes() {
        ReadingController.getReadingTypes(new RetrofitListener<List<TypeEntity>>() {
            @Override
            public void onSuccess(List<TypeEntity> data) {
                mReadingActivity.updateTypes(data);
            }

            @Override
            public void onError(String description) {

            }
        });
    }

//    /**
//     * 获取默认的闲读分类
//     *
//     * @return
//     */
//    public List<TypeEntity> getDefaultTypes() {
//        List<TypeEntity> data = new ArrayList<>();
//        data.add(new TypeEntity("wow", "科技资讯"));
//        data.add(new TypeEntity("apps", "趣味软件/游戏"));
//        data.add(new TypeEntity("imrich", "装备党"));
//        data.add(new TypeEntity("funny", "草根新闻"));
//        data.add(new TypeEntity("android", "Android"));
//        return data;
//    }
}
