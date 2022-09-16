package com.kingsware.kdev.core.cache.access;

import lombok.Data;

/**
 * 数据权限简要信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/7 2:00 下午
 */
@Data
public class DataResourceInfo {
    /** 表名 **/
    private String tableName;
    /** 附加sql **/
    private String extraSql;
    /** 关联字段 **/
    private String valueField;
}

