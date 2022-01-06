package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行账户列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountQueryArgv extends BasePageArgv {
    /** 行别ID */
    private String mechanismId;
    /** 账户 */
    private String account;
    /** 所属公司 */
    private String companyName;

}
