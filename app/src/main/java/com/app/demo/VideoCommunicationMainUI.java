package com.app.demo;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.app.demo.databinding.VideoCommunicationMainBinding;
import com.app.demo.helper.ZGVideoCommunicationHelper;
import com.app.demo.util.AppLogger;

import com.app.demo.ui.BaseActivity;


import java.util.HashMap;



/**
 * 视频通话专题入口
 *
 */
public class VideoCommunicationMainUI extends BaseActivity {

    private VideoCommunicationMainBinding videoCommunicationMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 这里使用Google官方的MVVM框架来实现UI的控制逻辑，开发者可以根据情况选择使用此框架
        videoCommunicationMainBinding = DataBindingUtil.setContentView(this, R.layout.video_communication_main);
        // 点击左上方的返回控件之后销毁当前Activity
        videoCommunicationMainBinding.goBackToMainUIFromVideoCommunication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCommunicationMainUI.this.goBackToMainUIFromVideoCommunication();
            }
        });
        // 点击"登录房间"的按钮进入房间并进行推拉流
        videoCommunicationMainBinding.btnJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomid = videoCommunicationMainBinding.edRoomId.getText().toString().trim();
                if(0 != roomid.length()){
                    PublishStreamAndPlayStreamUI.actionStart(VideoCommunicationMainUI.this, roomid);
                }else {
                    AppLogger.getInstance().i(VideoCommunicationMainUI.class, getString(com.app.demo.R.string.tx_room_id_is_no_null));
                    Toast.makeText(VideoCommunicationMainUI.this, getString(com.app.demo.R.string.tx_room_id_is_no_null), Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 初始化 ZGVideoCommunicationHelper 实例
        ZGVideoCommunicationHelper.sharedInstance().initZGVideoCommunicationHelper();

    }

    /**
     * 退出当前Activity的时候释放SDK，对于这里的方式，开发者无需照搬，可根据需要将SDK一直设置为初始化状态
     *
     * */
    @Override
    public void onBackPressed() {

        ZGVideoCommunicationHelper.sharedInstance().releaseZGVideoCommunicationHelper();
        super.onBackPressed();
    }

    /**
     * 供其他Activity调用，进入本专题的方法
     *
     * @param activity
     */
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, VideoCommunicationMainUI.class);
        activity.startActivity(intent);
    }

    /**
     * 返回到应用主界面
     *
     */
    public void goBackToMainUIFromVideoCommunication(){

        this.onBackPressed();
    }

}
