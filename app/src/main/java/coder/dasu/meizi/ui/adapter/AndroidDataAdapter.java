package coder.dasu.meizi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.mode.entity.Data;
import coder.dasu.meizi.ui.base.OnItemClickListener;
import coder.dasu.meizi.utils.TimeUtils;
import coder.dasu.meizi.ui.view.RatioImageView;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mData = mDataList.get(position);

        Glide.with(mContext)
                .load(R.drawable.meizhi1)
                .centerCrop()
                .into(holder.mItemImage)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.mItemView.isShown()) {
                            holder.mItemView.setVisibility(View.VISIBLE);
                        }
                    }
                });

        holder.mItemTitle.setText(holder.mData.getDesc());
        holder.mItemPublishTime.setText(TimeUtils.date2String(holder.mData.getPublishedAt(), TimeUtils.DAY_SDF));
        String author = TextUtils.isEmpty(holder.mData.getWho())
                ? mContext.getString(R.string.gank_data_unkonwn_who)
                : holder.mData.getWho();
        holder.mItemAuthor.setText(author);
        holder.mItemType.setText(holder.mData.getType());
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
        @InjectView(R.id.android_item_publish_time)
        TextView mItemPublishTime;
        @InjectView(R.id.android_item_author)
        TextView mItemAuthor;
        @InjectView(R.id.android_item_type)
        TextView mItemType;

        View mItemView;
        Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
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
