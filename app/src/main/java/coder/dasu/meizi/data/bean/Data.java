package coder.dasu.meizi.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 *
 * type类型的干货
 */
@Entity
public class Data {

    /**
     * _id : 57d741cc421aa95bc7f06a17
     * createdAt : 2016-09-13T08:01:16.18Z
     * desc : 9-13
     * publishedAt : 2016-09-13T11:36:54.991Z
     * source : chrome
     * type : 福利
     * url : http://ww3.sinaimg.cn/large/610dc034jw1f7rmrmrscrj20u011hgp1.jpg
     * used : true
     * who : 代码家
     */

    @Id(autoincrement = true)
    @Property(nameInDb = "unique_id")
    private Long unique_id;

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @Generated(hash = 1645128415)
    public Data(Long unique_id, String _id, String createdAt, String desc,
            String publishedAt, String source, String type, String url,
            boolean used, String who) {
        this.unique_id = unique_id;
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }
    @Generated(hash = 2135787902)
    public Data() {
    }
    public Long getUnique_id() {
        return this.unique_id;
    }
    public void setUnique_id(Long unique_id) {
        this.unique_id = unique_id;
    }
    public String get_id() {
        return this._id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPublishedAt() {
        return this.publishedAt;
    }
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public boolean getUsed() {
        return this.used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public String getWho() {
        return this.who;
    }
    public void setWho(String who) {
        this.who = who;
    }

}
