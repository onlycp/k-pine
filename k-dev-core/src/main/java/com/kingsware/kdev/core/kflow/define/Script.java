package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/17 2:43 下午
 */
@Data
public class Script {
    /** 数据源名称 **/
    private String sourceName;
    /** 脚本内容 **/
    private String content;
    /** 类型 **/
    private String type;
    /** 参数 **/
    private List<Object> params = new ArrayList<>();
    /** 列表返回大小写, upper 大写, lower 小写, normal 正常/为空默认是这个 **/
    private String columnLabelCase;
}
