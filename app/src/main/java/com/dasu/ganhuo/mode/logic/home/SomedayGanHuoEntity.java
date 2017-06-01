package com.dasu.ganhuo.mode.logic.home;

import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxq on 2017/4/14.
 *
 * 某一天的干货数据对象
 */

public class SomedayGanHuoEntity implements Serializable{

    private List<String> category;
    private Results results;

    public static class Results implements Serializable{
        @SerializedName("Android") private List<GanHuoEntity> android;
        @SerializedName("iOS") private List<GanHuoEntity> ios;
        @SerializedName("前端") private List<GanHuoEntity> web;
        @SerializedName("瞎推荐") private List<GanHuoEntity> recommend;
        @SerializedName("休息视频") private List<GanHuoEntity> video;
        @SerializedName("福利") private List<GanHuoEntity> meizi;
        @SerializedName("App") private List<GanHuoEntity> app;
        @SerializedName("扩展资源") private List<GanHuoEntity> other;

        public List<List<GanHuoEntity>> getAllList() {
            List<List<GanHuoEntity>> allList = new ArrayList<>();
            if (android != null && android.size() > 0) {
                allList.add(android);
            }
            if (ios != null && ios.size() > 0) {
                allList.add(ios);
            }
            if (web != null && web.size() > 0) {
                allList.add(web);
            }
            if (recommend != null && recommend.size() > 0) {
                allList.add(recommend);
            }
            if (video != null && video.size() > 0) {
                allList.add(video);
            }
            if (app != null && app.size() > 0) {
                allList.add(app);
            }
            if (other != null && other.size() > 0) {
                allList.add(other);
            }
            return allList;
        }
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
