package com.kingsware.kdev.core.cache.api;

import lombok.Data;

/**
 * 接口表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
public class ApiInfo{

    /** 接口名称 */
    private String apiName;
    /** 接口路径 */
    private String apiUrl;
    /** 接口描述 */
    private String apiNote;
    /** 接口标签 **/
    private String apiTags;
    /** 接口方法 */
    private String apiMethod;
    /** 接口参数方式 */
    private Integer apiArgvType;
    /** 接口请求参数 **/
    private String apiReqArgv;
    /** 接口响应结果 **/
    private String apiRspArgv;
    /** 调用方式 **/
    private Integer callType;
    /** 结果处理类 **/
    private String apiResultHandler;
    /** 接口流程 **/
    private String apiFlowId;
    /** 接口编码 **/
    private String apiCode;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;

}
