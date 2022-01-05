package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构表
 * @author chenpeng
 * @version 1.0.0
 * @date 2021-12-27 10:20
 */
@Data
public class KwEditionAccountRet extends BaseManageRet {

    /** 版本id */
    private String editionId;
    /** 登录类型 */
    private Integer loginType;
    /** 登录网银账号 **/
    private String bankAccount;
    /** 登录网银密码 **/
    private String bankPassword;
    /** 登录证书号 **/
    private String certNumber;
    /** ukey密码 **/
    private String ukeyPassword;
    /** 云柜ip **/
    private String usbIp;
    /** 云柜端口 **/
    private String usbPort;
    /** 云柜插口 **/
    private String usbGroup;
    /** 是否需要按ok **/
    private Integer isOkKey;
    /** ok_云柜ip **/
    private String usbIpOk;
    /** ok_云柜端口 **/
    private String usbPortOk;
    /** ok_云柜插口 **/
    private String usbGroupOk;
    /** 状态 **/
    private Integer status;
    /** 备用字段1 **/
    private String reserve1;
    /** 备用字段2 **/
    private String reserve2;
    /** 备用字段3 **/
    private String reserve3;
    /** 备用字段4 **/
    private String reserve4;
    /** 备用字段5 **/
    private String reserve5;

    /** 行别名称 **/
    private String bankName;
    /** 版本名称 **/
    private String editionName;

}
