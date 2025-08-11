package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 页面修改记录表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogicHistoryRet extends BaseSimpleRet {

    /** id **/
    private String id;

    /** 流程ID */
    private String flowId;

    /** 流程JSON */
    private String flowJson;

    /** 创建人员 **/
    private String whoCreated;

    /** 创建时间 **/
    private Timestamp whenCreated;

    /** 创建人员 **/
    private String createdUserName;

    /** 创建人员 **/
    private String createdUserAvatar;

}
