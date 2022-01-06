package com.kingsware.kdev.core.excel;

import lombok.Builder;
import lombok.Data;

/**
 * 格式化
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 6:29 下午
 */
@Builder
@Data
public class RegionDefine {
    /** 序号 **/
    private int index;
    /** 属性名 **/
    private String propName;
    /** label名 **/
    private String labelName;
    /** 格式化 **/
    private RegionFormat format;
}
