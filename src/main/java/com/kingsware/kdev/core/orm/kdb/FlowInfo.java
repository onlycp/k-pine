package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 流程信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 4:20 下午
 */
@Data
public class FlowInfo {

    /** 流程id **/
    private String flowID;
    /** 流程内容 **/
    private String content;
}
