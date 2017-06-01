package com.dasu.ganhuo.ui.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.mode.logic.category.GanHuoHelper;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.view.recyclerview.LoadMoreRecycleAdapter;
import com.dasu.ganhuo.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2017/4/18.
 */

class VideoRecycleAdapter extends LoadMoreRecycleAdapter<VideoRecycleAdapter.ViewHolder> {

    private List<GanHuoEntity> mDataList;
    private Context mContext;
    private OnItemClickListener<GanHuoEntity> mClickListener;

    public VideoRecycleAdapter(List<GanHuoEntity> data) {
        mDataList = data;
    }

    @Override
    public int getDataSize() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        final GanHuoEntity data = mDataList.get(position);
        final int posi = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, data, posi);
                }
            }
        });
        holder.mTitleTv.setText(data.getDesc());
        setSource(data.getUrl(), holder.mSourceTv);
        setDate(data.getPublishedAt(), holder.mDateTv);
    }

    private void setSource(String url, TextView tv) {
        tv.setBackgroundColor(mContext.getResources().getColor(GanHuoHelper.getSourceColor(url)));
        tv.setText(GanHuoHelper.getUrlSource(url));
    }

    private void setDate(Date date, TextView tv) {
        String time = TimeUtils.howLongAgo(TimeUtils.adjustDate(date));
        tv.setText(time);
    }

    public void setOnItemClickListener(OnItemClickListener<GanHuoEntity> listener) {
        mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTv;
        TextView mSourceTv;
        TextView mDateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_video_item_title);
            mDateTv = (TextView) itemView.findViewById(R.id.tv_video_item_date);
            mSourceTv = (TextView) itemView.findViewById(R.id.tv_video_item_source);
        }
    }
}
