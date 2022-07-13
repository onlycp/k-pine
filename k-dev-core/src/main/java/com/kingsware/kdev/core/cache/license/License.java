package com.kingsware.kdev.core.cache.license;

import lombok.Data;

/**
 * License
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/5 4:33 PM
 */
@Data
public class License {
    /** id **/
    private String id;
    /** 客户名称 **/
    private String customer;
    /** 机器码 **/
    private String mac;
    /** 生效日期 **/
    private String validDate;
    /** 失效日期 **/
    private String invalidDate;
    /** license **/
    private String license;
    /** 选项 **/
    private String options;
    /** 创建时间 **/
    private String whenCreated;
    /** 应用编码 **/
    private String appCode;
    /** 应用端口 **/
    private String appPort;
}
