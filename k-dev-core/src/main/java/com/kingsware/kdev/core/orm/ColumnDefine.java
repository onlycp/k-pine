package com.kingsware.kdev.core.orm;

import lombok.Data;

/**
 * 列定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 9:12 上午
 */
@Data
public class ColumnDefine {
    /** 表字段名 **/
    private String name;
    /**字符最大长度**/
    private Integer maxLength;
    /**是否允许为空 **/
    private boolean isNullable;
    /** 列 key **/
    private String columnKey;
    /** 注释 **/
    private String comment;
    /** 数据类型 **/
    private String type;
}
