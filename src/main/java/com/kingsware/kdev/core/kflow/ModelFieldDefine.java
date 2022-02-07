package com.kingsware.kdev.core.kflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型列定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/25 5:33 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelFieldDefine {
    /** 键名 **/
    private String field;
    /** 标签 **/
    private String label;
    /** 数据类型 **/
    private String type;
    /** 格式类型 **/
    private String formatType;
    /** 格式 **/
    private String formatPattern;
}
