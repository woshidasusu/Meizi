package coder.dasu.meizi.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.bean.Data;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.view.widgets.RatioImageView;

/**
 * Created by dasu on 2016/9/28.
 * https://github.com/woshidasusu/Meizi
 */
public class AndroidDataAdapter extends RecyclerView.Adapter<AndroidDataAdapter.ViewHolder> {

    private OnItemClickListener mItemClickListener;

    public Context mContext;
    public List<Data> mDataList;

    public AndroidDataAdapter(Context context, List<Data> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_android, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mData = mDataList.get(position);
        holder.mItemTitle.setText(holder.mData.getDesc());
        holder.mItemMessage.setText(holder.mData.getUrl());
        holder.mItemAuthor.setText(holder.mData.getWho());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.android_item_image)
        RatioImageView mItemImage;
        @InjectView(R.id.android_item_title)
        TextView mItemTitle;
        @InjectView(R.id.android_item_message)
        TextView mItemMessage;
        @InjectView(R.id.android_item_author)
        TextView mItemAuthor;

        Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, mItemImage, mItemTitle, mData);
            }
        }
    }
}
