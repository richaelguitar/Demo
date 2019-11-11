package com.app.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.app.demo.databinding.CallVideoBeforeBinding;
import com.app.demo.helper.ZGVideoCommunicationHelper;
import com.app.demo.model.PublishStreamAndPlayStreamLayoutModel;


import com.app.demo.ui.BaseActivity;
import com.app.demo.widgets.window.FloatingView;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;


import java.util.ArrayList;
import java.util.Date;

public class PublishStreamAndPlayStreamUI extends BaseActivity {

    /**
     * 获取当前Activity绑定的DataBinding
     *
     * @return
     */

    public  CallVideoBeforeBinding getPublishStreamAndPlayStreamBinding() {
        return callVideoBeforeBinding;
    }

    //这里使用Google官方的MVVM框架DataBinding来实现UI逻辑，开发者可以根据自己的情况使用别的方式编写UI逻辑
    private CallVideoBeforeBinding   callVideoBeforeBinding;

    // 这里为防止多个设备测试时相同流id冲推导致的推流失败，这里使用时间戳的后4位来作为随机的流id，开发者可根据业务需要定义业务上的流id
    private String mPublishStreamid = "s-streamid-" + new Date().getTime()%(new Date().getTime()/10000);

    // 推拉流布局模型
    PublishStreamAndPlayStreamLayoutModel mPublishStreamAndPlayStreamLayoutModel;
    // 当拉多条流时，把流id的引用放到ArrayList里
    private ArrayList<String> playStreamids = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使用DataBinding加载布局
        callVideoBeforeBinding = DataBindingUtil.setContentView(this, R.layout.call_video_before);
        // 在退出重进房间的时候UI上记录上一次摄像头开关和麦克风开关的状态
        callVideoBeforeBinding.MicrophoneState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgMicState());
        callVideoBeforeBinding.CameraState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgCameraState());


        // 设置麦克风和摄像头的点击事件
        setCameraAndMicrophoneStateChangedOnClickEvent();

        // 从VideoCommunicationMainUI的Activtity中获取传过来的RoomID，以便登录登录房间并马上推流
        Intent it = getIntent();
        String roomid = it.getStringExtra("roomID");

        // 设置当前UI界面左上角的点击事件，点击之后结束当前Activity，退出房间，SDK内部在退出房间的时候会自动停止推拉流
        callVideoBeforeBinding.goBackToVideoCommunicationInputRoomidUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ZGVideoCommunicationHelper.sharedInstance().setZGVideoCommunicationHelperCallback( new ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback(){

            @Override
            public TextureView addRenderViewByStreamAdd(ZegoStreamInfo listStream) {
                TextureView playRenderView = PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.addStreamToViewInLayout(listStream.streamID);
                PublishStreamAndPlayStreamUI.this.playStreamids.add(listStream.streamID);
                return playRenderView;
            }

            @Override
            public void onLoginRoomFailed(int errorcode){

                if(ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback.NUMBER_OF_PEOPLE_EXCEED_LIMIT == errorcode){
                    Toast.makeText(PublishStreamAndPlayStreamUI.this, "房间已满人，目前demo只展示12人通讯", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(PublishStreamAndPlayStreamUI.this, "登录房间失败，请检查网络", Toast.LENGTH_LONG).show();
                }
                PublishStreamAndPlayStreamUI.this.onBackPressed();

            }

            @Override
            public void onPublishStreamFailed(int errorcode){

                Toast.makeText(PublishStreamAndPlayStreamUI.this, "开启视频通话失败，检查网络", Toast.LENGTH_LONG).show();
                onBackPressed();

            }

            @Override
            public void removeRenderViewByStreamDelete(ZegoStreamInfo streamInfo) {
                PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.removeStreamToViewInLayout(streamInfo.streamID);
                PublishStreamAndPlayStreamUI.this.playStreamids.remove(streamInfo.streamID);
            }

        });

        // 这里创建多人连麦的Model的实例
        this.mPublishStreamAndPlayStreamLayoutModel = new PublishStreamAndPlayStreamLayoutModel(this);

        // 这里在登录房间之后马上推流并做本地推流的渲染
        TextureView localPreviewView = PublishStreamAndPlayStreamUI.this.mPublishStreamAndPlayStreamLayoutModel.addStreamToViewInLayout(PublishStreamAndPlayStreamUI.this.mPublishStreamid);

        // 这里进入当前Activty之后马上登录房间，在登录房间的回调中，若房间已经有其他流在推，从登录回调中获取拉流信息并拉这些流
        ZGVideoCommunicationHelper.sharedInstance().startVideoCommunication(roomid, localPreviewView, mPublishStreamid);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingView.get().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FloatingView.get().hidden();
    }

    /**
     * 定义设置麦克风和摄像头开关状态的点击事件
     *
     */
    private void setCameraAndMicrophoneStateChangedOnClickEvent() {

        // 设置摄像头开关的点击事件
        this.callVideoBeforeBinding.CameraState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    ZGVideoCommunicationHelper.sharedInstance().enableCamera(true);
                }else {
                    ZGVideoCommunicationHelper.sharedInstance().enableCamera(false);

                }

            }
        });

        // 设置麦克风开关的点击事件
        this.callVideoBeforeBinding.MicrophoneState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    ZGVideoCommunicationHelper.sharedInstance().enableMic(true);
                }else {
                    ZGVideoCommunicationHelper.sharedInstance().enableMic(false);
                }

            }
        });
    }

    /**
     * 当返回当前Activity的时候应该停止推拉流并退出房间，此处作为参考
     *
     */
    @Override
    public void onBackPressed() {

        this.mPublishStreamAndPlayStreamLayoutModel.removeAllStreamToViewInLayout();
        ZGVideoCommunicationHelper.sharedInstance().quitVideoCommunication(this.playStreamids);
        super.onBackPressed();
    }

    /**
     * 供其他Activity调用，进入当前Activity进行推拉流
     *
     * @param activity
     */
    public static void actionStart(Activity activity, String roomID) {
        Intent intent = new Intent(activity, PublishStreamAndPlayStreamUI.class);
        intent.putExtra("roomID", roomID);

        activity.startActivity(intent);
    }

    /**
     * 返回到输入房间id的UI界面
     *
     * @param v 点击返回的按钮
     */
    public void goBackToVideoCommunicationInputRoomidUI(View v){
        this.onBackPressed();
    }


}
