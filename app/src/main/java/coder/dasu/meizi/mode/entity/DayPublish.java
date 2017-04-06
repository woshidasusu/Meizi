package coder.dasu.meizi.mode.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 *
 * 发布过干货的日期
 */
@Entity
public class DayPublish implements Comparable<DayPublish>{

    @Id(autoincrement = true)
    private Long id;

    private String day;

    @Generated(hash = 1119906139)
    public DayPublish(Long id, String day) {
        this.id = id;
        this.day = day;
    }

    @Generated(hash = 1044609748)
    public DayPublish() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int compareTo(DayPublish o) {
        return o.getDay().compareTo(this.getDay());
    }
}
