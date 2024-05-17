package com.kingsware.kdev.core.cache.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode
public class ApiInfo{
    /** 接口 **/
    private String id;
    /** 接口名称 */
    private String apiName;
    /** 接口路径 */
    private String apiUrl;
    /** 接口方法 */
    private String apiMethod;
    /** 调用方式 **/
    private Integer callType;
    /** 接口流程 **/
    private String apiFlowId;
    /** 接口编码 **/
    private String apiCode;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;
    /** 子流程ids **/
    private String subFlowIds;
    /** 应用id **/
    private String appId;
    /** 创建人 **/
    private String whoCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    private String whenModified;
    private String apiTags;
    private String apiResultHandler;
    // 响应结果适配器
    private String apiRspArgv;
    // 是否启用接口缓存
    private Integer cacheEnable;
    // 缓存刷新表达式
    private String cacheCron;
    // 缓存过期时长（毫秒）
    private Integer cacheExpireTime;

}
