package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
@EqualsAndHashCode
public class ApiInfo{

    // 定义字符集常量
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String CHARSET_GBK = "gbk";
    private static final String CHARSET_GB2312 = "gb2312";
    private static final String CHARSET_GB18030 = "gb18030";
    private static final String CHARSET_BIG5 = "big5";
    private static final String CHARSET_UTF8 = "utf-8";
    private static final String CHARSET_UTF16 = "utf-16";
    private static final String CHARSET_UTF16LE = "utf-16le";
    private static final String CHARSET_UTF16BE = "utf-16be";

    /** 接口 **/
    private String id;
    /** 接口名称 */
    private String apiName;
    /** 接口路径 */
    private String apiUrl;
    /** 接口方法 */
    private String apiMethod;
    /** 调用方式 **/
    private Integer callType;
    /** 接口流程 **/
    private String apiFlowId;
    /** 接口编码 **/
    private String apiCode;
    /** 输入参数定义 **/
    private String inArgv;
    /** 输出参数定义 **/
    private String outArgv;
    /** 子流程ids **/
    private String subFlowIds;
    /** 应用id **/
    private String appId;
    /** 创建人 **/
    private String whoCreated;
    /** 修改人员 **/
    private String whoModified;
    /** 修改时间 **/
    private String whenModified;
    private String apiTags;
    private String apiResultHandler;
    // 响应结果适配器
    private String apiRspArgv;
    // 是否启用接口缓存
    private Integer cacheEnable;
    // 缓存刷新表达式
    private String cacheCron;
    // 缓存过期时长（毫秒）
    private Integer cacheExpireTime;


    public String getReqeustCharset() {
        if (StringUtils.isEmpty(apiTags)) {
            return DEFAULT_CHARSET;
        }
        String[] arr = apiTags.replace(";", ",").split(",");
        for (String a : arr) {
            String trimmedTag = a.trim();
            if (CHARSET_GBK.equalsIgnoreCase(trimmedTag)) {
                return CHARSET_GBK;
            }
            else if (CHARSET_GB2312.equalsIgnoreCase(trimmedTag) || "gb2312-80".equalsIgnoreCase(trimmedTag)) {
                return CHARSET_GB2312;
            }
            else if (CHARSET_GB18030.equalsIgnoreCase(trimmedTag)) {
                return CHARSET_GB18030;
            }
            else if (CHARSET_BIG5.equalsIgnoreCase(trimmedTag) || "big-5".equalsIgnoreCase(trimmedTag)) {
                return CHARSET_BIG5;
            }
            else if (CHARSET_UTF8.equalsIgnoreCase(trimmedTag) || "utf8".equalsIgnoreCase(trimmedTag)) {
                return CHARSET_UTF8;
            }
            else if (CHARSET_UTF16.equalsIgnoreCase(trimmedTag) || "utf16".equalsIgnoreCase(trimmedTag)) {
                return CHARSET_UTF16;
            }
            else if (CHARSET_UTF16LE.equalsIgnoreCase(trimmedTag)) {
                return CHARSET_UTF16LE;
            }
            else if (CHARSET_UTF16BE.equalsIgnoreCase(trimmedTag)) {
                return CHARSET_UTF16BE;
            }
        }
        // 如果没有找到指定的字符集，返回默认值
        return DEFAULT_CHARSET;
    }


}
