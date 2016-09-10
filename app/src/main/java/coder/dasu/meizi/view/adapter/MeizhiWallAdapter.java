package coder.dasu.meizi.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.Meizhi;
import coder.dasu.meizi.listener.OnItemClickListener;

/**
 * Created by sxq on 2016/9/10.
 */
public class MeizhiWallAdapter extends RecyclerView.Adapter<MeizhiWallAdapter.ViewHolder> {

    private List<Meizhi> mMeizhiList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public MeizhiWallAdapter(Context context, List<Meizhi> list) {
        mContext = context;
        mMeizhiList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi_wall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mMeizhi = mMeizhiList.get(position);
        
        Picasso.with(mContext)
                .load(holder.mMeizhi.getImgUrl())
                .error(holder.mMeizhi.getResId())
                .placeholder(R.drawable.meizhi1)
                .into(holder.meizhiImg);
        holder.meizhiText.setText(holder.mMeizhi.getText());
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
        ImageView meizhiImg;
        @InjectView(R.id.meizhi_tv)
        TextView meizhiText;

        View mItemView;
        Meizhi mMeizhi;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mItemView = itemView;
            mItemView.setOnClickListener(this);
            meizhiImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, meizhiImg, mItemView, mMeizhi);
            }
        }
    }
}
