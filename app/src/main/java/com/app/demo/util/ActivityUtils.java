package com.app.demo.util;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.app.demo.CommunicationVideoUI;
import com.app.demo.UserListActivity;
import com.app.demo.entity.Result;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 跳转工具包
 */
public class ActivityUtils {

    private static ActivityUtils activityUtils;

    private Context mContext;

    public final  static  int SUCCESS_CODE = 200;



    private ActivityUtils(Context context){
        this.mContext = context;
    }
    //单例
    public static ActivityUtils with(Context context){
        if(activityUtils == null){
            synchronized (ActivityUtils.class){
                if(activityUtils == null){
                    activityUtils = new ActivityUtils(context);
                }
            }
        }

        return  activityUtils;
    }

    public  void startVideo(String userId,String roomId){
        //发出请求
        OkHttpUtils.get()
                .url(Const.CREATE_ROOM_URL)
                .addParams("room_id", roomId)
                .addParams("user_id", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext,"网络请求失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Result result = new Gson().fromJson(response,Result.class);
                        if(result.getCode() == SUCCESS_CODE){
                            Intent intent = new Intent(mContext, CommunicationVideoUI.class);
                            intent.putExtra("roomId",roomId);
                            intent.putExtra(Const.ACTION_TYPE,Const.ACTION_CALL);
                            mContext.startActivity(intent);
                        }else{
                            Toast.makeText(mContext,"网络请求失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
