package com.dasu.gank.mode.database;

import android.content.UriMatcher;
import android.net.Uri;

class DatabaseConstValue {

    public static final String AUTHORITY = "com.qiyi.wstv.database";
    //DSP上报数据表名称
    public static final String DSP_DATA_TABLE_NAME = "DSP_DATA";
    //DSP上报数据表名称

    //DSP上报数据表字段
    public static final String DSP_ID = "_id";
    public static final String DSP_TYPE = "dsp_type";
    public static final String DSP_RECORD = "dsp_record";
    //播放记录
    //播放记录表名称
    public static final String PLAY_RECORD_TABLE_NAME = "PLAY_RECORD";
    //播放记录表名称

    //播放记录表字段
    public static final String ID = "_id";
    public static final String WSID = "ws_id";
    public static final String VIDEO_ID = "video_id";
    public static final String NAME = "name";//名称
    public static final String COVER = "cover";//封面图
    public static final String TYPE = "type";//视频类型  0：单剧集  1：多剧集
    public static final String DRAMA_INDEX = "drama_index";//剧情索引  DATE:发布日期 SERIE:集数
    public static final String SERIES_ID = "series_id";//分集id
    public static final String VIDEO_RELEASE_TIME = "video_release_time";//发布时间
    public static final String PLAYED_SERIES = "played_series";//记录播放第几集
    public static final String PLAYED_TIME = "played_time";//当前集播放的记录时间
    public static final String INSERT_TIME = "insert_time";//当前数据的插入时间
    //播放记录表字段

    public static final Uri CONTENT_URI_PLAY_RECORD = Uri.parse("content://" + AUTHORITY + "/" + PLAY_RECORD_TABLE_NAME);
    public static final Uri CONTENT_URI_DSP_DATA = Uri.parse("content://" + AUTHORITY + "/" + DSP_DATA_TABLE_NAME);
    //数据库匹配URL的返回码
    public static final int PLAY_RECORD = 1;
    public static final int DSP_DATA = 2;
    //数据库匹配URL的返回码

    public static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, PLAY_RECORD_TABLE_NAME, PLAY_RECORD);
        sUriMatcher.addURI(AUTHORITY, DSP_DATA_TABLE_NAME, DSP_DATA);
    }
}
