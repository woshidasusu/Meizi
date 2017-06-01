package com.dasu.ganhuo.ui.reading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dasu.ganhuo.R;

import java.util.List;

/**
 * Created by dasu on 2017/4/30.
 */

class PageSpinnerAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mPages;

    PageSpinnerAdapter(Context context, List<String> pages) {
        super(context, android.R.layout.simple_spinner_item, pages);
        mContext = context;
        mPages = pages;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(mContext.getResources().getColor(R.color.gray));
        tv.setTextSize(13f);
        tv.setText(mPages.get(position));
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setTextColor(mContext.getResources().getColor(R.color.gray));
        tv.setTextSize(13f);
        tv.setText(mPages.get(position));
        return convertView;
    }
}
