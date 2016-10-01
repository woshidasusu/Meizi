package coder.dasu.meizi.listener;

import android.view.View;

import coder.dasu.meizi.data.bean.Data;

/**
 * Created by dasu on 2016/9/10.
 * https://github.com/woshidasusu/Meizi
 */
public interface OnItemClickListener {

    /**
     * Gank data fragment里的 recycleview 的item点击回调接口
     * @param view
     * @param picture
     * @param text
     * @param data
     */
    void onItemClick(View view, View picture, View text, Data data);

}
