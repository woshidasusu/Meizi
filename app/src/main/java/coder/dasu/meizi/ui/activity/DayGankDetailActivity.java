package coder.dasu.meizi.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import coder.dasu.meizi.R;
import coder.dasu.meizi.ui.base.BaseActivity;

/**
 * Created by dasu on 2016/10/9.
 * ttps://github.com/woshidasusu/Meizi
 */
public class DayGankDetailActivity extends BaseActivity {

    private static final String TAG = DayGankDetailActivity.class.getSimpleName();

    @InjectView(R.id.rv_day_detail)
    RecyclerView mDayDetaiView;

    @Override
    public int provideContentView() {
        return R.layout.activity_day_gank_detail;
    }


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreae(savedInstanceState);
//        ButterKnife.inject(this);
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }
}
