package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 账号参数查询
 * @version 1.0.0
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwEditionAccountQueryArgv extends BasePageArgv {
    /** 登录网银账号 **/
    private String bankAccount;
    /** 云柜ip **/
    private String usbIp;
}
