package coder.dasu.meizi.listener;

import android.view.View;

import coder.dasu.meizi.data.entity.Data;

/**
 * Created by dasu on 2016/9/10.
 * https://github.com/woshidasusu/Meizi
 */
public interface OnItemClickListener {

    void onItemClick(View view, View picture, View text, Data data);

}
