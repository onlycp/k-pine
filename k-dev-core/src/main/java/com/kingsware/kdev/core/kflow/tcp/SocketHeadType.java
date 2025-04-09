package com.kingsware.kdev.core.kflow.tcp;

/**
 * <p>
 *
 * </p>
 *
 * @package: com.kingsware.socket
 * @description:
 * @author: moojn
 * @date: Created in 2023/10/10 15:23
 * @copyright: Copyright (c) 2023
 * @version: V1.0
 * @modified: moojn
 */
public enum SocketHeadType {
    /**
     * <code>DEFAULT = 0;</code>
     */
    DEFAULT(0),

    PINE(110),
    /**
     * <pre>
     *客户端连接成功返回消息
     * </pre>
     *
     * <code>CONNECT_RESPONSE = 1;</code>
     */
    CONNECT_RESPONSE(1),
    /**
     * <pre>
     *调试请求
     * </pre>
     *
     * <code>DEBBUG_REQUEST = 100;</code>
     */
    DEBBUG_REQUEST(100),
    /**
     * <code>DEBBUG_RESPONSE = 101;</code>
     */
    DEBBUG_RESPONSE(101),

    /**
     * <pre>
     * klog日志请求
     * </pre>
     *
     * <code>KLOG_REQUEST = 102;</code>
     */
    KLOG_REQUEST(102),

    /**
     * <pre>
     * klog日志响应
     * </pre>
     *
     * <code>KLOG_REQUEST = 103;</code>
     */
    KLOG_RESPONSE(103),

    /**
     * <pre>
     * klog日志响应
     * </pre>
     *
     * <code>KLOG_REQUEST = 103;</code>
     */
    USER_CUSTOM_REQUEST(104),
    USER_CUSTOM_RESPONSE(105),

    HEART_REQUEST(106),
    HEART_RESPONSE(107),




    UNRECOGNIZED(-1),
    ;

    /**
     * <code>DEFAULT = 0;</code>
     */
    public static final int DEFAULT_VALUE = 0;
    /**
     * <pre>
     *客户端连接成功返回消息
     * </pre>
     *
     * <code>CONNECT_RESPONSE = 1;</code>
     */
    public static final int CONNECT_RESPONSE_VALUE = 1;
    /**
     * <pre>
     *调试请求
     * </pre>
     *
     * <code>DEBBUG_REQUEST = 100;</code>
     */
    public static final int DEBBUG_REQUEST_VALUE = 100;
    /**
     * <code>DEBBUG_RESPONSE = 101;</code>
     */
    public static final int DEBBUG_RESPONSE_VALUE = 101;

    /**
     * <code>KLOG_REQUEST_VALUE = 102;</code>
     */
    public static final int KLOG_REQUEST_VALUE = 102;

    /**
     * <code>KLOG_RESPONSE_VALUE;</code>
     */
    public static final int KLOG_RESPONSE_VALUE = 103;


    /**
     * <code>KLOG_RESPONSE_VALUE;</code>
     */
    public static final int USER_CUSTOM_REQUEST_VALUE = 104;
    public static final int USER_CUSTOM_RESPONSE_VALUE = 105;


    /**
     * <code>心跳;</code>
     */
    public static final int HEART_REQUEST_VALUE = 106;
    public static final int HEART_RESPONSE_VALUE = 107;

    public final int getNumber() {
        if (this == UNRECOGNIZED) {
            throw new IllegalArgumentException(
                    "Can't get the number of an unknown enum value.");
        }
        return value;
    }

    public static SocketHeadType valueOf(int value) {
        return forNumber(value);
    }

    public static SocketHeadType forNumber(int value) {
        switch (value) {
            case 0: return DEFAULT;
            case 1: return CONNECT_RESPONSE;
            case 100: return DEBBUG_REQUEST;
            case 101: return DEBBUG_RESPONSE;
            case 102: return KLOG_REQUEST;
            case 103: return KLOG_RESPONSE;
            case 104: return USER_CUSTOM_REQUEST;
            case 105: return USER_CUSTOM_RESPONSE;
            case 106: return HEART_REQUEST;
            case 107: return HEART_RESPONSE;
            case 110: return PINE;
            default: return null;
        }
    }

    private final int value;

    SocketHeadType(int value) {
        this.value = value;
    }
}
