package com.app.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.app.demo.basic.BaseActivity;
import com.app.demo.helper.ZGVideoCommunicationHelper;


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

        // 初始化 ZGVideoCommunicationHelper 实例
        ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();


        //进入h5
        findViewById(R.id.btn_start_webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebViewActivity.class));
            }
        });

        //主播开播
        findViewById(R.id.btn_user_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               boolean isPremission = checkOrRequestPermission(REQUEST_PERMISSION_CODE);
               if(isPremission){
                   Intent intent = new Intent(MainActivity.this, CommunicationVideoUI.class);
                   intent.putExtra("roomID","100086");
                   startActivity(intent);
               }
            }
        });

    }

}
