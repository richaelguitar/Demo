package com.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.app.demo.ui.BaseActivity;
import com.app.demo.ui.WebActivity;
import com.zego.zegoliveroom.constants.ZegoConstants;


/**
 * 主页面入口
 * @author xiaoww
 * @since 2019-11-11
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //进入h5
        findViewById(R.id.btn_start_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebViewActivity.class));
            }
        });

        //主播开播
        findViewById(R.id.btn_anchor_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isPremission = checkOrRequestPermission(REQUEST_PERMISSION_CODE);
               if(isPremission){
                   Intent intent = new Intent(MainActivity.this, AnchorVideoUI.class);
                   intent.putExtra("roomID","10000086");
                   startActivity(intent);
               }
            }
        });

        //观众进入
        findViewById(R.id.btn_audience_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPremission = checkOrRequestPermission(REQUEST_PERMISSION_CODE);
                if(isPremission){
                    Intent intent = new Intent(MainActivity.this, AudienceVideoUI.class);
                    intent.putExtra("roomID","10000086");
                    startActivity(intent);
                }
            }
        });

    }

}
