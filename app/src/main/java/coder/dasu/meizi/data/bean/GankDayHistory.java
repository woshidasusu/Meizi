package coder.dasu.meizi.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 */
@Entity
public class GankDayHistory {

    @Id(autoincrement = true)
    private Long id;

    private String day;

    @Generated(hash = 272910289)
    public GankDayHistory(Long id, String day) {
        this.id = id;
        this.day = day;
    }

    @Generated(hash = 413149336)
    public GankDayHistory() {
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

}
