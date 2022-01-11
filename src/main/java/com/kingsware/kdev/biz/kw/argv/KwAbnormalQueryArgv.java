package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 异常总汇参数
 * @version 1.0.0
 * @description: 银行版本列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class KwAbnormalQueryArgv extends BasePageArgv {
    /** 行别（机构） */
    private String mechanismName;
    private String mechanismId;

    /** 版本 */
    private String editionName;
    private String editionId;

    /** 版本账号 */
    private String editionAccount;
    private String editionAccountId;


    /** 版本账号 */
    private String account;
    private String accountId;

    /** 时间范围 */
    private String startDate;
    private String endDate;
}
