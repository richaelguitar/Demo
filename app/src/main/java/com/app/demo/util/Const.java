package com.app.demo.util;


import com.app.demo.BuildConfig;

/**
 * 常量管理
 * @author xww
 * @since 2019-03-22
 */
public class Const {



    public static final String sharePerference="userInfo";

    //基础接口
    public static final String APP_BASE_URL= BuildConfig.BASE_URL;


    //获取最新发布数据
    public static final String USER_DATA_URL=APP_BASE_URL+"/addLoginInfoAction";

    public static String MESSAGE_RECEIVER_ACTION="com.local.message.receiverMsg";//接受消息Action

    public static String MESSAGE_KEY="msgKey";//消息key

    public static final String NOTIFICATION_CHANNEL_ID="WarningNotification";

    public static final String GET_DEVICE_URL=APP_BASE_URL+"/getDeviceInfoListAction";//获取设备列表


    public static final String GET_BIND_DEVICE_URL=APP_BASE_URL+"/bindDeviceAction";//绑定设备



}
