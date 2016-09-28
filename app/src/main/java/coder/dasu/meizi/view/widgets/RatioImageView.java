package coder.dasu.meizi.view.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import coder.dasu.meizi.R;

/**
 * Created by sxq on 2016/9/21.
 */

public class RatioImageView extends ImageView {

    private int originWidth;
    private int originHeight;

    public RatioImageView(Context context) {
        this(context, null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        originWidth = ta.getInteger(R.styleable.RatioImageView_origin_width, 0);
        originHeight = ta.getInteger(R.styleable.RatioImageView_origin_height, 0);
        ta.recycle();
    }

    public void setOriginSize(int originWidth, int originHeight) {
        this.originWidth = originWidth;
        this.originHeight = originHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originHeight >0 && originWidth >0) {
            float ratio = (float) originWidth / (float)originHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            if (width > 0) {
                height = (int) ((float)width / ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
