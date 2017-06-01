package com.dasu.ganhuo.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.utils.ScreenUtils;
import com.dasu.ganhuo.utils.ToastUtils;

import java.util.List;

/**
 * Created by dasu on 2017/5/2.
 *
 * 上一页，下一页的底部view
 */

public class MorePageView extends LinearLayout {

    private Context mContext;
    private TextView mPrePageTv;
    private TextView mNextPageTv;
    private TextView mCurPageTv;
    private Dialog mPageSelectDialog;
    private OnPageClickListener mPageClickListener;
    private List<String> mPageList;
    private String mCurPage = "1";

    public MorePageView(Context context) {
        this(context, null, 0);
    }

    public MorePageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MorePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_more_page, this, true);
        mPrePageTv = (TextView) view.findViewById(R.id.tv_pre_page);
        mCurPageTv = (TextView) view.findViewById(R.id.tv_current_page);
        mNextPageTv = (TextView) view.findViewById(R.id.tv_next_page);
        mPrePageTv.setOnClickListener(onPrePageClick());
        mCurPageTv.setOnClickListener(onCurPageClick());
        mNextPageTv.setOnClickListener(onNextPageClick());
        initDialog();
    }

    private OnClickListener onPrePageClick() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageList == null || mPageList.size() <= 1) {
                    ToastUtils.show(mContext, "没有更多了哦！");
                    return;
                }
                int page = Integer.parseInt(mCurPage);
                if (page <= 1) {
                    ToastUtils.show(mContext, "没有更多了哦！");
                    return;
                }
//                mCurPage = String.valueOf((page - 1));
//                mCurPageTv.setText(Html.fromHtml("<u>" + mCurPage + "</u>"));
                if (mPageClickListener != null) {
                    mPageClickListener.onPrePageClick();
                }
            }
        };
    }

    private OnClickListener onCurPageClick() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageList == null || mPageList.size() <= 1) {
                    ToastUtils.show(mContext, "没有更多了哦！");
                    return;
                }
                showSelectPageDialog();
            }
        };

    }

    private OnClickListener onNextPageClick() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageList == null || mPageList.size() <= 1) {
                    ToastUtils.show(mContext, "没有更多了哦！");
                    return;
                }
                int page = Integer.parseInt(mCurPage);
                if (page >= mPageList.size()) {
                    ToastUtils.show(mContext, "没有更多了哦！");
                    return;
                }
//                mCurPage = String.valueOf((page + 1));
//                mCurPageTv.setText(Html.fromHtml("<u>" + mCurPage + "</u>"));
                if (mPageClickListener != null) {
                    mPageClickListener.onNextPageClick();
                }
            }
        };
    }

    private PageSelectAdapter mSelectAdapter;
    private RecyclerView mPageSelectRv;

    private void initDialog() {
        mPageSelectDialog = new Dialog(mContext, R.style.dialog);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_page_select, null, false);
        mPageSelectRv = (RecyclerView) view.findViewById(R.id.rv_dialog_page_select);
        mPageSelectRv.setLayoutManager(new LinearLayoutManager(mContext));
        mSelectAdapter = new PageSelectAdapter();
        mPageSelectRv.setAdapter(mSelectAdapter);
        mPageSelectDialog.setContentView(view);
    }

    private void showSelectPageDialog() {
        if (mPageSelectDialog == null) {
            initDialog();
        }
        if (mPageSelectDialog.isShowing()) {
            return;
        }
        mPageSelectDialog.show();
        mSelectAdapter.notifyDataSetChanged();
        mPageSelectRv.scrollToPosition(Integer.parseInt(mCurPage));
        WindowManager.LayoutParams params = mPageSelectDialog.getWindow().getAttributes();
        mPageSelectDialog.getWindow().setGravity(Gravity.CENTER);
        params.width = ScreenUtils.dip2px(mContext, 70);
        params.height = 400;
        params.y = 300;
        mPageSelectDialog.getWindow().setAttributes(params);
    }

    public void setPageList(List<String> page) {
        mPageList = page;
    }

    public void setCurPage(String page) {
        mCurPage = page;
        if (mCurPageTv != null) {
            mCurPageTv.setText(Html.fromHtml("<u>" + page + "</u>"));
        }
    }

    public void setOnPageClickListener(OnPageClickListener listener) {
        mPageClickListener = listener;
    }

    public interface OnPageClickListener {
        void onNextPageClick();
        void onPrePageClick();
        void onPageSelected(String page);
    }

    private class PageSelectAdapter extends RecyclerView.Adapter<PageViewHolder> {

        @Override
        public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_page_select, parent, false);
            return new PageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PageViewHolder holder, int position) {
            final int posi = position;
            holder.pageTv.setText(mPageList.get(position));
            holder.pageTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPageSelectDialog != null) {
                        mPageSelectDialog.dismiss();
                    }
                    if (mPageClickListener != null) {
                        mPageClickListener.onPageSelected(mPageList.get(posi));
                    }
//                    mCurPage = mPageList.get(posi);
//                    mCurPageTv.setText(Html.fromHtml("<u>" + mCurPage + "</u>"));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPageList != null ? mPageList.size() : 0;
        }
    }

    private class PageViewHolder extends RecyclerView.ViewHolder {

        TextView pageTv;

        PageViewHolder(View itemView) {
            super(itemView);
            pageTv = (TextView) itemView.findViewById(R.id.tv_dialog_page);
        }
    }

}
