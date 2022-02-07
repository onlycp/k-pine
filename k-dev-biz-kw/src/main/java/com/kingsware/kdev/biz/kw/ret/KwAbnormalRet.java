package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class KwAbnormalRet extends BaseManageRet {

    /** 行别（机构） */
    private String mechanismName;
    private String mechanismId;

    /** 版本 */
    private String editionName;
    private String editionId;

    /** 版本账号 */
    private String editionAccount;
    private String editionAccountId;

    /** 账户数量 */
    private Integer accountNum;
    /** 余额异常 */
    private Integer balanceException;
    /** 流水无回单 */
    private Integer noReceipt;
    /** 回单无流水 */
    private Integer noWater;


    /** 备用字段1 */
    private String reserve1;
    /** 备用字段2 */
    private String reserve2;
    /** 备用字段3 */
    private String reserve3;
    /** 备用字段4 */
    private String reserve4;
    /** 备用字段5 */
    private String reserve5;
}
