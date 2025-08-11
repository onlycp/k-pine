package com.kingsware.kdev.sys.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 演示model
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:12 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class DevOtaChannel extends BaseManageModel {

    /** 通道名称 **/
    private String channelName;
    /** 通道地址 **/
    private String channelUrl;
    /** 安全令牌 **/
    private String authToken;
    /** 签名令牌 **/
    private String signSecret;
    /** 是否主通道 **/
    private Integer master;
    /** 备注 **/
    private String note;

}
