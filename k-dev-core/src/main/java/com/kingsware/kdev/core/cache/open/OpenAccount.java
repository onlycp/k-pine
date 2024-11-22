package com.kingsware.kdev.core.cache.open;

import com.kingsware.kdev.core.bean.BaseManageModel;
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
public class OpenAccount extends BaseManageModel {


    /**
     * 接入者ID
     */
    private String accessId;

    /**
     * 接入者名称
     */
    private String accessName;

    /**
     * 参数配置
     */
    private String authParams;

    /**
     * 授权类型
     * 1：简单模式，即access_id为access_token，此时token是固定的
     */
    private Integer authType;

    /**
     * 失效日期
     */
    private String invalidDate;

    /**
     * 签名密钥
     */
    private String signKey;

    /**
     * 是否启用
     */
    private Integer status = 1;

    /**
     * 生效日期
     */
    private String validDate;

    /**
     * 是否验签
     */
    private Integer validateSign = 0;

    /**
     * 应用ID
     */
    private String appId;



}
