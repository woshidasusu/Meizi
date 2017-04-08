package com.dasu.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import com.dasu.gank.R;
import com.dasu.gank.mode.entity.Data;

/**
 * Created by dasu on 2016/10/9.
 * ttps://github.com/woshidasusu/Meizi
 */
public class DayGankDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DayGankDetailAdapter.class.getSimpleName();

    private static final int VIDEO_ITEM = 1;
    private static final int DATA_ITEM = 2;

    private Context mContext;
    private List<Data> mDataList;

    public DayGankDetailAdapter(Context context, List<Data> data) {
        mContext = context;
        mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIDEO_ITEM:
                return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_day_detail_video, parent, false));
            case DATA_ITEM:
                return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_day_detail_data, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int dataPosition = position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIDEO_ITEM;
        } else {
            return DATA_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }


    class VideoViewHolder extends RecyclerView.ViewHolder{

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        public DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
