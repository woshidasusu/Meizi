package com.dasu.ganhuo.mode.logic.category;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.okhttp.GankController;

/**
 * Created by dasu on 2017/4/18.
 *
 * GanHuoEntity 获取type对应背景颜色等的帮助类
 */

public class GanHuoHelper {

    /**
     * 获取type对应的背景颜色
     *
     * @param type {@link GankController}
     * @return
     */
    public static int getTypeColor(String type) {
        if (type.equals(GankController.TYPE_ANDROID)) {
            return R.color.green;
        } else if (type.equals(GankController.TYPE_IOS)) {
            return R.color.yellow;
        } else if (type.equals(GankController.TYPE_WEB)) {
            return R.color.blue;
        } else if (type.equals(GankController.TYPE_APP)) {
            return R.color.orange;
        } else if (type.equals(GankController.TYPE_MEIZI)) {
            return R.color.red;
        } else if (type.equals(GankController.TYPE_RECOMMENT)) {
            return R.color.pink;
        } else if (type.equals(GankController.TYPE_VIDEO)) {
            return R.color.deep_blue;
        } else if (type.equals(GankController.TYPE_OTHER)) {
            return R.color.brown;
        } else {
            return R.color.teal;
        }
    }

    /**
     * 根据url判断来源
     *
     * @param url
     * @return
     */
    public static String getUrlSource(String url) {
        if (url.contains("github")) {
            return "Github";
        } else if (url.contains("jianshu")) {
            return "简书";
        } else if (url.contains("csdn")) {
            return "CSDN";
        } else if (url.contains("cnblogs")) {
            return "博客园";
        } else if (url.contains("bilibili")) {
            return "bilibili";
        } else if (url.contains("v.qq")) {
            return "腾讯视频";
        } else if (url.contains("v.youku")) {
            return "优酷视频";
        } else if (url.contains("weibo")) {
            return "微博";
        } else if (url.contains("douban")) {
            return "豆瓣";
        } else if (url.contains("miaopai")) {
            return "秒拍";
        } else {
            return "其它来源";
        }
    }

    /**
     * 获取url来源对应的背景颜色
     *
     * @param url
     * @return
     */
    public static int getSourceColor(String url) {
        if (url.contains("github")) {
            return R.color.black;
        } else if (url.contains("jianshu")) {
            return R.color.light_pink;
        } else if (url.contains("csdn")) {
            return R.color.deep_blue;
        } else if (url.contains("cnblogs")) {
            return R.color.red;
        } else if (url.contains("bilibili")) {
            return R.color.pink;
        } else if (url.contains("v.qq")) {
            return R.color.teal;
        } else if (url.contains("v.youku")) {
            return R.color.blue;
        } else if (url.contains(".jpg")) {
            return R.color.white;
        } else if (url.contains("weibo")) {
            return R.color.yellow;
        } else if (url.contains("douban")) {
            return R.color.green;
        } else if (url.contains("miaopai")) {
            return R.color.yellow;
        } else {
            return R.color.teal;
        }
    }

}
