package com.dasu.ganhuo.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.dasu.ganhuo.R;

/**
 * Created by dasu on 2017/4/18.
 *
 * 按指定的长宽比，同比例截取图片中心展示
 */

@SuppressLint("AppCompatCustomView")
public class ScaleImageView extends ImageView {

    private int originWidth;
    private int originHeight;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        originWidth = ta.getInteger(R.styleable.ScaleImageView_origin_width, 0);
        originHeight = ta.getInteger(R.styleable.ScaleImageView_origin_height, 0);
        ta.recycle();
    }

    public void setOriginSize(int originWidth, int originHeight) {
        this.originWidth = originWidth;
        this.originHeight = originHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originWidth > 0 && originHeight > 0) {
            float scale = originWidth * 1.0f / originHeight;
            if (scale < 0.7f) {
                scale = 0.7f;
            } else if (scale > 1.3f) {
                scale = 1.3f;
            }
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (width > 0) {
                height = (int) (width * 1.0f / scale);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
