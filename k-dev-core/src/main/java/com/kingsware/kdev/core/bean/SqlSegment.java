package com.kingsware.kdev.core.bean;


import lombok.Data;

/**
 * DevModelSqlSegment类表示开发模型中的SQL代码段
 * 该类主要用于存储在开发过程中捕获的SQL语句及其所在行的信息
 */
@Data
public class SqlSegment {
    // SQL语句所在的行号，用于定位SQL语句在代码中的位置
    private Integer line;

    // SQL语句文本，存储实际的SQL代码
    private String sql;
}
