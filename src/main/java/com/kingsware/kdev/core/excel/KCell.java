package com.kingsware.kdev.core.excel;

import lombok.Data;

/**
 * 单元格定义
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 3:04 下午
 */
@Data
public class KCell {
    /** 行号，从1开始 **/
    private int rowIndex;
    /** 列号, 从1开始**/
    private int columnIndex;
}
