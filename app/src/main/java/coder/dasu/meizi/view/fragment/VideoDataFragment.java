package coder.dasu.meizi.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import coder.dasu.meizi.R;
import coder.dasu.meizi.view.base.SwipeRefreshFragment;

/**
 * Created by dasu on 2016/9/26.
 * https://github.com/woshidasusu/Meizi
 */
public class VideoDataFragment extends SwipeRefreshFragment {

    private static final String TAG = VideoDataFragment.class.getSimpleName();

    public VideoDataFragment(String value) {
        super(value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void loadData() {

    }
}
