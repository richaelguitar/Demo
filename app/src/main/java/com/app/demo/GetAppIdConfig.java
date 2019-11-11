package com.app.demo;

/**
 * Created by zego on 2018/11/15.
 */

public class GetAppIdConfig {

    /**
     * 请提前在即构管理控制台获取 appID 与 appSign
     *  AppID 填写样式示例：
            public static final long appId = 123456789L;
     *  appSign 填写样式示例：
            public static final byte[] appSign = {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
            }
     *
     */

    public static final long appId = 318596465;

    public static final byte[] appSign = new byte[]{(byte)0xc7,(byte)0xcc,(byte)0x0a,(byte)0x69,(byte)0x6d,(byte)0xae,(byte)0x5a,(byte)0xd6,(byte)0x17,(byte)0x5a,(byte)0x26,(byte)0x8d,(byte)0xa4,(byte)0x94,(byte)0xdb,(byte)0xf0,(byte)0x9b,(byte)0xda,(byte)0x76,(byte)0x3e,(byte)0x3a,(byte)0xf5,(byte)0x04,(byte)0x45,(byte)0x78,(byte)0xf7,(byte)0x93,(byte)0x68,(byte)0x58,(byte)0x9e,(byte)0xb0,(byte)0xfa};
}