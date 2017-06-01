package com.dasu.ganhuo.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dasu.ganhuo.R;
import com.dasu.ganhuo.ui.base.SubpageWithToolbarActivity;
import com.dasu.ganhuo.ui.home.WebViewActivity;
import com.dasu.ganhuo.ui.view.GlideCircleTransform;

/**
 * Created by dasu on 2017/4/17.
 */

public class AboutActivity extends SubpageWithToolbarActivity {

    private static final String GITHUB = "https://github.com/woshidasusu";
    private static final String JIANSHU = "http://www.jianshu.com/u/bb52a2918096";

    @Override
    public String getToolbarTitle() {
        return "关于";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private ImageView mMeIv;
    private TextView mProjectTv;
    private TextView mThanksTv;
    private TextView mGithubTv;
    private TextView mJianshuTv;

    private void initView() {
        //init imageview me
        mMeIv = (ImageView) findViewById(R.id.iv_about_me);
        Glide.with(this).load(R.drawable.img_me)
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(mMeIv);
        //init textview
        mProjectTv = (TextView) findViewById(R.id.tv_about_project);
        mThanksTv = (TextView) findViewById(R.id.tv_about_thanks);
        mGithubTv = (TextView) findViewById(R.id.tv_about_github);
        mJianshuTv = (TextView) findViewById(R.id.tv_about_jianshu);
        mGithubTv.setOnClickListener(onGithubClick());
        mJianshuTv.setOnClickListener(onJianshuClick());
        setText();
    }

    private View.OnClickListener onGithubClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startActivity(mContext, GITHUB, "Github");
            }
        };
    }

    private View.OnClickListener onJianshuClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startActivity(mContext, JIANSHU, "简书");
            }
        };
    }

    private String mProjectIntroduction =
            "项目地址: " + "\r\n" +
                    "         https://github.com/woshidasusu/GanHuo" + "\r\n\r\n" +
                    "第三方库: " + "\r\n" + "" +
                    "         okhttp + retrofit （网络访问）" + "\r\n" +
                    "         gson （json数据解析）" + "\r\n" +
                    "         jsoup （Html解析）" + "\r\n" +
                    "         glide （图片加载）" + "\r\n" +
                    "         photoview  (图片查看，支持手势缩放)" + "\r\n\r\n" +
                    "说明: " + "\r\n" +
                    "         该项目旨在增加自己的编程实践，因此第三方库的使用只选择最基本的功能，比如网络访问，图片加载这些" +
                    "这些无法自己实现的功能，其他包括数据库等都尝试自己进行封装实现。另外，项目架构虽然只是MVC模式，但基本都是" +
                    "按照功能模块来进行划分，ui逻辑和业务逻辑也尽可能的分离，底层数据库模块和网络访问模块都进行了封装，严格控制了" +
                    "访问权限，尽可能对外部隐藏其模块的实现细节，欢迎star交流、指点。" + "\r\n";

    private String mThanksBy =
            "鸣谢: " + "\r\n" +
                    "   @drakeet/Meizhi" + "\r\n" +
                    "   @burgessjp/GanHuoIO" + "\r\n" +
                    "   @CaMnter/EasyGank" + "\r\n";

    private void setText() {
        mGithubTv.setText(Html.fromHtml("<u>" + GITHUB + "</u>"));
        mJianshuTv.setText(Html.fromHtml("<u>" + JIANSHU + "</u>"));
        mProjectTv.setText(mProjectIntroduction);
        mThanksTv.setText(mThanksBy);
    }
}
