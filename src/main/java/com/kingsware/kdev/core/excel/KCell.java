package com.kingsware.kdev.core.excel;

import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KCell cell = (KCell) o;
        return rowIndex == cell.rowIndex && columnIndex == cell.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }
}
