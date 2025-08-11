package com.kingsware.kdev.sys.argv;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 页面修改记录表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
public class SysLogicHistoryArgv {

    /** ID */
    private String id;

    /** 流程ID */
    private String flowId;

    /** 流程JSON */
    private String flowJson;

    /** 创建人员 **/
    private String whoCreated;

    /** 创建时间 **/
    private Timestamp whenCreated;

}
