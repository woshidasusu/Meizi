package coder.dasu.meizi.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2016/9/27.
 * https://github.com/woshidasusu/Meizi
 * <p>
 * type类型的干货
 */
@Entity
public class Data implements Comparable<Data>{

    /**
     * {
     * "_id": "57eb0100421aa95de3b8ab00",
     * "createdAt": "2016-09-28T07:30:08.163Z",
     * "desc": "Android \u4e0b\u96ea\u6548\u679c",
     * "images": [
     * "http://img.gank.io/cf2f253d-a7d7-453e-a948-3dda9d9984ae"
     * ],
     * "publishedAt": "2016-09-28T11:35:12.91Z",
     * "source": "chrome",
     * "type": "Android",
     * "url": "https://github.com/HelloVass/SnowingView",
     * "used": true,
     * "who": "\u4ee3\u7801\u5bb6"
     * }
     */
    @Id(autoincrement = true)
    @Property(nameInDb = "unique_id")
    private Long unique_id;

    @Unique
    private String _id;
    private Date createdAt;
    private String desc;
    private Date publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    /**
     * 数据库表范式至少为1NF，所以加一个String字段来存储List的值
     */
    @Transient
    private List<String> images;

    private String strImages;

    @Generated(hash = 1756799058)
    public Data(Long unique_id, String _id, Date createdAt, String desc, Date publishedAt,
            String source, String type, String url, boolean used, String who, String strImages) {
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
        this.strImages = strImages;
    }

    @Generated(hash = 2135787902)
    public Data() {
    }

    public String getStrImages() {
        return strImages;
    }

    public void setStrImages(String strImages) {
        this.strImages = strImages;

    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setImages(String strImages) {
        this.images = Arrays.asList(strImages.split(","));
    }

    public void setStrImages(List<String> images) {
        this.strImages = Arrays.toString(images.toArray());
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Long getUnique_id() {
        return this.unique_id;
    }

    public void setUnique_id(Long unique_id) {
        this.unique_id = unique_id;
    }

    public boolean getUsed() {
        return this.used;
    }

    @Override
    public int compareTo(Data o) {
        return o.getPublishedAt().compareTo(this.getPublishedAt());
    }
}
