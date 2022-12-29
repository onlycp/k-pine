package com.kingsware.kdev.core.excel;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    /** 数据类型 number: 数字, formula：公式 , string: 字符串, image: 图片, 默认是根据值的类型来自动识别  **/
    private String type;
    /** 样式 **/
    private KRegionStyle style;
    /** 数据项 **/
    private List<String> items = new ArrayList<>();

    /**
     * 是否单个单元格
     * @return  是否
     */
    public boolean isSingleCell() {
        return startCell.getRowIndex() == endCell.getRowIndex() && startCell.getColumnIndex() == endCell.getColumnIndex();
    }


}
