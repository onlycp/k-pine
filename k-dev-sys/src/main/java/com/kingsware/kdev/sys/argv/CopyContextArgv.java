package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/3/17
 */
@Data
public class CopyContextArgv {
    /** 名称后缀 **/
    private String nameSuffix;
    /** url后缀 **/
    private String urlSuffix;
    /** url后缀 **/
    private String codeSuffix;
    /** 源应用ic **/
    private String sourceAppId;
    /** 目标应用ic **/
    private String targetAppId;
    /** 是否拷贝系统数据 **/
    private Integer withSystemData;
    /** 是否深度拷贝 **/
    private Integer deepCopy;

}
