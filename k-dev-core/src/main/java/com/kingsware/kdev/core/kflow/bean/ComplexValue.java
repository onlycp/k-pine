package com.kingsware.kdev.core.kflow.bean;

import lombok.Data;

/**
 * 复合值
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/7 9:50 上午
 */
@Data
public class ComplexValue {
    /** 值 **/
    private Object value;
    /** 标签 **/
    private String label;
}
