package com.dasu.ganhuo.mode.logic.home;

import android.content.Context;

import com.dasu.ganhuo.BuildConfig;
import com.dasu.ganhuo.mode.logic.update.UpdateController;
import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.mode.okhttp.RetrofitListener;
import com.dasu.ganhuo.ui.home.HomeActivity;
import com.dasu.ganhuo.ui.update.UpdateDialog;
import com.dasu.ganhuo.utils.LogUtils;
import com.dasu.ganhuo.utils.TimeUtils;

/**
 * Created by dasu on 2017/4/18.
 * <p>
 * 负责HomeActivity的业务逻辑即数据加载
 */

public class HomeController {
    private static final String TAG = HomeController.class.getSimpleName();

    private Context mContext;
    private HomeActivity mHomeActivity;

    public HomeController(Context context) {
        if (!(context instanceof HomeActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mHomeActivity = (HomeActivity) context;
    }

    public void loadBaseData() {
        //发起版本更新检查
        UpdateController.checkUpdate(mContext, new UpdateDialog(mContext));
        final String today = TimeUtils.getCurTimeString(TimeUtils.YMD_SDF);
        //获取当天的干货数据
        GankController.getTodayGanHuo(today, new RetrofitListener<SomedayGanHuoEntity>() {
            @Override
            public void onSuccess(SomedayGanHuoEntity data) {
                LogUtils.d(TAG, data.toString());
                if (data.getCategory().size() > 0) {
                    mHomeActivity.updateGanhuoRv(data);
                    //拼接原文的地址
                    String sourceUrl = BuildConfig.HTTP_GANK + today.split("-")[0] + "/" + today.split("-")[1] + "/" + today.split("-")[2];
                    mHomeActivity.updateSourceUrl(sourceUrl);
                } else {
                    //今天的干货暂时还没有
                    mHomeActivity.updateGanhuoRv(null);
                }
            }

            @Override
            public void onError(String description) {
                LogUtils.e(TAG, description);
                mHomeActivity.update404();
            }
        });
        //获取当天网页数据，用于解析标题
        GankController.getSomedayHtmlData(today, new RetrofitListener<HtmlDataEntity>() {
            @Override
            public void onSuccess(HtmlDataEntity data) {
                if (data != null) {
                    String text = data.getTitle().contains("今日力推")
                            ? data.getTitle().substring(5)
                            : data.getTitle();
                    text = text.replaceAll("/", "\r\n").replaceAll(" ", "");
                    mHomeActivity.updateSubTitle(text);
                }
            }

            @Override
            public void onError(String description) {
                LogUtils.e(TAG, description);
            }
        });
    }

    public void retryLoad() {
        final String today = TimeUtils.getCurTimeString(TimeUtils.YMD_SDF);
        //获取当天的干货数据
        GankController.getSomedayGanHuo(today, new RetrofitListener<SomedayGanHuoEntity>() {
            @Override
            public void onSuccess(SomedayGanHuoEntity data) {
                LogUtils.d(TAG, data.toString());
                if (data.getCategory().size() > 0) {
                    mHomeActivity.updateGanhuoRv(data);
                    //拼接原文的地址
                    String sourceUrl = BuildConfig.HTTP_GANK + today.split("-")[0] + "/" + today.split("-")[1] + "/" + today.split("-")[2];
                    mHomeActivity.updateSourceUrl(sourceUrl);
                } else {
                    //今天的干货暂时还没有
                    mHomeActivity.updateGanhuoRv(null);
                }
            }

            @Override
            public void onError(String description) {
                LogUtils.e(TAG, description);
                mHomeActivity.update404();
            }
        });
        //获取当天网页数据，用于解析标题
        GankController.getSomedayHtmlData(today, new RetrofitListener<HtmlDataEntity>() {
            @Override
            public void onSuccess(HtmlDataEntity data) {
                if (data != null) {
                    String text = data.getTitle().contains("今日力推")
                            ? data.getTitle().substring(5)
                            : data.getTitle();
                    text = text.replaceAll("/", "\r\n").replaceAll(" ", "");
                    mHomeActivity.updateSubTitle(text);
                }
            }

            @Override
            public void onError(String description) {
                LogUtils.e(TAG, description);
            }
        });
    }
}
