package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenp peng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperateLogQueryArgv extends BasePageArgv {

    /** 模块，对应@Api **/
    private String module;
    /** 动作，对应@ApiOperation **/
    private String action;
    /** 操作时间区间，用逗号分隔 **/
    private String operateTimes;
    /** 操作人员 **/
    private String operator;
    /* 响应码 用于筛选成功或者失败*/
    private Integer responseCode;
    /** 所属应用ID **/
    private String appId;
}
