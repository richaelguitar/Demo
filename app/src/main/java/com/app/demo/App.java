package com.app.demo;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.app.demo.helper.ZGBaseHelper;
import com.app.demo.schedule.SchedulerUtils;
import com.app.demo.util.AppLogger;
import com.app.demo.util.DeviceInfoManager;
import com.app.demo.util.LoginUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

/**
 * 程序入口
 * @author xiaoww
 * @since 2019-11-11
 */
public class App extends MultiDexApplication {

    private static final String TAG = App.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App application;

    private List<String>  notificationRoomList = new ArrayList<>();

    public List<String> getNotificationRoomList() {
        return notificationRoomList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //初始化网络请求框架参数配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT) )
                .addInterceptor(new LoggerInterceptor(TAG))//设置日志拦截器
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
        //启动任务
        boolean isLogin = LoginUtils.getLoginInfo(this).getBoolean("isLogin",false);
        if(isLogin){
            SchedulerUtils.with(this).scheduler();//开启刷新任务
        }else{
            SchedulerUtils.with(this).stopAll();//取消所有任务
        }
        String randomSuffix = "-" + new Date().getTime()%(new Date().getTime()/1000);

        String userId = DeviceInfoManager.generateDeviceId(this) + randomSuffix;
        String userName = DeviceInfoManager.getProductName() + randomSuffix;

        // 使用Zego sdk前必须先设置SDKContext。
        ZGBaseHelper.sharedInstance().setSDKContextEx(userId, userName, null, null, 10 * 1024 * 1024, this);

        AppLogger.getInstance().i(App.class, "SDK version : %s",  ZegoLiveRoom.version());
        AppLogger.getInstance().i(App.class, "VE version : %s",  ZegoLiveRoom.version2());

        // bugly初始化用户id
        CrashReport.initCrashReport(getApplicationContext(), "7ace07528f", false);
        CrashReport.setUserId(userId);
    }
}
