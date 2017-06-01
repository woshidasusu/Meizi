package com.dasu.ganhuo.ui.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.logic.history.SomedayEntity;
import com.dasu.ganhuo.ui.base.OnItemClickListener;

import java.util.List;

/**
 * Created by dasu on 2017/4/27.
 */

class SomedayRecycleAdapter extends RecyclerView.Adapter<SomedayRecycleAdapter.ViewHolder>{

    private Context mContext;
    private List<SomedayEntity> mDataList;
    private OnItemClickListener<String> mClickListener;

    public SomedayRecycleAdapter(List<SomedayEntity> data) {
        mDataList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_someday, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SomedayEntity someday = mDataList.get(position);
        holder.typeTv.setText(someday.getTitle());
        holder.typeContentLayout.removeAllViews();
        final int posi = position;
        for (final SomedayEntity.Type type : someday.getTypes()) {
            TextView tv = new TextView(mContext);
            tv.setTextColor(mContext.getResources().getColor(R.color.gray));
            tv.setTextSize(15);
            tv.setGravity(Gravity.LEFT);
            tv.setText("· " + type.getText());
            tv.setPadding(0, 10, 0, 10);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, type.getHref(), posi);
                    }
                }
            });
            holder.typeContentLayout.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener<String> listener) {
        mClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView typeTv;
        ViewGroup typeContentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            typeTv = (TextView) itemView.findViewById(R.id.tv_someday_type);
            typeContentLayout = (ViewGroup) itemView.findViewById(R.id.layout_type_content);
        }
    }

}
