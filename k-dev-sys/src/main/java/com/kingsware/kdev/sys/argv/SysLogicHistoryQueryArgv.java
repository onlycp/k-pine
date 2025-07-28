package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
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
public class SysLogicHistoryQueryArgv extends BasePageArgv {

    /** 流程ID */
    private String flowId;

    /** 流程JSON */
    private String flowJson;

    /** 关键字 */
    private String keyword;

    /** 创建人员 **/
    private String whoCreated;

    /** 创建时间 **/
    private Timestamp whenCreated;

}
