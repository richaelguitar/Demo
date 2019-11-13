package com.app.demo.schedule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import okhttp3.Call;


import androidx.annotation.Nullable;

import com.app.demo.App;
import com.app.demo.CommunicationVideoUI;
import com.app.demo.UserListActivity;
import com.app.demo.entity.MessageInfo;
import com.app.demo.entity.Result;
import com.app.demo.util.Const;
import com.app.demo.util.LoginUtils;
import com.app.demo.util.NotificationUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


/**
 * 后台执行刷新数据
 */
public class ReflushDataService extends Service {
    public ReflushDataService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reflushData();
        return super.onStartCommand(intent, flags, startId);
    }

    private void reflushData() {
        //获取userId
        int loginId = LoginUtils.getLoginInfo(this).getInt("userId",666666);
                    OkHttpUtils.get()
                    .url(Const.GET_ROOM_BY_USERID_URL)
                    .addParams("userId", ""+loginId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Result result = new Gson().fromJson(response,Result.class);
                            if("200".equals(result.getCode())){
                                if(result.getData()!=null&& !App.application.getNotificationRoomList().contains(result.getData().getRoomId())){
                                    MessageInfo messageInfo = new MessageInfo();
                                    messageInfo.getValues().put("title","视频请求");
                                    messageInfo.getValues().put("content","用户："+result.getData().getUserId()+"邀请您视频通话");
                                    messageInfo.getValues().put("roomId",result.getData().getRoomId());
                                    messageInfo.getValues().put("userId",result.getData().getUserId());
                                    //发出视频通知
                                    NotificationUtils.showDefaultNotification(messageInfo,ReflushDataService.this);
                                    App.application.getNotificationRoomList().add(result.getData().getRoomId());
                                }
                            }
                        }
                    });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
