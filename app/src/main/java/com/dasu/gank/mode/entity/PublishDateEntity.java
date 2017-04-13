package com.dasu.gank.mode.entity;

import android.provider.BaseColumns;

/**
 * Created by suxq on 2017/4/13.
 */

public class PublishDateEntity implements BaseColumns {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PublishDateEntity{" +
                "date='" + date + '\'' +
                '}';
    }
}
