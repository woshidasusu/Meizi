package com.dasu.ganhuo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.GanHuoEntity;
import com.dasu.ganhuo.mode.logic.category.GanHuoHelper;
import com.dasu.ganhuo.mode.logic.home.SomedayGanHuoEntity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.history.HistoryActivity;
import com.dasu.ganhuo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dasu on 2017/4/17.
 */

class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = HomeRecycleAdapter.class.getSimpleName();
    private static final int TYPE_DATA_VIEW = 1;
    private static final int TYPE_FOOT_VIEW = 2;
    private static final int TYPE_EMPTY_VIEW = 3;

    private List<GanHuoEntity> mDataList;
    private OnItemClickListener<GanHuoEntity> mClickListener;
    private Context mContext;

    public HomeRecycleAdapter(SomedayGanHuoEntity data) {
        setData(data);
    }

    @Override
    public int getItemCount() {
        return mDataList != null && mDataList.size() > 0 ? mDataList.size() + 1 : 2;
    }

    @Override
    public int getItemViewType(int position) {
        if ((mDataList == null || mDataList.size() == 0)) {
            return position == 0 ? TYPE_EMPTY_VIEW : TYPE_FOOT_VIEW;
        }
        if (mDataList != null && mDataList.size() > 0 && position == mDataList.size()) {
            return TYPE_FOOT_VIEW;
        }
        return TYPE_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == TYPE_FOOT_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_footview, parent, false);
            return new FootViewHolder(view);
        }
        if (viewType == TYPE_EMPTY_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_data_empty, parent, false);
            return new EmptyViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vHolder = (ViewHolder) holder;
            final GanHuoEntity data = mDataList.get(position);
            final int posi = position;
            vHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, data, posi);
                    }
                }
            });
            vHolder.mTitleTv.setText(data.getDesc());
            vHolder.mAuthorTv.setText(data.getWho());
            setSource(data.getUrl(), vHolder.mSourceTv);
            setType(data.getType(), vHolder.mTypeTv);
            setDate(data.getPublishedAt(), vHolder.mDateTv);
        } else if (holder instanceof EmptyViewHolder) {
            EmptyViewHolder eHolder = (EmptyViewHolder) holder;
            if (mEmptyImgId > 0) {
                eHolder.emptyImg.setImageResource(mEmptyImgId);
            }
            if (!TextUtils.isEmpty(mEmptyTip)) {
                eHolder.emptyTip.setText(mEmptyTip);
            }
        }
    }

    private void setType(String type, TextView tv) {
        tv.setBackgroundColor(mContext.getResources().getColor(GanHuoHelper.getTypeColor(type)));
        tv.setText(type);
    }

    private void setSource(String url, TextView tv) {
        tv.setBackgroundColor(mContext.getResources().getColor(GanHuoHelper.getSourceColor(url)));
        tv.setText(GanHuoHelper.getUrlSource(url));
    }

    private void setDate(Date date, TextView tv) {
        String time = TimeUtils.howLongAgo(TimeUtils.adjustDate(date));
        tv.setText(time);
    }

    public void setData(SomedayGanHuoEntity data) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (data == null || data.getResults() == null) {
            return;
        }
        mDataList.clear();
        for (List<GanHuoEntity> l : data.getResults().getAllList()) {
            for (GanHuoEntity g : l) {
                mDataList.add(g);
            }
        }
        notifyDataSetChanged();
    }

    private int mEmptyImgId = -1;

    public void setEmptyImg(int resId) {
        mEmptyImgId = resId;
        notifyDataSetChanged();
    }

    private String mEmptyTip;

    public void setEmptyTip(String text) {
        mEmptyTip = text;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<GanHuoEntity> listener) {
        mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTv;
        TextView mTypeTv;
        TextView mSourceTv;
        TextView mAuthorTv;
        TextView mDateTv;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_home_item_title);
            mTypeTv = (TextView) itemView.findViewById(R.id.tv_home_item_type);
            mAuthorTv = (TextView) itemView.findViewById(R.id.tv_home_item_author);
            mDateTv = (TextView) itemView.findViewById(R.id.tv_home_item_date);
            mSourceTv = (TextView) itemView.findViewById(R.id.tv_home_item_source);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        ImageView mFootIv;
        TextView mFootTipTv;

        FootViewHolder(View itemView) {
            super(itemView);
            mFootIv = (ImageView) itemView.findViewById(R.id.iv_home_fv_icon);
            mFootTipTv = (TextView) itemView.findViewById(R.id.tv_home_fv_tip);
            mFootTipTv.setText(Html.fromHtml("<u>点击跳往历史推荐查看更多干货</u>"));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, HistoryActivity.class));
                }
            });
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        ImageView emptyImg;
        TextView emptyTip;

        EmptyViewHolder(View itemView) {
            super(itemView);
            emptyImg = (ImageView) itemView.findViewById(R.id.iv_view_data_empty);
            emptyTip = (TextView) itemView.findViewById(R.id.tv_view_data_empty);
        }
    }
}
