package com.kingsware.kdev.core.orm;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 表定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/25 9:11 上午
 */
@Data
public class TableDefine {
    /** 表名 **/
    private String tableName;
    /** 注释描述 **/
    private String comments;
}
