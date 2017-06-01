package com.dasu.ganhuo.mode.logic.reading;

/**
 * Created by dasu on 2017/4/22.
 *
 * 闲读的文章信息
 */

public class BlogEntity {

    private String title;
    private String blogUrl;
    private String date;
    private String source;
    private String sourceIcon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    @Override
    public String toString() {
        return "BlogEntity{" +
                "title='" + title + '\'' +
                ", blogUrl='" + blogUrl + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", sourceIcon='" + sourceIcon + '\'' +
                '}';
    }
}
