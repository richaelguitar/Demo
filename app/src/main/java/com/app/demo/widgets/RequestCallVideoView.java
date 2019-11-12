package com.app.demo.widgets;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import androidx.annotation.NonNull;


import com.app.demo.R;

import com.app.demo.util.AppLogger;
import com.app.demo.widgets.window.FloatingView;

/**
 * 发起视频请求
 */
public class RequestCallVideoView extends FrameLayout {


    private ImageView callImage, cancelImage;

    public RequestCallVideoView(@NonNull Context context) {
        super(context, null);
        inflate(context, R.layout.request_call_video_view, this);
        callImage = findViewById(R.id.iv_call);
        cancelImage = findViewById(R.id.iv_cancel);

        //发起视频
        callImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppLogger.getInstance().clearLog();
            }
        });
        //取消
        cancelImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingView.get().hidden();
            }
        });

    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


}
