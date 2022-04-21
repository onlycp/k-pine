package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * 接口调试返回内容
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/23 11:39 上午
 */
@Data
public class SysFlowDebugRet {
    /** 消耗 **/
    private Long takeMs;
    /** 执行状态. 1 成功  0 失败 **/
    private int code;
    /** 响应内容 **/
    private String responseBody;
    /** 日志 **/
    private String log;
}
