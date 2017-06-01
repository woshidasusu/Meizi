package com.dasu.ganhuo.ui.meizi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.ui.base.ActivityStack;
import com.dasu.ganhuo.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/24.
 */

public class ImageActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        initVariable();
        initView();
    }

    private List<String> mImageList;
    private int mPosition;

    private void initVariable() {
        mImageList = getIntent().getStringArrayListExtra("_images");
        mPosition = getIntent().getIntExtra("_position", 0);
    }

    private ViewPager mViewPager;
    private SwitchImageAdapter mPagerAdapter;
    private ImageView mBackBtn;
    private TextView mImageIndexTv;

    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.ibtn_meizi_back);
        mImageIndexTv = (TextView) findViewById(R.id.tv_meizi_image_index);
        mBackBtn.setOnClickListener(onBackClick());
        if (mImageList != null && mImageList.size() > 0) {
            mImageIndexTv.setText((mPosition + 1) + "/" + mImageList.size());
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_meizi_image);
        mPagerAdapter = new SwitchImageAdapter(mContext, mViewPager, mImageList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(onPageChange());
        mViewPager.setCurrentItem(mPosition);
    }

    public static void startActivity(Context context, int position, ArrayList<String> images) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putStringArrayListExtra("_images", images);
        intent.putExtra("_position", position);
        context.startActivity(intent);
    }

    private View.OnClickListener onBackClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityStack.getInstance().popAndFinishActivity();
            }
        };
    }

    private ViewPager.OnPageChangeListener onPageChange() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int total = mImageList.size();
                String text = (position + 1) + "/" + total;
                mImageIndexTv.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

}
