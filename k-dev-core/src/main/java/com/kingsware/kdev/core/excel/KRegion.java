package com.kingsware.kdev.core.excel;

import lombok.Data;

/**
 * Excel的区域
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 3:12 下午
 */
@Data
public class KRegion {
    /** 开始单元格 **/
    private KCell startCell;
    /** 结束单元格 **/
    private KCell endCell;
    /** 值 **/
    private Object value;

    /**
     * 是否单个单元格
     * @return  是否
     */
    public boolean isSingleCell() {
        return startCell.getRowIndex() == endCell.getRowIndex() && startCell.getColumnIndex() == endCell.getColumnIndex();
    }


}
