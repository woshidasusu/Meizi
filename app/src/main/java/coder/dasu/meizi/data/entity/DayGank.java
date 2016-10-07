package coder.dasu.meizi.data.entity;

import org.greenrobot.greendao.annotation.Id;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 *
 * 每日干货
 */
public class DayGank implements Comparable<DayGank>{

    @Id(autoincrement = true)
    private Long id;

    private String date;
    private String imgUrl;
    private String description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(DayGank o) {
        return o.getDate().compareTo(this.getDate());
    }
}
