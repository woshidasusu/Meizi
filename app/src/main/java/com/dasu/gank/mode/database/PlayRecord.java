package com.dasu.gank.mode.database;

import java.io.Serializable;

public class PlayRecord implements Serializable {
    private long video_id;
    private String name;
    private String cover;
    private int type;//视频类型  0：单剧集  1：多剧集
    private String drama_index;//剧情索引  DATE:发布日期 SERIE:集数
    private long series_id;//分集id

    public long getVideo_release_time() {
        return video_release_time;
    }

    public void setVideo_release_time(long video_release_time) {
        this.video_release_time = video_release_time;
    }

    private long video_release_time;
    private long played_series;//记录播放第几集
    private int played_time;//当前集播放的记录时间

    public long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(long video_id) {
        this.video_id = video_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDrama_index() {
        return drama_index;
    }

    public void setDrama_index(String drama_index) {
        this.drama_index = drama_index;
    }

    public long getSeries_id() {
        return series_id;
    }

    public void setSeries_id(long series_id) {
        this.series_id = series_id;
    }

    public long getPlayed_series() {
        return played_series;
    }

    public void setPlayed_series(long played_series) {
        this.played_series = played_series;
    }

    public int getPlayed_time() {
        return played_time;
    }

    public void setPlayed_time(int played_time) {
        this.played_time = played_time;
    }

    @Override
    public String toString() {
        return "PlayRecord: video_id=" + video_id + " name=" + name + " cover=" + cover + " " +
                "type=" + type + " drama_index=" + drama_index + " series_id=" + series_id + " " +
                "played_series=" + played_series + " played_time=" + played_time+" video_release_time="+video_release_time;
    }
}
