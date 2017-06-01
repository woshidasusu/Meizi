package com.dasu.ganhuo.ui.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasu.ganhuo.R;

/**
 * Created by dasu on 2017/4/26.
 *
 * 带有Footview布局的上拉加载更多的RecyclerAdapter
 */

public abstract class LoadMoreRecycleAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DATA_VIEW = 1;
    private static final int TYPE_FOOT_VIEW = 2;

    private FootViewHolder mFootViewHolder;

    //模仿使用RecycleAdapter的习惯，新开三个抽象方法
    public abstract int getDataSize();
    public abstract VH onCreateViewHolder(ViewGroup parent);
    public abstract void onBindView(VH holder, int position);

    @Override
    public int getItemCount() {
        //无数据时就不加载footview布局
        return getDataSize() > 0 ? getDataSize() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && position == getDataSize()) {
            return TYPE_FOOT_VIEW;
        } else {
            return TYPE_DATA_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more_footview, parent, false);
            mFootViewHolder = new FootViewHolder(view);
            return mFootViewHolder;
        }
        return onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0 && position == getDataSize()) {
            //nothing to do
        } else {
            onBindView((VH) holder, position);
        }
    }

    private OnPullUpRefreshListener mRefreshListener;

    public void setOnPullUpRefresh(OnPullUpRefreshListener listener) {
        mRefreshListener = listener;
    }

    public void setFootViewVisibility(int visibility) {
        if (mFootViewHolder != null) {
            mFootViewHolder.itemView.setVisibility(visibility);
        }
    }

    public void setFootIcon(int resId) {
        if (mFootViewHolder != null) {
            mFootViewHolder.mLoadImg.setImageResource(resId);
        }
    }

    public void setFootTip(String text) {
        if (mFootViewHolder != null) {
            mFootViewHolder.mLoadTipTv.setText(text);
        }
    }

    public View getFootView() {
        if (mFootViewHolder != null) {
            return mFootViewHolder.itemView;
        }
        return null;
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        ImageView mLoadImg;
        TextView mLoadTipTv;

        FootViewHolder(View itemView) {
            super(itemView);
            mLoadImg = (ImageView) itemView.findViewById(R.id.iv_load_icon);
            mLoadTipTv = (TextView) itemView.findViewById(R.id.tv_load_tip);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRefreshListener != null) {
                        mRefreshListener.onPullUpRefresh();
                    }
                }
            });
        }
    }
}
