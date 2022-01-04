package com.kingsware.kdev.core.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 基础业务返回类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/28 9:14 上午
 */
@Data
public class BaseManageRet extends BaseSimpleRet{

    /** id **/
    private String id;
    /** 创建人员 **/
    private String whoCreated;
    /** 创建时间 **/
    private Timestamp whenCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    private Timestamp whenModified;
}
