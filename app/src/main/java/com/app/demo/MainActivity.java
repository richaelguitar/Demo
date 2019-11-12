package com.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.app.demo.ui.BaseActivity;
import com.app.demo.ui.WebActivity;


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
//                startActivity(new Intent(MainActivity.this,WebViewActivity.class));
                WebActivity.actionStart(MainActivity.this,"https://m.baidu.com","百度一下");
            }
        });

        //发起视频
        findViewById(R.id.btn_call_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isPremission = checkOrRequestPermission(REQUEST_PERMISSION_CODE);
               if(isPremission){
                   startActivity(new Intent(MainActivity.this, VideoCommunicationMainUI.class));
               }
            }
        });

    }

}
