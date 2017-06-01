package com.dasu.ganhuo.ui.category;

import java.util.List;

/**
 * Created by dasu on 2017/4/21.
 *
 * 分类浏览界面的列表项的点击回调，需要区分点击的是图片还是标题
 */

interface OnItemClickListener<T> {
    void onImageClick(List<String> imgUrls);
    void onItemClick(T data);
}
