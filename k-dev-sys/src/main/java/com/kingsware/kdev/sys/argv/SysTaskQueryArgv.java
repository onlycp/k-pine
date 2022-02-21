package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTaskQueryArgv extends BasePageArgv {

    /** 名称 **/
    private String name;
    /** 是否分布式 **/
    private Integer distributed;
    /** 任务类型, 1：Java类 2：流程 3:java脚本  **/
    private Integer taskType;
    /** 是否启用 **/
    private Integer enable;

}
