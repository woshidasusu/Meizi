package coder.dasu.meizi.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 *
 * 发布过干货的日期
 */
@Entity
public class DayPublish {

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
    

}
