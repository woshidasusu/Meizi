package com.dasu.ganhuo.ui.reading;

import com.dasu.ganhuo.mode.logic.reading.BlogEntity;

import java.util.List;

/**
 * Created by dasu on 2017/4/22.
 */

interface IReadingController {

    String getReadingType();

    void updateBlogs(List<BlogEntity> data);

    void onLoadFailed();

    void updatePages(List<String> pages);

    void updateCurPage(String page);

}
