package com.app.demo.basic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.app.demo.widget.WebViewProgressBar;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginManager;
import org.apache.cordova.Whitelist;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * H5控制器封装
 * @author xiaoww
 * @since 2019-11-11
 */
public abstract class BasicWebViewActivity extends AppCompatActivity {

    private String TAG = "BasicWebViewActivity";
    private String launchUrl;
    protected CordovaWebView mWebInterface;
    private SystemWebView mSystemWebView;
    protected CordovaInterfaceImpl mCordovaInterface;
    protected SystemWebViewEngine mSystemWebViewEngine;
    protected CordovaPreferences mPreferences;

    private WebViewProgressBar webViewProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        launchUrl = getLaunchUrl();
        setContentView(getContentViewLayoutId());
        initView();
        initData();
        setup();
    }

    /**
     * onCreate's bundle
     */
    public void getBundleExtras(Bundle bundle) {
    }

    public void initView() {
    }

    public void initData() {
    }

    public abstract int getContentViewLayoutId();

    public abstract String getLaunchUrl();

    public abstract SystemWebView getSystemWebView();

    public abstract WebViewProgressBar getProgressBar();

    public abstract void getTitle(String title);


    private void setup() {
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(this);

        webViewProgressBar = getProgressBar();
        mSystemWebView = getSystemWebView();
        mCordovaInterface = new KCordovaInterfaceImpl(this);
        mSystemWebViewEngine = new SystemWebViewEngine(mSystemWebView);
        mWebInterface = new CordovaWebViewImpl(mSystemWebViewEngine);
        mPreferences = parser.getPreferences();
        mWebInterface.init(mCordovaInterface, parser.getPluginEntries(), mPreferences);

        mSystemWebView.setWebViewClient(new ReWebViewClient(mSystemWebViewEngine));
        mSystemWebView.setWebChromeClient(new ReWebChromeClient(mSystemWebViewEngine));
        mSystemWebView.loadUrl(launchUrl);
    }

    private boolean isInWhiteList() {
        Whitelist whitelist = new Whitelist();
        return whitelist.isUrlWhiteListed(launchUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PluginManager pluginManager = mWebInterface.getPluginManager();
        if (pluginManager != null) {
            pluginManager.onDestroy();
        }
        if (mWebInterface != null) {
            mWebInterface.handleDestroy();
        }
    }

    private void hideProgressBar() {
        if (webViewProgressBar != null) {
            webViewProgressBar.setVisibility(View.GONE);
        }
    }


    private void showProgressBar() {
        if (webViewProgressBar != null) {
            webViewProgressBar.setVisibility(View.VISIBLE);
        }
    }


    private void setWebViewProgress(int progress) {
        if (webViewProgressBar != null) {
            webViewProgressBar.setProgress(progress);
        }
    }

    public void onReceivedError(final int errorCode, final String description, final String failingUrl) {
        if (isInWhiteList()) {
            //In whitelist
            // If errorUrl specified, then load it
            final String errorUrl = mPreferences.getString("errorUrl", null);
            if ((errorUrl != null) && (!failingUrl.equals(errorUrl)) && (mSystemWebView != null)) {
                mSystemWebView.loadUrl(errorUrl);
            } else {
                // If not, then display error dialog
            }
        } else {
            //Not in whitelist
            Toast.makeText(this, "Not in whitelist", Toast.LENGTH_SHORT).show();
        }
    }


    private class KCordovaInterfaceImpl extends CordovaInterfaceImpl {

        public KCordovaInterfaceImpl(Activity activity) {
            super(activity);
        }


        @Override
        public Object onMessage(String id, Object data) {
            Log.d(TAG, "============" + id);
            if ("onReceivedError".equals(id)) {
                JSONObject d = (JSONObject) data;
                try {
                    BasicWebViewActivity.this.onReceivedError(d.getInt("errorCode"), d.getString("description"), d.getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if ("exit".equals(id)) {
                finish();
            }
            return null;
        }

    }

    private class ReWebViewClient extends SystemWebViewClient {

        public ReWebViewClient(SystemWebViewEngine parentEngine) {
            super(parentEngine);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            // webView.loadUrl("file:///android_asset/www/test.html");
            Log.d(TAG, "---onReceivedError");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Log.d(TAG, "---shouldOverrideUrlLoading");
            return super.shouldOverrideUrlLoading(view, request);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "---onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "---onPageFinished");
        }
    }


    private class ReWebChromeClient extends SystemWebChromeClient {

        public ReWebChromeClient(SystemWebViewEngine parentEngine) {
            super(parentEngine);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            showProgressBar();
            setWebViewProgress(newProgress);
            if (newProgress == 100) {
                hideProgressBar();
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            getTitle(title);
        }
    }
}
