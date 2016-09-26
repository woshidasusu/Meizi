package coder.dasu.meizi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
public class WebDataFragment extends SwipeRefreshFragment {

    private static final String TAG = WebDataFragment.class.getSimpleName();

    public WebDataFragment(String value) {
        super(value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void loadData() {

    }
}
