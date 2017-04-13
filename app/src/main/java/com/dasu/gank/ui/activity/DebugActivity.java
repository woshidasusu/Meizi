package com.dasu.gank.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dasu.gank.R;

/**
 * Created by suxq on 2017/4/13.
 */

public class DebugActivity extends Activity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }

    public void getBlogApi(View view) {

    }
}
