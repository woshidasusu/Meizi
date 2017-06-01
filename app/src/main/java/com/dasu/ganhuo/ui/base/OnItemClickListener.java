package com.dasu.ganhuo.ui.base;

import android.view.View;

/**
 * Created by dasu on 2017/4/17.
 * https://github.com/woshidasusu/Meizi
 *
 * RecyclerView 的Item点击回调
 */
public interface OnItemClickListener<T> {
    void onItemClick(View view, T data, int position);
}
