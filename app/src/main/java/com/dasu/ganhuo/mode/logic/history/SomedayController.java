package com.dasu.ganhuo.mode.logic.history;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.dasu.ganhuo.BuildConfig;
import com.dasu.ganhuo.ui.history.SomedayActivity;
import com.dasu.ganhuo.utils.LogUtils;
import com.dasu.ganhuo.utils.TimeUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2017/4/27.
 */

public class SomedayController {
    private static final String TAG = SomedayController.class.getSimpleName();

    private Context mContext;
    private SomedayActivity mSomedayActivity;

    public SomedayController(Context context) {
        if (!(context instanceof SomedayActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mSomedayActivity = (SomedayActivity) context;
    }

    private static final int STATE_PARSE_END = 1;
    private static final int STATE_PARSING = 2;

    private UiHandler mUiHandler;
    private int mParseState = STATE_PARSE_END;
    private ParseTask mParseTask;

    public void parseContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (mParseState == STATE_PARSING) {
            return;
        }
        mParseState = STATE_PARSING;
        if (mParseTask == null) {
            mParseTask = new ParseTask(content);
        }
        if (mUiHandler == null) {
            mUiHandler = new UiHandler();
        }
        new Thread(mParseTask).start();
    }

    public void parseOthers(String title, long publishDate) {
        if (TextUtils.isEmpty(title) || publishDate == 0) {
            return;
        }
        String date = TimeUtils.formatDate(new Date(publishDate));
        String bigTitle = title.substring(0, title.indexOf("：")).replace("今日", date);
        String subTitle = title.substring(title.indexOf("：") + 1).replaceAll("/", "\r\n").replaceAll(" ", "");
        mSomedayActivity.updateSubTitle(subTitle);
        mSomedayActivity.updateTitle(bigTitle);
        String[] dates = TimeUtils.milliseconds2String(publishDate, TimeUtils.YMD_SDF).split("-");
        String sourceUrl = BuildConfig.HTTP_GANK + dates[0] + "/" + dates[1] + "/" + dates[2];
        mSomedayActivity.updateSourceUrl(sourceUrl);
    }


    private class UiHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_PARSE_END:
                    List<SomedayEntity> data = (List<SomedayEntity>) msg.obj;
                    mSomedayActivity.updateSomeday(data);
                    break;
                case STATE_PARSING:

                    break;
            }
        }
    }

    private class ParseTask implements Runnable {

        private String mHtmlContent;

        ParseTask(String html) {
            mHtmlContent = html;
        }

        @Override
        public void run() {
            List<SomedayEntity> somedayList = new ArrayList<>();
            try {
                Document doc = Jsoup.parse(mHtmlContent);
                Elements h3s = doc.getElementsByTag("h3");
                for (Element h3 : h3s) {
                    SomedayEntity somedayHtml = new SomedayEntity();
                    somedayHtml.setTitle(h3.text());
                    Elements as = h3.nextElementSibling().getElementsByTag("a");
                    List<SomedayEntity.Type> typeList = new ArrayList<>();
                    for (Element a : as) {
                        SomedayEntity.Type type = new SomedayEntity.Type();
                        if (!TextUtils.isEmpty(a.text())) {
                            type.setText(a.parent().text());
                            type.setHref(a.attr("href"));
                            typeList.add(type);
                        }
                    }
                    somedayHtml.setTypes(typeList);
                    somedayList.add(somedayHtml);
                }
            } finally {
                Message msg = Message.obtain();
                msg.what = STATE_PARSE_END;
                msg.obj = somedayList;
                mUiHandler.sendMessage(msg);
            }
        }
    }

}
