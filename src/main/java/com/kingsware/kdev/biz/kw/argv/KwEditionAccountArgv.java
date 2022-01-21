package com.kingsware.kdev.biz.kw.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  角色参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class KwEditionAccountArgv {
    /** id **/
    private String id;
    /** 版本id **/
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

    /** 客户号 */
    private String reserve1;

}
