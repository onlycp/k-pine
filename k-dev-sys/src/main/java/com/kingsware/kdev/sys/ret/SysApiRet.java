package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口响应类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysApiRet extends BaseManageRet {
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
    /** 结果处理类 **/
    private String apiResultHandler;
    /** 接口流程 **/
    private String apiFlowId;
    /** 接口编码 **/
    private String apiCode;
    /** 调用方式 **/
    private Integer callType;
}
