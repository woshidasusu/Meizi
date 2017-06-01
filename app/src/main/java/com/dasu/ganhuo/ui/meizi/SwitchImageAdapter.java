package com.dasu.ganhuo.ui.meizi;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * Created by dasu on 2017/4/24.
 *
 * viewPager左右滑动切换图片的适配器，因为每个page都只是展示一张图片，所以可以将ImageView复用
 * 以减少每次都需要销毁重建浪费的资源
 */

class SwitchImageAdapter extends PagerAdapter {
    private static final String TAG = SwitchImageAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> mImageList;
    private ImageView[] mImageViews = new ImageView[3]; //只有3个ImageView复用显示图片
    private ViewPager mViewPager;

    SwitchImageAdapter(Context context, ViewPager viewPager, List<String> images) {
        mContext = context;
        mImageList = images;
        mViewPager = viewPager;
    }

    private ImageView newImageView(Context context) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView imageView = new PhotoView(context);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageView;
    }

    @Override
    public int getCount() {
        return mImageList == null ? 0 : mImageList.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //保证viewPager只缓存3个page
        if (mViewPager.getOffscreenPageLimit() > 1) {
            mViewPager.setOffscreenPageLimit(1);
        }
        if (mImageViews[position % 3] == null) {
            mImageViews[position % 3] = newImageView(mContext);
        }
        ImageView imageView = mImageViews[position % 3];
        Glide.with(mContext)
                .load(mImageList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        if (container.indexOfChild(imageView) == -1) {
            container.addView(imageView);
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
