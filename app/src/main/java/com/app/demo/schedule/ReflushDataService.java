package com.app.demo.schedule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


import androidx.annotation.Nullable;

import com.app.demo.util.Const;
import com.app.demo.util.LoginUtils;
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
//        int loginId = LoginUtils.getLoginInfo(this).getInt("userId",0);
//                    OkHttpUtils.get()
//                    .url(Const.GET_DEVICE_URL)
//                    .addParams("userId", ""+loginId)
//                    .build()
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//
//                        }
//
//                        @Override
//                        public void onResponse(String response, int id) {
//
//                        }
//                    });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
