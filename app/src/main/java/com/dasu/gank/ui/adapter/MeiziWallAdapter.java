package com.dasu.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.dasu.gank.R;
import com.dasu.gank.mode.entity.Data;
import com.dasu.gank.ui.base.OnItemClickListener;
import com.dasu.gank.ui.view.RatioImageView;

/**
 * Created by sxq on 2016/9/10.
 */
public class MeiziWallAdapter extends RecyclerView.Adapter<MeiziWallAdapter.ViewHolder> {

    private List<Data> mMeizhiList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public MeiziWallAdapter(Context context, List<Data> list) {
        mContext = context;
        mMeizhiList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi_wall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mMeizhi = mMeizhiList.get(position);

        holder.meizhiText.setText(holder.mMeizhi.getDesc());

        Glide.with(mContext)
                .load(holder.mMeizhi.getUrl())
                .centerCrop()
                .into(holder.meizhiImg)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.mItemView.isShown()) {
                            holder.mItemView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMeizhiList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.meizhi_img)
        RatioImageView meizhiImg;
        @InjectView(R.id.meizhi_tv)
        TextView meizhiText;

        View mItemView;
        Data mMeizhi;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mItemView = itemView;
            mItemView.setOnClickListener(this);
            meizhiImg.setOnClickListener(this);
            meizhiImg.setOriginSize(50, 50);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, meizhiImg, mItemView, mMeizhi);
            }
        }
    }
}
