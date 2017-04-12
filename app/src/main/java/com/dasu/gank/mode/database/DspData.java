package com.dasu.gank.mode.database;

import java.io.Serializable;

/**
 * Created by zhengyx on 2017/3/15.
 */

public class DspData implements Serializable {
    private String type;
    private String dsp_record;
    private long insert_time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDsp_record() {
        return dsp_record;
    }

    public void setDsp_record(String dsp_record) {
        this.dsp_record = dsp_record;
    }
    public long getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(long insert_time) {
        this.insert_time = insert_time;
    }

    @Override
    public String toString() {
        return "DspData: type=" + type + " dsp_record=" + dsp_record;
    }
}
