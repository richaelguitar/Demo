package com.app.demo;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.app.demo.helper.ZGBaseHelper;
import com.app.demo.util.AppLogger;
import com.app.demo.util.DeviceInfoManager;
import com.app.demo.widgets.window.FloatingView;
import com.tencent.bugly.crashreport.CrashReport;
import com.zego.zegoliveroom.ZegoLiveRoom;

import java.util.Date;

/**
 * 程序入口
 * @author xiaoww
 * @since 2019-11-11
 */
public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Application App;

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;

        String randomSuffix = "-" + new Date().getTime()%(new Date().getTime()/1000);

        String userId = DeviceInfoManager.generateDeviceId(this) + randomSuffix;
        String userName = DeviceInfoManager.getProductName() + randomSuffix;

        // 添加悬浮日志视图
        FloatingView.get().add();

        // 使用Zego sdk前必须先设置SDKContext。
        ZGBaseHelper.sharedInstance().setSDKContextEx(userId, userName, null, null, 10 * 1024 * 1024, this);

        AppLogger.getInstance().i(App.class, "SDK version : %s",  ZegoLiveRoom.version());
        AppLogger.getInstance().i(App.class, "VE version : %s",  ZegoLiveRoom.version2());

        // bugly初始化用户id
        CrashReport.initCrashReport(getApplicationContext(), "7ace07528f", false);
        CrashReport.setUserId(userId);
    }
}
