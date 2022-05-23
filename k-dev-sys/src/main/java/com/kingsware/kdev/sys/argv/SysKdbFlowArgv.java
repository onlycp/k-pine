package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

/**
 * 新增流程实体
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysKdbFlowArgv {
    /** 流程id **/
    private String id;
    /** 流程内容 **/
    private String content;
    /** 名称 **/
    private String name;
    /** 标签 **/
    private String tags;
    /** 输入参数 **/
    private String inArgv;
    /** 输出参数 **/
    private String outArgv;
    /** 应用id **/
    private String applicationId;
    /** 描述 **/
    private String description;
    /** 默认数据源 **/
    private String defaultSourceName;
    /** 关联接口URL **/
    private String apiUrl;
    /** 关联接口请求方式 **/
    private String apiMethod;

}
