package com.dasu.ganhuo.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.ui.base.ActivityStack;
import com.dasu.ganhuo.ui.base.BaseActivity;
import com.dasu.ganhuo.utils.LogUtils;

import java.io.File;

/**
 * Created by dasu on 2017/4/24.
 *
 * 干货文章阅读页
 */

public class WebViewActivity extends BaseActivity{
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weiview);
        initVariable();
        initView();
        mWebView.loadUrl(mGanHuoUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    private String mGanHuoUrl;
    private String mGanHuoTitle;
    private boolean isOnLoading;

    private void initVariable() {
        mGanHuoUrl = getIntent().getStringExtra("_url");
        mGanHuoTitle = getIntent().getStringExtra("_title");
        isOnLoading = false;
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("网页");
        mWebView = (WebView) findViewById(R.id.wv_webview_content);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_webview_load_progress);
        mProgressBar.setProgress(0);
        initWebView();
    }

    private void initWebView() {
        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");

        //设置缓存
        mWebView.getSettings().setDomStorageEnabled(true); //开启DOM storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true); //开启database storage API 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        mWebView.getSettings().setUseWideViewPort(true);  //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //设置WebView视图大小与HTML中viewport Tag的关系
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //设置支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        File cacheFile = mContext.getCacheDir();
        if (cacheFile != null) {
            mWebView.getSettings().setAppCachePath(cacheFile.getAbsolutePath());
        }
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mWebView.getContext().startActivity(intent);
            }
        });
        //辅助webView处理各种通知、请求事件
        mWebView.setWebViewClient(new MyWebViewClient());
        //辅助WebView处理js的弹窗响应等
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private void showProgressBar() {
        if (mProgressBar != null && mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("_url", url);
        intent.putExtra("_title", title);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActivityStack.getInstance().popAndFinishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    //WebViewClient就是帮助WebView处理各种通知、请求事件的。
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.d(TAG, "url: " + url);
            isOnLoading = false;
            showProgressBar();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.d(TAG, "page Start");
            isOnLoading = true;
            showProgressBar();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            LogUtils.d(TAG, "page finish");
            if (isOnLoading) {
                mProgressBar.setProgress(0);
                mProgressBar.setVisibility(View.GONE);
                isOnLoading = false;
            }
        }

        //        shouldOverrideUrlLoading(WebView view, String url)  最常用的，比如上面的。
//        //在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
//        //比如获取url，查看url.contains(“add”)，进行添加操作
//
//        shouldOverrideKeyEvent(WebView view, KeyEvent event)
//        //重写此方法才能够处理在浏览器中的按键事件。
//
//        onPageStarted(WebView view, String url, Bitmap favicon)
//        //这个事件就是开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
//
//        onPageFinished(WebView view, String url)
//        //在页面加载结束时调用。同样道理，我们可以关闭loading 条，切换程序动作。
//
//        onLoadResource(WebView view, String url)
//        // 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
//
//        onReceivedError(WebView view, int errorCode, String description, String failingUrl)
//        // (报告错误信息)
//
//        doUpdateVisitedHistory(WebView view, String url, boolean isReload)
//        //(更新历史记录)
//
//        onFormResubmission(WebView view, Message dontResend, Message resend)
//        //(应用程序重新请求网页数据)
//
//        onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host,String realm)
//        //（获取返回信息授权请求）
//
//        onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
//        //重写此方法可以让webview处理https请求。
//
//        onScaleChanged(WebView view, float oldScale, float newScale)
//        // (WebView发生改变时调用)
//
//        onUnhandledKeyEvent(WebView view, KeyEvent event)
//        //（Key事件未被加载时调用）
    }

    //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            LogUtils.d(TAG, "progress: " + newProgress);
            if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
                mProgressBar.setProgress(newProgress);
            }
        }
//        //获取Web页中的title用来设置自己界面中的title
//        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
//        //因此建议当触发onReceiveError时，不要使用获取到的title
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//        }
//
//        @Override
//        public void onReceivedIcon(WebView view, Bitmap icon) {
//            //
//        }
//
//        @Override
//        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            //
//            return true;
//        }
//
//        @Override
//        public void onCloseWindow(WebView window) {
//        }
//
//        //处理alert弹出框，html 弹框的一种方式
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
//
//        //处理confirm弹出框
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
//                result) {
//            //
//            return true;
//        }
//
//        //处理prompt弹出框
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//            //
//            return true;
//        }
    }

}
