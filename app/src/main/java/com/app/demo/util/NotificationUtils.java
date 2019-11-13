package com.app.demo.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;


import androidx.core.app.NotificationCompat;

import com.app.demo.CommunicationVideoUI;
import com.app.demo.R;
import com.app.demo.entity.MessageInfo;

import java.util.Random;

/**
 * 通知管理器
 */
public class NotificationUtils {


    /**
     * 显示通知栏
     * @param msg
     * @param context
     */
    public static void showDefaultNotification(MessageInfo msg, Context context) {
        int id = new Random(System.nanoTime()).nextInt();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //创建渠道通知
            manager.createNotificationChannel(createNotificationChannel(Const.NOTIFICATION_CHANNEL_ID,"channelName"));
            mBuilder = new NotificationCompat.Builder(context,Const.NOTIFICATION_CHANNEL_ID);
        }else{
            mBuilder = new NotificationCompat.Builder(context,"");
        }


        mBuilder.setContentTitle(msg.getValues().get("title"))
                .setContentText(msg.getValues().get("content"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(msg.getValues().get("userId"))
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        PendingIntent clickPendingIntent = getClickPendingIntent(context,msg.getValues().get("roomId"),msg.getValues().get("userId"));
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel(String channelId, String channelName){
        NotificationChannel channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
        channel.setLightColor(Color.RED);//设置小红点颜色
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setShowBadge(true);//长按桌面应用图标是否会显示渠道通知
        return channel;
    }

    /**
     * 点击查看通知
     * @param context
     * @return
     */
    public static PendingIntent getClickPendingIntent(Context context,String roomId,String userId) {
        Intent clickIntent = new Intent(context, CommunicationVideoUI.class);
        clickIntent.putExtra("roomId",roomId);
        clickIntent.putExtra("userId",userId);
        clickIntent.putExtra(Const.ACTION_TYPE,Const.ACTION_ACCEPT);
        return PendingIntent.getActivity(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
