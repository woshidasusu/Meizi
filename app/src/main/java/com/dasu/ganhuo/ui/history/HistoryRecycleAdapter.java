package com.dasu.ganhuo.ui.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.category.GanHuoHelper;
import com.dasu.ganhuo.mode.logic.home.HtmlDataEntity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;
import com.dasu.ganhuo.ui.view.recyclerview.LoadMoreRecycleAdapter;
import com.dasu.ganhuo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dasu on 2017/4/18.
 */

class HistoryRecycleAdapter extends LoadMoreRecycleAdapter<HistoryRecycleAdapter.ViewHolder> {

    private Context mContext;
    private List<HtmlDataEntity> mDataList;
    private OnItemClickListener<HtmlDataEntity> mClickListener;

    HistoryRecycleAdapter(List<HtmlDataEntity> data) {
        mDataList = data;
    }

    @Override
    public int getDataSize() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        final HtmlDataEntity data = mDataList.get(position);
        final int posi = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(v, data, posi);
                }
            }
        });
        setTitle(holder.mTitleTv, data);
        setLabel(holder, data.getContent());
    }

    private void setTitle(TextView titleTv, HtmlDataEntity data) {
        String str = data.getTitle();
        String date = TimeUtils.formatDate(new Date(TimeUtils.adjustDate(data.getPublishedAt())));
        String title;
        if (str.contains("今日力推")) {
            title = str.replace("今日", date);
        } else {
            title = date + "力推：" + str;
        }
        titleTv.setText(title);
    }

    private void setLabel(ViewHolder holder, String content) {
        Pattern p = Pattern.compile("<h3>.*</h3>");
        Matcher matcher = p.matcher(content);
        int i=0;
        while (matcher.find() && i < 7) {
            String t = matcher.group();
            TextView tv = holder.getTextView(i);
            tv.setVisibility(View.VISIBLE);
            String type = t.substring(4, t.lastIndexOf("<")).trim();
            tv.setText(type);
            tv.setBackgroundColor(mContext.getResources().getColor(GanHuoHelper.getTypeColor(type)));
            i++;
        }
        for (;i < 7; i++) {
            TextView tv = holder.getTextView(i);
            tv.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<HtmlDataEntity> listener) {
        mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mLabel1Tv;
        TextView mLabel2Tv;
        TextView mLabel3Tv;
        TextView mLabel4Tv;
        TextView mLabel5Tv;
        TextView mLabel6Tv;
        TextView mLabel7Tv;
        List<TextView> mTextViewList;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_history_item_title);
            mLabel1Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type1);
            mLabel2Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type2);
            mLabel3Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type3);
            mLabel4Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type4);
            mLabel5Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type5);
            mLabel6Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type6);
            mLabel7Tv = (TextView) itemView.findViewById(R.id.tv_history_item_type7);
            mTextViewList = new ArrayList<>();
            mTextViewList.add(mLabel1Tv);
            mTextViewList.add(mLabel2Tv);
            mTextViewList.add(mLabel3Tv);
            mTextViewList.add(mLabel4Tv);
            mTextViewList.add(mLabel5Tv);
            mTextViewList.add(mLabel6Tv);
            mTextViewList.add(mLabel7Tv);
        }

        public TextView getTextView(int index) {
            if (mTextViewList != null && mTextViewList.size() > index) {
                return mTextViewList.get(index);
            }
            return null;
        }
    }
}
