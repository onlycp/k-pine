package com.kingsware.kdev.sys.ret;

import lombok.Data;

/**
 * license信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/5/5 3:57 PM
 */
@Data
public class LicenseRet {

    /** 客户名称 **/
    private String customer;
    /** 生效日期 **/
    private String validDate;
    /** 失效日期 **/
    private String invalidDate;
    /** 状态 0: license不存在或无效 1:未生效 2: 已生效 3: 已失效 **/
    private Integer status = 0;
    /** mac **/
    private String mac;
    /** 错误信息 **/
    private String errorMessage;

}
