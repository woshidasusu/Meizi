package com.dasu.ganhuo.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dasu.ganhuo.R;

/**
 * Created by dasu on 2017/5/3.
 */

public class EmptyDataView extends LinearLayout {

    public EmptyDataView(Context context) {
        this(context, null);
    }

    public EmptyDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TextView mEmptyTipTv;

    public EmptyDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_data_empty, this, true);
        mEmptyTipTv = (TextView) view.findViewById(R.id.tv_view_data_empty);
    }

    public void setEmptyTip(String text) {
        if (mEmptyTipTv != null) {
            mEmptyTipTv.setText(text);
        }
    }
}
