package com.kingsware.kdev.core.cache.api;

import com.kingsware.kdev.core.util.StringUtils;
import io.netty.util.internal.StringUtil;
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
    // 自动化国际键值
    private String i18nKeys;

    public String getReqeustCharset() {
        if (StringUtils.isEmpty(apiTags)) {
            return "utf-8";
        }
        String[] arr = apiTags.replace(";", ",").split(",");
        for (String a : arr) {
            String trimmedTag = a.trim();
            if ("gbk".equalsIgnoreCase(trimmedTag)) {
                return "gbk";
            }
            else if ("gb2312".equalsIgnoreCase(trimmedTag) || "gb2312-80".equalsIgnoreCase(trimmedTag)) {
                return "gb2312";
            }
            else if ("gb18030".equalsIgnoreCase(trimmedTag)) {
                return "gb18030";
            }
            else if ("big5".equalsIgnoreCase(trimmedTag) || "big-5".equalsIgnoreCase(trimmedTag)) {
                return "big5";
            }
            else if ("utf-8".equalsIgnoreCase(trimmedTag) || "utf8".equalsIgnoreCase(trimmedTag)) {
                return "utf-8";
            }
            else if ("utf-16".equalsIgnoreCase(trimmedTag) || "utf16".equalsIgnoreCase(trimmedTag)) {
                return "utf-16";
            }
            else if ("utf-16le".equalsIgnoreCase(trimmedTag)) {
                return "utf-16le";
            }
            else if ("utf-16be".equalsIgnoreCase(trimmedTag)) {
                return "utf-16be";
            }
        }
        // 如果没有找到指定的字符集，返回默认值
        return "utf-8";
    }

}
