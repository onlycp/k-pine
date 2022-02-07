package com.kingsware.kdev.core.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 基础业务返回类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/28 9:14 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseManageRet extends BaseSimpleRet{

    /** id **/
    private String id;
    /** 创建人员 **/
    private String whoCreated;
    /** 创建时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp whenModified;
}
