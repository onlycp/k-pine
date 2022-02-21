package com.kingsware.kdev.core.kflow.define;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * //todo 描述当前类是干什么用的.
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
}
