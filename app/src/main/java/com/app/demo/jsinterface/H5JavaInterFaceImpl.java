package com.app.demo.jsinterface;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.app.demo.util.ActivityUtils;

/**
 * js接口是异步回调
 */
public class H5JavaInterFaceImpl implements H5JavaInterFace {

    private Activity activity;

    public H5JavaInterFaceImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    @JavascriptInterface
    public void forwardUserVideo(String userId) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.with(activity).startVideo(userId,"room"+System.currentTimeMillis());
            }
        });
    }
}
