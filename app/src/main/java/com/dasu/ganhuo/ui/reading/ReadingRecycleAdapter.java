package com.dasu.ganhuo.ui.reading;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.reading.BlogEntity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.view.GlideCircleTransform;
import com.dasu.ganhuo.ui.view.MorePageView;

import java.util.List;

/**
 * Created by dasu on 2017/4/22.
 */

class ReadingRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ReadingRecycleAdapter.class.getSimpleName();

    private Context mContext;
    private List<BlogEntity> mDataList;
    private OnItemClickListener<BlogEntity> mClickListener;
    private MorePageView.OnPageClickListener mPageClickListener;
    private List<String> mPages;
    private String mCurPage;

    ReadingRecycleAdapter(List<BlogEntity> blogList) {
        mDataList = blogList;
    }

    private static final int TYPE_FOOT_VIEW = 1;
    private static final int TYPE_DATA_VIEW = 2;

    @Override
    public int getItemCount() {
        return mDataList != null && mDataList.size() > 0 ? mDataList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && mDataList.size() == position) {
            return TYPE_FOOT_VIEW;
        }
        return TYPE_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == TYPE_FOOT_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_reading_footview, parent, false);
            return new FootViewHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_reading, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final BlogEntity data = mDataList.get(position);
            final int posi = position;
            ViewHolder vHolder = (ViewHolder) holder;
            vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, data, posi);
                    }
                }
            });
            vHolder.titleTv.setText(data.getTitle());
            vHolder.dateTv.setText(data.getDate());
            vHolder.sourceNameTv.setText(data.getSource());
            Glide.with(mContext)
                    .load(data.getSourceIcon())
                    .transform(new GlideCircleTransform(mContext))
                    .into(vHolder.sourceIconIv);
        } else if (holder instanceof FootViewHolder) {
            final FootViewHolder fvHolder = (FootViewHolder) holder;
            fvHolder.footView.setOnPageClickListener(new MorePageView.OnPageClickListener() {
                @Override
                public void onNextPageClick() {
                    if (mPageClickListener != null) {
                        mPageClickListener.onNextPageClick();
                    }
                }

                @Override
                public void onPrePageClick() {
                    if (mPageClickListener != null) {
                        mPageClickListener.onPrePageClick();
                    }
                }

                @Override
                public void onPageSelected(String page) {
                    if (mPageClickListener != null) {
                        mPageClickListener.onPageSelected(page);
                    }
                }
            });
            fvHolder.footView.setPageList(mPages);
            fvHolder.footView.setCurPage(mCurPage);
        }
    }

    public void setOnPageClickListener(MorePageView.OnPageClickListener listener) {
        mPageClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener<BlogEntity> listener) {
        mClickListener = listener;
    }

    public void updatePages(List<String> pages) {
        mPages = pages;
        notifyItemChanged(mDataList.size());
    }

    public void updateCurPage(String page) {
        mCurPage = page;
        notifyItemChanged(mDataList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView sourceIconIv;
        TextView sourceNameTv;
        TextView dateTv;
        TextView titleTv;

        ViewHolder(View itemView) {
            super(itemView);
            sourceIconIv = (ImageView) itemView.findViewById(R.id.iv_reading_source);
            sourceNameTv = (TextView) itemView.findViewById(R.id.tv_reading_source);
            dateTv = (TextView) itemView.findViewById(R.id.tv_reading_date);
            titleTv = (TextView) itemView.findViewById(R.id.tv_reading_title);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        MorePageView footView;

        FootViewHolder(View itemView) {
            super(itemView);
            footView = (MorePageView) itemView.findViewById(R.id.view_reading_footview);
        }
    }
}
