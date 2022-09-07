package com.kingsware.kdev.core.excel;

import lombok.Data;

/**
 * 区域样式
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/4 6:23 下午
 */
@Data
public class KRegionStyle {

    private String bgColor;
    /** 字体颜色 **/
    private String fontName;
    /** 字体颜色 **/
    private String fontColor;
    /** 字体大小 **/
    private Integer fontSize;
    /** 是否粗体 **/
    private boolean bold;
    /** 水平方向, 0:左 1：中间 2：右 **/
    private Integer h;
    /** 垂直方向 0：上 1：中间 2：下 **/
    private Integer v;


}
