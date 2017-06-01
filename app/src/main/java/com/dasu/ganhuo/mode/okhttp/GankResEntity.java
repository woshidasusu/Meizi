package com.dasu.ganhuo.mode.okhttp;

import java.util.List;

/**
 * Created by suxq on 2017/4/14.
 *
 * Gank.io 接口返回的json数据对象，results会根据不同接口有不同的形态
 * 所以用 {@link GankController} 对Retrofit再做一层封装，对results做不同的处理
 */

public class GankResEntity {

    private List<String> category;
    private boolean error;
    private Object results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankResEntity{" +
                "category=" + category +
                ", error=" + error +
                ", results='" + results + '\'' +
                '}';
    }
}
