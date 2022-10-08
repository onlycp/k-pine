package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 角色返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
public class SysOperateLogRet extends BaseSimpleRet {
    /** id **/
    private String id;
    /** 模块，对应@Api **/
    private String module;
    /** 动作，对应@ApiOperation **/
    private String action;
    /** 路径 **/
    private String url;
    /** 操作时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp operateTime;
    /** 操作人员 **/
    private String operator;
    /** ip **/
    private String ip;
    /**  耗时 **/
    private int times;
    /** 请求内容体 **/
    private String requestBody;
    /** 响应码 **/
    private int responseCode;
    /** 响应消息 **/
    private String responseMessage;
    /** 创建时间 **/
    private String whenCreated;
    /** 所属应用ID **/
    private String appId;
    /*方法名称*/
    private String method;
    /*请求方式*/
    private String requestMethod;
    /*响应内容体*/
    private String responseBody;
}
