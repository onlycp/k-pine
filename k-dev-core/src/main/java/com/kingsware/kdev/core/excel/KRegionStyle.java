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
    /** 背景色 **/
    private String bgColor;
    /** 字体 **/
    private String fontName;
    /** 字体颜色 **/
    private String fontColor;
    /** 字体大小 **/
    private Integer fontSize;
    /** 是否粗体 **/
    private boolean bold;
    /** 斜体 **/
    private boolean italic;
    /** 水平方向, 0:左 1：中间 2：右 **/
    private Integer h;
    /** 垂直方向 0：上 1：中间 2：下 **/
    private Integer v;
    /** 宽，字符**/
    private Integer width;
    /** 高，单位磅 **/
    private Integer height;
    /** 自动换行 **/
    private boolean wrapText;
    /** 小数点位 **/
    private Integer scale;
    /** 底部边框 0：无, 1：细, 2：中度, 3：虚线, 4：点线, 5：厚 **/
    private Integer borderBottom;
    /** 左边框 **/
    private Integer borderLeft;
    /** 底部边框 **/
    private Integer borderTop;
    /** 左边框 **/
    private Integer borderRight;
    /** 总边框 **/
    private Integer border;
    /** 边框色 **/
    private String borderColor;
}
