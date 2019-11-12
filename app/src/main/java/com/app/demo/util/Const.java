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

    ////创建房间
    public static final String CREATE_ROOM_URL=APP_BASE_URL+"/api/createOlineRoom";

    //根据用户id获取房间信息
    public static final String GET_ROOM_BY_USERID_URL=APP_BASE_URL+"/api/getOnlineRoomByUserId";

    //根据房间id获取房间信息
    public static final String GET_ROOM_BY_ROOMID_URL=APP_BASE_URL+"/api/getOnlineRoomByRoomId?roomId=r123456789";

    //删除房间
    public static final String DELETE_ROOM_URL=APP_BASE_URL+"/api/deleteOnlineRoom?roomId=r123456789";

    public static String MESSAGE_RECEIVER_ACTION="com.local.message.receiverMsg";//接受消息Action

    public static String MESSAGE_KEY="msgKey";//消息key

    public static final String NOTIFICATION_CHANNEL_ID="Notification";



}
