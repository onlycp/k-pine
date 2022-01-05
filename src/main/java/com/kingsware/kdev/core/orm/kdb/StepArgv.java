package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

import java.util.List;

/**
 * 节点参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 2:26 下午
 */
@Data
public class StepArgv {
    /** 数据源 **/
    private String sourceName;
    /** 内容 **/
    private String content;
    /** 参数 **/
    private List<Object> params;
}
