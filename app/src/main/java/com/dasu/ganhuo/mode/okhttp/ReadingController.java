package com.dasu.ganhuo.mode.okhttp;

import android.os.Handler;
import android.os.Message;

import com.dasu.ganhuo.BuildConfig;
import com.dasu.ganhuo.mode.logic.reading.BlogEntity;
import com.dasu.ganhuo.mode.logic.reading.ReadingEntity;
import com.dasu.ganhuo.mode.logic.reading.TypeEntity;
import com.dasu.ganhuo.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/21.
 *
 * 闲读数据获取，使用jsoup来解析闲读html页面数据，因为没有相关api可以使用
 */

public class ReadingController {
    private static final String TAG = ReadingController.class.getSimpleName();

    private static final String API_URL = BuildConfig.READING_SERVICE;
    //用于防止重复发起请求
    private static final int TASK_NOTHING = 0X01;
    private static final int TASK_GET_TYPE = 0X02;
    private static final int TASK_GET_BLOG = 0X04;
    //用于在ui线程中回调结果
    private static final int ON_SUCCESS = 1;
    private static final int ON_FAILED = 2;

    private static int sTaskState = TASK_NOTHING;

    private static UiHandler sUiHandler = new UiHandler();

    /**
     * 获取闲读的所有栏目分类
     *
     * @param listener
     */
    public static void getReadingTypes(final RetrofitListener<List<TypeEntity>> listener) {
        if (markTaskState(TASK_GET_TYPE)) {
            return;
        }
        sTaskState |= TASK_GET_TYPE;
        LogUtils.d(TAG, "获取闲读的所有栏目分类...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //解析 html
                    Document doc = Jsoup.connect(API_URL).get();
                    Element element = doc.getElementById("xiandu_cat");
                    Elements elements = element.getElementsByTag("a");
                    List<TypeEntity> dataList = new ArrayList<>();
                    for (Element e : elements) {
                        TypeEntity entity = new TypeEntity();
                        entity.setName(e.text());
                        entity.setType(e.attr("href").substring(e.attr("href").lastIndexOf("/") + 1));
                        dataList.add(entity);
                    }
                    LogUtils.d(TAG, "获取成功: " + dataList.size());
                    Message msg = Message.obtain();
                    msg.what = ON_SUCCESS;
                    msg.obj = new HandlerMsg<List<TypeEntity>>().setListener(listener).setData(dataList);
                    sUiHandler.sendMessage(msg);
                } catch (IOException e) {
                    LogUtils.e(TAG, "获取失败, " + e.getMessage());
                    Message msg = Message.obtain();
                    msg.what = ON_FAILED;
                    msg.obj = new HandlerMsg<List<TypeEntity>>().setListener(listener);
                    sUiHandler.sendMessage(msg);
                } finally {
                    sTaskState &= ~TASK_GET_TYPE;
                }
            }
        }).start();
    }

    /**
     * 获取指定栏目的分类的闲读文章
     *
     * @param type
     * @param page
     * @param listener
     */
    public static void getSpecifyType(final String type, final int page, final RetrofitListener<ReadingEntity> listener) {
        if (markTaskState(TASK_GET_BLOG)) {
            return;
        }
        sTaskState |= TASK_GET_BLOG;
        LogUtils.d(TAG, "获取第" + page + "页的" + type + "类型的闲读文章中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ReadingEntity readingEntity = new ReadingEntity();
                    //解析 html
                    String url = API_URL + type + "/page/" + page;
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("xiandu_item");
                    List<BlogEntity> dataList = new ArrayList<>();
                    for (Element e : elements) {
                        BlogEntity entity = new BlogEntity();
                        entity.setTitle(e.getElementsByTag("a").get(0).text());
                        entity.setBlogUrl(e.getElementsByTag("a").get(0).attr("href"));
                        entity.setSource(e.getElementsByTag("a").get(1).attr("title"));
                        entity.setDate(e.getElementsByTag("small").get(0).text());
                        entity.setSourceIcon(e.getElementsByTag("img").get(0).attr("src"));
                        dataList.add(entity);
                    }
                    LogUtils.d(TAG, "获取成功: " + dataList.size());
                    readingEntity.setBlogEntitys(dataList);
                    readingEntity.setCurPage(page);
                    List<String> pageList = new ArrayList<String>();
                    Element pages = doc.getElementById("pagination");
                    for (Element p : pages.children()) {
                        pageList.add(p.text());
                    }
                    readingEntity.setPages(pageList);
                    Message msg = Message.obtain();
                    msg.what = ON_SUCCESS;
                    msg.obj = new HandlerMsg<ReadingEntity>().setListener(listener).setData(readingEntity);
                    sUiHandler.sendMessage(msg);
                } catch (IOException e) {
                    LogUtils.e(TAG, "获取失败, " + e.getMessage());
                    Message msg = Message.obtain();
                    msg.what = ON_FAILED;
                    msg.obj = new HandlerMsg<ReadingEntity>().setListener(listener);
                    sUiHandler.sendMessage(msg);
                } finally {
                    sTaskState &= ~TASK_GET_BLOG;
                }
            }
        }).start();
    }

    private static boolean markTaskState(int taskState) {
        if ((sTaskState & taskState) == taskState ) {
            return true;
        }
        return false;
    }

    private static class UiHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    HandlerMsg handlerMsg = (HandlerMsg) msg.obj;
                    handlerMsg.mListener.onSuccess(handlerMsg.data);
                    break;
                case ON_FAILED:
                    HandlerMsg handle = (HandlerMsg) msg.obj;
                    handle.mListener.onError("获取失败");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private static class HandlerMsg<T> {
        RetrofitListener<T> mListener;
        T data;

        HandlerMsg<T> setListener(RetrofitListener<T> listener) {
            mListener = listener;
            return this;
        }

        HandlerMsg<T> setData(T data) {
            this.data = data;
            return this;
        }
    }

}
