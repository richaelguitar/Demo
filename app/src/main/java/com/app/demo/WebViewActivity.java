package com.app.demo;


import android.graphics.Color;
import android.os.Bundle;

import com.app.demo.basic.BasicWebViewActivity;
import com.app.demo.jsinterface.H5JavaInterFaceImpl;
import com.app.demo.widgets.WebViewProgressBar;

import org.apache.cordova.engine.SystemWebView;


/**
 * H5页面控制器
 * @author  C xiaoww
 * @since  2019/11/11.
 */

public class WebViewActivity extends BasicWebViewActivity {

    public static  final String TAG = "WebViewActivity";


    WebViewProgressBar webViewProgressBar;

    SystemWebView webView;



    private String mUrl = "file:///android_asset/video.html";

    @Override
    public void getBundleExtras(Bundle bundle) {
        super.getBundleExtras(bundle);
        mUrl = bundle.getString("url");
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.webview_layout;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public String getLaunchUrl() {
        return mUrl;
    }

    @Override
    public SystemWebView getSystemWebView() {
        webView = findViewById(R.id.web_view);
        //注入js
        webView.addJavascriptInterface(new H5JavaInterFaceImpl(this),"H5JavaInterface");
        return webView;
    }

    @Override
    public WebViewProgressBar getProgressBar() {
        webViewProgressBar = findViewById(R.id.web_progressbar);
        webViewProgressBar.setColor(Color.parseColor("#FF01C3A6"));
        return webViewProgressBar;
    }

    @Override
    public void getTitle(String title) {

    }


}
