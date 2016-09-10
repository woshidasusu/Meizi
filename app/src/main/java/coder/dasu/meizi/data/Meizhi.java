package coder.dasu.meizi.data;

/**
 * Created by sxq on 2016/9/10.
 */
public class Meizhi {

    private String imgUrl;
    private String text;
    private int resId;

    public Meizhi() {
    }

    public Meizhi(String imgUrl, String text, int resId) {
        this.imgUrl = imgUrl;
        this.text = text;
        this.resId = resId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
