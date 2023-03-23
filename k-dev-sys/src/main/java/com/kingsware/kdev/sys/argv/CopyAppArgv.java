package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/3/17
 */
@Data
public class CopyAppArgv {
    /** 团队id **/
    private String teamId;
    /** 名称 **/
    private String name;
    /** 名称后缀 **/
    private String nameSuffix;
    /** url后缀 **/
    private String urlSuffix;
    /** url后缀 **/
    private String codeSuffix;

}
