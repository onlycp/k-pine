package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面修改记录表
 * @author AndyZheng
 * @version 1.0.0
 * @date 2022-02-13 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DevPageHistoryQueryArgv extends BasePageArgv {

    // 搜索关键字
    private String keywords;

    /** 可用状态 */
    private Integer enableStatus;

    /** 开发状态 */
    private Integer devStatus;

    /** 当前发布版本 */
    private String version;

    /** 负责人 */
    private String whoInCharge;

    /** 应用类型 */
    private String appType;
}
