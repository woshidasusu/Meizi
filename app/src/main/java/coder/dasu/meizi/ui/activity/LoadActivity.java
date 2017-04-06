package coder.dasu.meizi.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import coder.dasu.meizi.AppConstant;
import coder.dasu.meizi.MeiziApplication;
import coder.dasu.meizi.R;
import coder.dasu.meizi.mode.dao.DaoSession;
import coder.dasu.meizi.mode.entity.DayPublish;
import coder.dasu.meizi.mode.net.GankApi;
import coder.dasu.meizi.mode.net.GankRetrofit;
import coder.dasu.meizi.mode.net.response.GankHistoryResponse;
import coder.dasu.meizi.utils.ListUtils;
import coder.dasu.meizi.utils.ScreenUtils;
import coder.dasu.meizi.utils.TimeUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dasu on 2016/10/3.
 * https://github.com/woshidasusu/Meizi
 * <p>
 * 先获取Gank api发布过干货的日期，好供后面页面加载数据
 */
public class LoadActivity extends Activity {

    private static final String TAG = LoadActivity.class.getSimpleName();

    private DaoSession mDaoSession;
    private String mLastUpdateDay;
    private List<String> mHistroyDayList;
    private String mTodayDate;
    private Call<GankHistoryResponse> call;
    private boolean isLoading;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_welcome);
        initVatiable();
        loadServiceData();
    }

    private void initVatiable() {
        isLoading = false;
        mTodayDate = TimeUtils.getCurTimeString(TimeUtils.DAY_SDF);
        mDaoSession = MeiziApplication.getDaoSession();
        mLastUpdateDay = MeiziApplication.getConfigSP().getString(AppConstant.GANK_DAY_LATEST_UPDATE_TIME, "");
        mHistroyDayList = new ArrayList<>();
        QueryBuilder<DayPublish> qb = mDaoSession.getDayPublishDao().queryBuilder();
        for (DayPublish day : qb.list()) {
            mHistroyDayList.add(day.getDay());
        }
    }

    public void loadServiceData() {
        long today = TimeUtils.string2Milliseconds(mTodayDate, TimeUtils.DAY_SDF);
        long lastUpdateDay = TextUtils.isEmpty(mLastUpdateDay)
                ? 0
                : TimeUtils.string2Milliseconds(mLastUpdateDay, TimeUtils.DAY_SDF);
        if (today > lastUpdateDay) {
            loadGankDayData();
            mTimer = new Timer();
            mTimer.schedule(getTimeLimitTask(), 2000);
        } else {
            startMainActivity();
        }
    }

    private void loadGankDayData() {
        isLoading = true;
        GankApi gankApi = GankRetrofit.getGankService();
        call = gankApi.getGankDay();
        call.enqueue(getLoadGankDayCallback());
    }

    public Callback<GankHistoryResponse> getLoadGankDayCallback() {
        return new Callback<GankHistoryResponse>() {
            @Override
            public void onResponse(Call<GankHistoryResponse> call, Response<GankHistoryResponse> response) {
                Log.d(TAG, "loadGankDayData()->onResponse(): " + response.body().toString());
                if (response.isSuccessful()) {
                    MeiziApplication.getConfigSP().putString(AppConstant.GANK_DAY_LATEST_UPDATE_TIME, mTodayDate);
                    List<String> strList = response.body().results;
                    strList.removeAll(mHistroyDayList);
                    List<DayPublish> dayList = new ArrayList<>();
                    for (String s : strList) {
                        DayPublish day = new DayPublish();
                        day.setDay(s);
                        dayList.add(day);
                    }
                    if (!ListUtils.isEmpty(dayList)) {
                        Collections.sort(dayList);
                        MeiziApplication.getConfigSP().putString(AppConstant.GANK_SERVER_LATEST_ISSUE_TIME,
                                dayList.get(0).getDay());
                        mDaoSession.getDayPublishDao().insertInTx(dayList);
                    }
                    if (isLoading) {
                        mTimer.cancel();
                        startMainActivity();
                    }
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<GankHistoryResponse> call, Throwable t) {
                Log.d(TAG, "loadGankDayData()->onFailure(): " + t.getMessage());
                if (isLoading) {
                    mTimer.cancel();
                    startMainActivity();
                }
                isLoading = false;
            }
        };
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public TimerTask getTimeLimitTask() {
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        call.cancel();
                        isLoading = false;
                        startMainActivity();
                    }
                });
            }
        };
    }
}
