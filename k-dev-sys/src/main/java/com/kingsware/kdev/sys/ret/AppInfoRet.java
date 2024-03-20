package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * AppInfoRet 类用于封装应用信息返回结果。
 */
@Data
public class AppInfoRet {
    // 服务器名称或地址
    private String server;
    // 服务器端口
    private int port;
    // 应用的状态码或标识码
    private String code;
    // 应用实例ID
    private String instanceId;
}
