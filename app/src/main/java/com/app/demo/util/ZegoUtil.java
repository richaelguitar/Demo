package com.app.demo.util;

import android.text.TextUtils;

import com.app.demo.GetAppIdConfig;
import com.app.demo.util.AppLogger;
import com.app.demo.util.PreferenceUtil;

import java.util.regex.Pattern;

import static com.app.demo.util.PreferenceUtil.KEY_APP_ID;
import static com.app.demo.util.PreferenceUtil.KEY_APP_SIGN;
import static com.app.demo.util.PreferenceUtil.KEY_TEST_ENVIRONMENT;

/**
 * Created by zego on 2019/4/16.
 */

public class ZegoUtil {

    /**
     * 字符串转换成 byte 数组
     * 主要用于 appSign 的转换
     * @param strSignKey
     * @return
     * @throws NumberFormatException
     */
    public static byte[] parseSignKeyFromString(String strSignKey) throws NumberFormatException {
        String[] keys = strSignKey.split(",");
        if (keys.length != 32) {
            AppLogger.getInstance().i(ZegoUtil.class, "appSign 格式非法");

            return null;
        }
        byte[] byteSignKey = new byte[32];
        for (int i = 0; i < 32; i++) {
            int data = Integer.valueOf(keys[i].trim().replace("0x", ""), 16);
            byteSignKey[i] = (byte) data;
        }
        return byteSignKey;
    }

    public static long parseAppIDFromString(String strAppID) throws NumberFormatException {

        // 使用正则表达式校验是否包含除数字以外的字符
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean isInt = pattern.matcher(strAppID).matches();
        if (TextUtils.isEmpty(strAppID) || !isInt) {
            AppLogger.getInstance().i(ZegoUtil.class, "appID 格式非法");

            return 0;
        }

        return Long.parseLong(strAppID);
    }

    /**
     * 获取随机生成StreamID
     * @return
     */
    public static String getPublishStreamID(){
        return "s" + System.currentTimeMillis();
    }

    /**
     * 获取当前设置的 AppID
     */
    public static long getAppID() {
        String strAppID = PreferenceUtil.getInstance().getStringValue(KEY_APP_ID, "");
        if (strAppID.equals("")){
            return GetAppIdConfig.appId;
        }
        return parseAppIDFromString(strAppID);
    }

    /**
     * 获取当前设置的 AppSign
     */
    public static byte[] getAppSign() {
        String strAppSign = PreferenceUtil.getInstance().getStringValue(KEY_APP_SIGN, "");
        if (strAppSign.equals("")) {
            return GetAppIdConfig.appSign;
        }
        return parseSignKeyFromString(strAppSign);
    }

    /**
     * 获取当前设置的环境
     */
    public static boolean getIsTestEnv() {
        return PreferenceUtil.getInstance().getBooleanValue(KEY_TEST_ENVIRONMENT, true);
    }

}
