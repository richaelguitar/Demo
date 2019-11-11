package com.app.demo;

import com.app.demo.ZGBaseHelper;
import com.app.demo.util.AppLogger;
import com.app.demo.util.ZegoUtil;
import com.zego.zegoavkit2.ZegoExternalVideoCapture;
import com.zego.zegoavkit2.ZegoVideoCaptureFactory;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.constants.ZegoAvConfig;
import com.zego.zegoliveroom.constants.ZegoConstants;


/**
 * ZegoApi管理类，主要用于进阶模块初始化 sdk 的一些功能封装
 */
public class ZGManager {

    private boolean isTestEnv = ZegoUtil.getIsTestEnv();

    public ZegoLiveRoom api() {
        // 判断是否创建了ZegoLiveRoom实例 避免重复初始化sdk
        if (com.app.demo.ZGBaseHelper.sharedInstance().getZegoLiveRoom() == null) {
            // 初始化zegoSDK
            com.app.demo.ZGBaseHelper.sharedInstance().initZegoSDK(ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
                @Override
                public void onInitSDK(int errorCode) {
                    if (errorCode == 0) {
                        AppLogger.getInstance().i(com.app.demo.ZGBaseHelper.class, "初始化zegoSDK成功!");
                    } else {
                        // 如果第一次初始化sdk，并且设备没有联网，会初始化失败。需要重新初始化sdk
                        AppLogger.getInstance().w(com.app.demo.ZGBaseHelper.class, "初始化zegoSDK失败!!! 错误码 errorCode : %d", errorCode);
                    }
                }
            });

        }

        return com.app.demo.ZGBaseHelper.sharedInstance().getZegoLiveRoom();
    }


    public static ZGManager zgManager;

    public static ZGManager sharedInstance() {
        if (zgManager == null) {
            synchronized (ZGManager.class) {
                if (zgManager == null) {
                    zgManager = new ZGManager();
                }
            }
        }
        return zgManager;
    }


    public void enableExternalVideoCapture(ZegoVideoCaptureFactory zegoVideoCaptureFactory) {
        /* 释放ZegoSDK */
        unInitSDK();
        ZegoExternalVideoCapture.setVideoCaptureFactory(zegoVideoCaptureFactory, ZegoConstants.PublishChannelIndex.MAIN);
    }

    public void setZegoAvConfig(int width, int height) {
        ZegoAvConfig mZegoAvConfig = new ZegoAvConfig(ZegoAvConfig.Level.High);
        mZegoAvConfig.setVideoEncodeResolution(width, height);
        mZegoAvConfig.setVideoCaptureResolution(width, height);
        mZegoAvConfig.setVideoFPS(25);
        api().setAVConfig(mZegoAvConfig);
    }

    public boolean isTestEnv() {
        return isTestEnv;
    }

    public void unInitSDK() {
        ZGBaseHelper.sharedInstance().unInitZegoSDK();
    }
}
