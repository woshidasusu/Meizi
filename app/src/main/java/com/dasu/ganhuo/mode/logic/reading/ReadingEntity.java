package com.dasu.ganhuo.mode.logic.reading;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dasu on 2017/4/30.
 */

public class ReadingEntity implements Serializable {

    private int curPage;
    private List<String> pages;
    private List<BlogEntity> blogEntitys;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public List<BlogEntity> getBlogEntitys() {
        return blogEntitys;
    }

    public void setBlogEntitys(List<BlogEntity> blogEntitys) {
        this.blogEntitys = blogEntitys;
    }

    @Override
    public String toString() {
        return "ReadingEntity{" +
                "curPage=" + curPage +
                ", pages=" + pages +
                ", blogEntitys=" + blogEntitys +
                '}';
    }
}
