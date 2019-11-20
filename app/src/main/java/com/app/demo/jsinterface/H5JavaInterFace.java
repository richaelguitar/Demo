package com.app.demo.jsinterface;

import android.webkit.JavascriptInterface;

/**
 * H5 js interface
 * @author xww
 */
public interface H5JavaInterFace {

    @JavascriptInterface
    void  forwardUserVideo(String userId);
}
