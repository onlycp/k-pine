package com.kingsware.kdev.core.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作区
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 3:00 下午
 */
@Data
public class KSheet {
    /** 序号 **/
    private int index;
    /** 名称 **/
    private String name;

    /** 区域清表 **/
    private List<KRegion> regions = new ArrayList<>();

    /**
     *  增加单元格区域
     * @param rowIndex      行号
     * @param columnIndex   列号
     * @param value         值
     * @return              返回当前区域
     */
    public KRegion addCellRegion(int rowIndex, int columnIndex, Object value) {
        return addRegion(rowIndex, columnIndex, rowIndex, columnIndex, value);
    }

    /**
     *  增加区域
     * @param rowIndex1      行号1
     * @param columnIndex1   列号1
     * @param rowIndex2      行号2
     * @param columnIndex2   列号2
     * @param value         值
     * @return              返回当前区域
     */
    public KRegion addRegion(int rowIndex1, int columnIndex1, int rowIndex2, int columnIndex2,  Object value) {
        // 创建区域
        KRegion region = new KRegion();
        // 创建单元格，并设置行和列
        // 单元格1
        KCell cell1 = new KCell();
        cell1.setRowIndex(rowIndex1);
        cell1.setColumnIndex(columnIndex1);
        // 单元格2
        KCell cell2 = new KCell();
        cell2.setRowIndex(rowIndex2);
        cell2.setColumnIndex(columnIndex2);
        // 区域的起始单元格和结束单元格设为一致
        region.setStartCell(cell1);
        region.setEndCell(cell2);
        // 设置值
        region.setValue(value);
        regions.add(region);
        // 返回当前区域
        return region;
    }
}
