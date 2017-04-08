package com.dasu.gank.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 */
public class GankDayResponse extends GankResponse{

    public List<String> category;
    public Result results;

    public class Result{
        @SerializedName("Android") public List<Data> android;
        @SerializedName("iOS") public List<Data> ios;
        @SerializedName("福利") public List<Data> meizi;
        @SerializedName("瞎推荐") public List<Data> push;
        @SerializedName("休息视频") public List<Data> video;
        @SerializedName("拓展资源") public List<Data> expand;
        @SerializedName("App") public List<Data> app;
    }

    /**
     * {
     *  "category": [
     *      "iOS",
     *      "Android",
     *      "\u778e\u63a8\u8350",
     *      "\u62d3\u5c55\u8d44\u6e90",
     *      "\u798f\u5229",
     *      "\u4f11\u606f\u89c6\u9891"
     *      ],
     *  "error": false,
     *  "results": {
     *      "Android": [
     *          {
     *              "_id": "56cc6d23421aa95caa707a69",
     *              "createdAt": "2015-08-06T07:15:52.65Z",
     *              "desc": "\u7c7b\u4f3cLink Bubble\u7684\u60ac\u6d6e\u5f0f\u64cd\u4f5c\u8bbe\u8ba1",
     *              "publishedAt": "2015-08-07T03:57:48.45Z",
     *              "type": "Android",
     *              "url": "https://github.com/recruit-lifestyle/FloatingView",
     *              "used": true,
     *              "who": "mthli"
     *          },
     *          ****
     *        ]
     *   }
     * }
     */

}
