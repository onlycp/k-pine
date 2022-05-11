package com.kingsware.kdev.core.cache.open;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/11 11:45 AM
 */
@Data
public class OpenAccountInfo {
    /** 接入商id **/
    private String accessId;
    /** 接入商名称 **/
    private String accessName;
    /** 认证类型 **/
    private Integer authType;
    /** 签名密钥 **/
    private String signKey;
    /** 是否签名 **/
    private Integer validateSign;
    /** 生效日期 **/
    private String validDate;
    /** 失效日期 **/
    private String invalidDate;
    /** 认证参数 **/
    private Map<String, Object> authParams = new HashMap<>();
    /** 接口编码 **/
    private Set<String> apiCodes = new HashSet<>();

}
