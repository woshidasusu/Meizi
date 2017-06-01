package com.dasu.ganhuo.mode.logic.category;

import android.content.Context;

import com.dasu.ganhuo.mode.okhttp.GankController;
import com.dasu.ganhuo.ui.category.CategoryActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dasu on 2017/4/20.
 *
 * CategoryActivity对应的Controller
 */
public class CategoryAController {
    private static final String TAG = CategoryAController.class.getSimpleName();

    private Context mContext;
    private CategoryActivity mCategoryActivity;
    //tab 标签页
    private List<String> mCategoryList;

    public CategoryAController(Context context) {
        if (!(context instanceof CategoryActivity)) {
            LogUtils.e(TAG, TAG + "绑定错误的Activity");
            throw new UnsupportedOperationException(TAG + "绑定错误的Activity");
        }
        mContext = context;
        mCategoryActivity = (CategoryActivity) context;
        initVariable();
    }

    private void initVariable() {
        mCategoryList = new ArrayList<>();
        mCategoryList.add(GankController.TYPE_ANDROID);
        mCategoryList.add(GankController.TYPE_IOS);
        mCategoryList.add(GankController.TYPE_APP);
        mCategoryList.add(GankController.TYPE_WEB);
        mCategoryList.add(GankController.TYPE_RECOMMENT);
        mCategoryList.add(GankController.TYPE_OTHER);
    }

    public List<String> getCategoryList() {
        if (mCategoryList == null) {
            mCategoryList = new ArrayList<>();
        }
        return mCategoryList;
    }

}
