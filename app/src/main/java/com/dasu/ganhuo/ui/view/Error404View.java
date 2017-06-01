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

public class Error404View extends LinearLayout{

    public Error404View(Context context) {
        this(context, null);
    }

    public Error404View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private TextView mRetryBtn;
    private OnRetryClickListener mRetryClickListener;

    public Error404View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_404_error, this, true);
        mRetryBtn = (TextView) view.findViewById(R.id.tv_view_404);
        mRetryBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRetryClickListener != null) {
                    mRetryClickListener.onRetryClick();
                }
            }
        });
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public void setOnRetryClickListener(OnRetryClickListener listener) {
        mRetryClickListener = listener;
    }
}
