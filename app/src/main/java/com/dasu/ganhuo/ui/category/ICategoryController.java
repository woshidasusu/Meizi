package com.dasu.ganhuo.ui.category;

import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;

import java.util.List;

/**
 * Created by dasu on 2017/4/20.
 *
 * 这不是一个回调接口，而是一个规范接口，定义每个CategoryFragment必须要实现的工作
 */

interface ICategoryController {

    String getCategoryType();

    void updateGanHuo(List<GanHuoEntity> data);

    void onLoadFailed();

}
