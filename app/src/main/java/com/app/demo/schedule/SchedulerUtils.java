package com.app.demo.schedule;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

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

import okhttp3.Call;


/**
 * 定时执行调度工具
 */
public class SchedulerUtils {

    private static final int MSG_POLLING = 100;
    private static SchedulerUtils schedulerUtils;

    public static final  int SUCCESS_CODE = 200;

    private Context mContext;

    //时间间隔
    private  static final long POLLING_INTERVAL=3*1000L;

    public static MessageHandler mHandler = new MessageHandler();


    private SchedulerUtils(Context context){
        this.mContext = context;
    }
    //单例
    public static SchedulerUtils with(Context context){
        if(schedulerUtils == null){
            synchronized (SchedulerUtils.class){
                if(schedulerUtils == null){
                    schedulerUtils = new SchedulerUtils(context);
                }
            }
        }

        return  schedulerUtils;
    }

    public static class MessageHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_POLLING:
                    schedulerUtils.doPolling(false);
                    break;
                    default:
                        break;
            }
        }
    }

    /**
     * 移除消息
     */
    public void  removeMessageCallback(){
        if(mHandler!=null){
            mHandler.removeMessages(MSG_POLLING);
        }
    }




    public void doPolling(Boolean isFirst) {
        if(isFirst) {
            mHandler.removeMessages(MSG_POLLING);
            mHandler.sendEmptyMessageDelayed(MSG_POLLING, POLLING_INTERVAL);
            return;
        }
        reflushData(); //执行异步轮询任务
        mHandler.sendEmptyMessageDelayed(MSG_POLLING, POLLING_INTERVAL);
    }

    /**
     * 刷新消息
     */
    private  void reflushData() {
        Log.d(SchedulerUtils.class.getSimpleName(),"执行了====");
        //获取userId
        String loginId = LoginUtils.getLoginInfo(mContext).getString("userId","4");
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
                        if(result.getCode() == SUCCESS_CODE){
                            if(result.getData()!=null&& !TextUtils.isEmpty(result.getData().getRoom_id())){
                                //判断app是否在前台
                                boolean isForeground = BuildConfig.APPLICATION_ID.equalsIgnoreCase(DeviceInfoManager.getTopActivityPackageName(mContext));
                                if(isForeground){
                                    Intent intent = new Intent(mContext, CommunicationVideoUI.class);
                                    intent.putExtra("roomId",result.getData().getRoom_id());
                                    intent.putExtra(Const.ACTION_TYPE,Const.ACTION_ACCEPT);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                }else{
                                    MessageInfo messageInfo = new MessageInfo();
                                    messageInfo.getValues().put("title","视频请求");
                                    messageInfo.getValues().put("content","事件id："+result.getData().getEvent_id()+"邀请您视频通话");
                                    messageInfo.getValues().put("roomId",result.getData().getRoom_id());

                                    NotificationUtils.showDefaultNotification(messageInfo,mContext);
                                }
                                App.application.getNotificationRoomList().add(result.getData().getRoom_id());
                            }
                        }
                    }
                });
    }
}
