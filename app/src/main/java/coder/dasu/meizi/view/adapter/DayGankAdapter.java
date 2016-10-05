package coder.dasu.meizi.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.MeiziApp;
import coder.dasu.meizi.R;
import coder.dasu.meizi.data.dao.DataDao;
import coder.dasu.meizi.data.entity.Data;
import coder.dasu.meizi.data.entity.DayGank;
import coder.dasu.meizi.data.entity.DayPublish;
import coder.dasu.meizi.listener.OnItemClickListener;
import coder.dasu.meizi.utils.TimeUtils;
import coder.dasu.meizi.view.widgets.RatioImageView;

/**
 * ddfasdfsf
 */
public class DayGankAdapter extends RecyclerView.Adapter<DayGankAdapter.ViewHolder> {

    private static final String TAG = DayGankAdapter.class.getSimpleName();

    private Context mContext;
    private List<DayPublish> mDayPublishList;
    private OnItemClickListener mItemClickListener;
    private List<DayGank> mDayGankList;


    public DayGankAdapter(Context context, List<DayPublish> data) {
        mContext = context;
        mDayPublishList = data;
        mDayGankList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_day_gank, parent, false);
        return new ViewHolder(view);
    }

    static int i = 0;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String day = mDayPublishList.get(position).getDay();
        final String[] dayArr = day.split("-");
        DataDao dataDao = MeiziApp.getDaoSession().getDataDao();
        Date time = TimeUtils.string2Date(day, TimeUtils.DAY_SDF);
        Log.e("!!!!!!!!!!!","count : " + i++);
        Data meizi = dataDao.queryBuilder()
                .where(DataDao.Properties.Type.eq("福利"))
                .limit(1).list().get(0);
        Data video = dataDao.queryBuilder()
                .where(DataDao.Properties.Type.eq("休息视频"))
                .limit(1).list().get(0);
        Glide.with(mContext)
                .load(meizi.getUrl())
                .centerCrop()
                .into(holder.mDayGankImg)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.mItemView.isShown()) {
                            holder.mItemView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        holder.mDayGankTitle.setText(video.getDesc());


    }

    public void initItemView(final ViewHolder holder, int position) {
        DayGank data = mDayGankList.get(position);
        holder.mData = data;

        Glide.with(mContext)
                .load(data.getImgUrl())
                .centerCrop()
                .into(holder.mDayGankImg)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.mItemView.isShown()) {
                            holder.mItemView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        holder.mDayGankTitle.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return mDayPublishList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @InjectView(R.id.day_gank_img)
        RatioImageView mDayGankImg;
        @InjectView(R.id.day_gank_title)
        TextView mDayGankTitle;

        DayGank mData;
        View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            mItemView = itemView;
            mItemView.setOnClickListener(this);
            mDayGankImg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, mDayGankImg, mItemView, null);
            }
        }
    }

}
