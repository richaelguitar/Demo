package com.app.demo.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * 登录工具
 */
public class LoginUtils {

    /**
     * 是否登录
     * @param context
     * @return
     */
    public static boolean isLogin(Context context){
        return  getLoginInfo(context).getBoolean("isLogin",false);
    }


    /**
     * 当前登录用户是否是管理员
     * @param context
     * @return
     */
    public static boolean isAdmin(Context context){
        return  getLoginInfo(context).getBoolean("isAdmin",false);
    }

    /**
     * 获取用户登录信息
     * @param context
     * @return
     */
    public static SharedPreferences getLoginInfo(Context context){
        return  context.getSharedPreferences(Const.sharePerference, Context.MODE_PRIVATE);
    }
}
