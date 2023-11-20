package com.kingsware.kdev.sys.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.orm.annotation.AutoEnum;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 登录日志返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
public class SysLoginLogRet extends BaseSimpleRet {
    /** id **/
    private String id;
    /** 操作时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp operateTime;
    /** 操作人员 **/
    private String operator;
    /** ip **/
    private String ip;
    /** address **/
    private String address;
    /**  耗时 **/
    private int times;
    /** 请求内容体 **/
    private String requestBody;
    /** 响应码 **/
    private String responseCode;
    /** 响应消息 **/
    private String responseMessage;
    /** 创建时间 **/
    private String whenCreated;
}
