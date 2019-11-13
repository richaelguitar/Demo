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
import com.app.demo.model.VideoLayoutModel;


import com.app.demo.basic.BaseActivity;
import com.app.demo.widgets.window.FloatingView;
import com.zego.zegoliveroom.entity.ZegoStreamInfo;


import java.util.ArrayList;
import java.util.Date;

/**
 * 视频页面
 */
public class CommunicationVideoUI extends BaseActivity {

    /**
     * 获取当前Activity绑定的DataBinding
     *
     * @return
     */


    public  CallVideoBeforeBinding getCallVideoBeforeBinding() {
        return callVideoBeforeBinding;
    }

    //这里使用Google官方的MVVM框架DataBinding来实现UI逻辑，开发者可以根据自己的情况使用别的方式编写UI逻辑
    private CallVideoBeforeBinding   callVideoBeforeBinding;

    // 这里为防止多个设备测试时相同流id冲推导致的推流失败，这里使用前缀"s-streamid-"+用户id作为标识
    private String mPublishStreamid;//+ new Date().getTime()%(new Date().getTime()/10000);

    // 推拉流布局模型
    VideoLayoutModel mVideoLayoutModel;
    // 当拉多条流时，把流id的引用放到ArrayList里
    private ArrayList<String> playStreamids = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加悬浮日志视图
        FloatingView.get().add();

        // 使用DataBinding加载布局
        callVideoBeforeBinding = DataBindingUtil.setContentView(this, R.layout.call_video_before);
        // 在退出重进房间的时候UI上记录上一次摄像头开关和麦克风开关的状态
        callVideoBeforeBinding.MicrophoneState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgMicState());
        callVideoBeforeBinding.CameraState.setChecked(ZGVideoCommunicationHelper.sharedInstance().getZgCameraState());

        //挂断视频
        callVideoBeforeBinding.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunicationVideoUI.this,"通话结束",Toast.LENGTH_SHORT).show();
                CommunicationVideoUI.this.onBackPressed();
            }
        });


        // 设置麦克风和摄像头的点击事件
        setCameraAndMicrophoneStateChangedOnClickEvent();

        // 从VideoCommunicationMainUI的Activtity中获取传过来的RoomID，以便登录登录房间并马上推流
        Intent it = getIntent();
        String roomid = it.getStringExtra("roomId");
        mPublishStreamid = "s-streamid-"+it.getStringExtra("userId");

        ZGVideoCommunicationHelper.sharedInstance().setZGVideoCommunicationHelperCallback( new ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback(){

            @Override
            public TextureView addRenderViewByStreamAdd(ZegoStreamInfo listStream) {
                //有新流则添加
                TextureView playRenderView = CommunicationVideoUI.this.mVideoLayoutModel.addStreamToViewInLayout(listStream.streamID);

                if(!playStreamids.contains(listStream.streamID)){
                    CommunicationVideoUI.this.playStreamids.add(listStream.streamID);
                }
                if(playStreamids.size() <= 2){
                    callVideoBeforeBinding.tvStateDesc.setText("视频通话中");
                }
                return playRenderView;
            }

            @Override
            public void onLoginRoomFailed(int errorcode){

                if(ZGVideoCommunicationHelper.ZGVideoCommunicationHelperCallback.NUMBER_OF_PEOPLE_EXCEED_LIMIT == errorcode){
                    Toast.makeText(CommunicationVideoUI.this, "房间已满人，目前demo只展示2人通讯", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(CommunicationVideoUI.this, "登录房间失败，请检查网络", Toast.LENGTH_LONG).show();
                }
                CommunicationVideoUI.this.onBackPressed();

            }

            @Override
            public void onPublishStreamFailed(int errorcode){

                Toast.makeText(CommunicationVideoUI.this, "开启视频通话失败，检查网络", Toast.LENGTH_LONG).show();
                onBackPressed();

            }

            @Override
            public void removeRenderViewByStreamDelete(ZegoStreamInfo streamInfo) {
                CommunicationVideoUI.this.mVideoLayoutModel.removeStreamToViewInLayout(streamInfo.streamID);
                CommunicationVideoUI.this.playStreamids.remove(streamInfo.streamID);
                Toast.makeText(CommunicationVideoUI.this,"对方已挂断",Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        // 这里创建多人连麦的Model的实例
        this.mVideoLayoutModel = new VideoLayoutModel(this);

        // 这里在登录房间之后马上推流并做本地推流的渲染
        TextureView localPreviewView = CommunicationVideoUI.this.mVideoLayoutModel.addStreamToViewInLayout(CommunicationVideoUI.this.mPublishStreamid);

        // 这里进入当前Activty之后马上登录房间，在登录房间的回调中，若房间已经有其他流在推，从登录回调中获取拉流信息并拉这些流
        ZGVideoCommunicationHelper.sharedInstance().startVideoCommunication(roomid, localPreviewView, mPublishStreamid);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 在应用内实现悬浮窗，需要依附Activity生命周期
        FloatingView.get().attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // // 在应用内实现悬浮窗，需要依附Activity生命周期
        FloatingView.get().detach(this);
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

        this.mVideoLayoutModel.removeAllStreamToViewInLayout();
        ZGVideoCommunicationHelper.sharedInstance().quitVideoCommunication(this.playStreamids);
        super.onBackPressed();
    }

    /**
     * 供其他Activity调用，进入当前Activity进行推拉流
     *
     * @param activity
     */
    public static void actionStart(Activity activity, String roomID) {
        Intent intent = new Intent(activity, CommunicationVideoUI.class);
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
