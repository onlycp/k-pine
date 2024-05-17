package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  接口参数类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysApiArgv {
    /** idc**/
    private String id;
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
    /** 响应适配器 **/
    private String apiRspArgv;
    /** 结果处理类 **/
    private String apiResultHandler;
    /** 接口流程 **/
    private String apiFlowId;
    /** 接口编码 **/
    private String apiCode;
    /** 调用方式 **/
    private Integer callType;
    /** 所属应用ID **/
    private String appId;
    /** 缓存开关 **/
    private Integer cacheEnable;
    /** 缓存过期时间 **/
    private Integer cacheExpireTime;
    /** 缓存刷新时间 **/
    private Integer cacheCron;
}
