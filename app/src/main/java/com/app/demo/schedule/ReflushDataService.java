package com.app.demo.schedule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import okhttp3.Call;


import androidx.annotation.Nullable;

import com.app.demo.App;
import com.app.demo.BuildConfig;
import com.app.demo.CommunicationVideoUI;
import com.app.demo.entity.MessageInfo;
import com.app.demo.entity.Result;
import com.app.demo.util.Const;
import com.app.demo.util.DeviceInfoManager;
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
        String loginId = LoginUtils.getLoginInfo(this).getString("userId","8");
                    OkHttpUtils.get()
                    .url(Const.GET_ROOM_BY_USERID_URL)
                    .addParams("user_id", loginId)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Result result = new Gson().fromJson(response,Result.class);
                            if(result.getCode() == 200){
                                if(result.getData()!=null&& !TextUtils.isEmpty(result.getData().getRoom_id())){
                                    //判断app是否在前台
                                    boolean isForeground = BuildConfig.APPLICATION_ID.equalsIgnoreCase(DeviceInfoManager.getTopActivityPackageName(getApplication()));
                                    if(isForeground){
                                        Intent intent = new Intent(getApplicationContext(), CommunicationVideoUI.class);
                                        intent.putExtra("roomId",result.getData().getRoom_id());
                                        intent.putExtra(Const.ACTION_TYPE,Const.ACTION_ACCEPT);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }else{
                                        MessageInfo messageInfo = new MessageInfo();
                                        messageInfo.getValues().put("title","视频请求");
                                        messageInfo.getValues().put("content","用户："+result.getData().getProducer()+"邀请您视频通话");
                                        messageInfo.getValues().put("roomId",result.getData().getRoom_id());

                                        NotificationUtils.showDefaultNotification(messageInfo,ReflushDataService.this);
                                    }
                                    App.application.getNotificationRoomList().add(result.getData().getRoom_id());
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
