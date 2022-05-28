package com.kingsware.kdev.core.kmq.websocket;

/**
 * websocket变量
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 4:41 下午
 */
public class WebsocketConstants {

    private WebsocketConstants() {}

    /** 系统主题 **/
    public static final String T_PING = "ping";
    /** 转向内部mq的topic名称 **/
    public static final String MQ_FROM_WEBSOCKET= "from_websocket";
    /** 转向内部mq的topic名称 **/
    public static final String MQ_TO_WEBSOCKET = "to_websocket";

}
