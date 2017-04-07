package coder.dasu.meizi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.MeiziApplication;
import coder.dasu.meizi.R;
import coder.dasu.meizi.mode.dao.DataDao;
import coder.dasu.meizi.mode.entity.Data;
import coder.dasu.meizi.mode.entity.DayGank;
import coder.dasu.meizi.mode.entity.DayPublish;
import coder.dasu.meizi.mode.net.retrofit.GankRetrofit;
import coder.dasu.meizi.mode.entity.GankDayResponse;
import coder.dasu.meizi.utils.ListUtils;
import coder.dasu.meizi.utils.TimeUtils;
import coder.dasu.meizi.ui.view.RatioImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        List<Data> meizi = MeiziApplication.getDaoSession().getDataDao().queryBuilder().where(DataDao.Properties.Type.eq("福利")).list();
        List<Data> video = MeiziApplication.getDaoSession().getDataDao().queryBuilder().where(DataDao.Properties.Type.eq("休息视频")).list();
        Collections.sort(meizi);
        Collections.sort(video);
        int length = meizi.size() < video.size() ? meizi.size() : video.size();
        for (int i=0;i<length;i++) {
            DayGank dayGank = new DayGank();
            dayGank.setDescription(video.get(i).getDesc());
            dayGank.setImgUrl(meizi.get(i).getUrl());
            dayGank.setDate(TimeUtils.date2String(meizi.get(i).getPublishedAt(),TimeUtils.DAY_SDF));
            mDayGankList.add(dayGank);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_day_gank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String day = mDayPublishList.get(position).getDay();
        final String[] dayArr = day.split("-");
        if (hadDayGankData(day)) {
            initItemView(holder, position);
            return;
        }
        final int positi = position;
        Call<GankDayResponse> call =  GankRetrofit.getGankService().getDayGankData(dayArr[0], dayArr[1], dayArr[2]);
        call.enqueue(new Callback<GankDayResponse>() {
            @Override
            public void onResponse(Call<GankDayResponse> call, Response<GankDayResponse> response) {
                if (response.isSuccessful()) {
                    List<Data> android = response.body().results.android;
                    List<Data> app = response.body().results.app;
                    List<Data> expand= response.body().results.expand;
                    List<Data> ios= response.body().results.ios;
                    List<Data> meizi= response.body().results.meizi;
                    List<Data> push = response.body().results.push;
                    List<Data> video = response.body().results.video;
                    save2Database(android);
                    save2Database(app);
                    save2Database(expand);
                    save2Database(ios);
                    save2Database(meizi);
                    save2Database(push);
                    save2Database(video);
                    DayGank dayGank = new DayGank();
                    if (ListUtils.isNotEmpty(meizi, video)){
                        dayGank.setDate(TimeUtils.date2String(meizi.get(0).getPublishedAt(), TimeUtils.DAY_SDF));
                        dayGank.setImgUrl(meizi.get(0).getUrl());
                        dayGank.setDescription(video.get(0).getDesc());
                        mDayGankList.add(dayGank);
                        Collections.sort(mDayGankList);
                        initItemView(holder, positi);
                    } else {
                        holder.mItemView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<GankDayResponse> call, Throwable t) {
                holder.mItemView.setVisibility(View.GONE);
            }
        });

    }

    public boolean hadDayGankData(String date) {
        for (DayGank d: mDayGankList) {
            if (d.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    public void initItemView(final ViewHolder holder, int position) {
        if (mDayGankList.size() <= position) {
            return;
        }
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

    public void save2Database(List<Data> dataList) {
        if (ListUtils.isEmpty(dataList)) {
            return;
        }
        MeiziApplication.getDaoSession().getDataDao().insertOrReplaceInTx(dataList);
    }

    @Override
    public int getItemCount() {
        return mDayGankList.size() + 1;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, View picture, View text, DayGank dayGank);
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
                mItemClickListener.onItemClick(v, mDayGankImg, mItemView, mData);
            }
        }
    }

}
