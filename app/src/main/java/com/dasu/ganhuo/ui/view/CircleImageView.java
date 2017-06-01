package com.dasu.ganhuo.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

/**
 * Created by dasu on 2017/5/3.
 *
 * 利用Glide实现圆形ImageView
 */

public class CircleImageView extends ImageView {

    private Context mContext;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mContext = getContext();
        BitmapDrawable bDrawable = (BitmapDrawable) drawable;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bDrawable.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
        Glide.with(mContext)
                .load(baos.toByteArray())
                .transform(new GlideCircleTransform(mContext))
                .into(this);
    }
}
