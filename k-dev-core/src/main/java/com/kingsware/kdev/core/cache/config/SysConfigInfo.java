package com.kingsware.kdev.core.cache.config;

import lombok.Data;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/8 5:09 下午
 */
@Data
public class SysConfigInfo {
    /** 参数名称 */
    private String name;
    /** 参数键名 */
    private String code;
    /** 参数类型 */
    private Integer valueType;
    /** 参数键值 */
    private String value;
    /** 是否系统内置 */
    private Integer isSys;
    /** 备注 */
    private String note;
}
