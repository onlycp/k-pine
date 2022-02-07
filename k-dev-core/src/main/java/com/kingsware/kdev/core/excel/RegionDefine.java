package com.kingsware.kdev.core.excel;

import com.kingsware.kdev.core.excel.format.*;
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
    /** 属性名 **/
    private String propName;
    /** label名 **/
    private String labelName;
    /** 格式化 **/
    private RegionFormat format;
    /** 示例值 **/
    private String example;

    /**
     * 日期时间格式化
     * @param propName  属性名
     * @param labelName 标签
     * @return          区域定义
     */
    public static RegionDefine dateTimeDefine(String propName, String labelName) {
        return RegionDefine.builder().propName(propName).labelName(labelName).format(new RegionDateTimeFormat()).build();
    }

    /**
     * 日期格式化
     * @param propName  属性名
     * @param labelName 标签
     * @return          区域定义
     */
    public static RegionDefine dateDefine(String propName, String labelName) {
        return RegionDefine.builder().propName(propName).labelName(labelName).format(new RegionDateFormat()).build();
    }

    /**
     * 日期格式化
     * @param propName  属性名
     * @param labelName 标签
     * @return          区域定义
     */
    public static RegionDefine timeDefine(String propName, String labelName) {
        return RegionDefine.builder().propName(propName).labelName(labelName).format(new RegionTimeFormat()).build();
    }

    /**
     * 日期格式化
     * @param propName  属性名
     * @param labelName 标签
     * @return          区域定义
     */
    public static RegionDefine textDefine(String propName, String labelName) {
        return RegionDefine.builder().propName(propName).labelName(labelName).build();
    }

    /**
     * 字典格式化
     * @param propName  属性名
     * @param labelName 标签
     * @param dictCode 字典码
     * @return          区域定义
     */
    public static RegionDefine dateDefine(String propName, String labelName, String dictCode) {
        return RegionDefine.builder().propName(propName).labelName(labelName).format(new RegionDictFormat(dictCode)).build();
    }

}
