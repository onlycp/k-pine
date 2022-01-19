package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 项目扩展表列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountExpandQueryArgv extends BasePageArgv {
    /** 项目编号 */
    private String proNum;
    /** 项目名称 */
    private String proName;
    /** 银行账号 */
    private String account;
}
