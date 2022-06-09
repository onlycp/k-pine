package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author AndyZheng
 * @version 1.0.0
 * @description: 字典类型
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysKdbFlowQueryArgv extends BasePageArgv {
    /** 流程名称 **/
    private String name = "";
    /** 父级id **/
    private String parentId = "";
    /** 流程内容 **/
    private String content = "";
    /** 应用id **/
    private String applicationId;
    /** 标签 **/
    private String tags;
    /** 接口路径 **/
    private String apiUrl = "";

}
