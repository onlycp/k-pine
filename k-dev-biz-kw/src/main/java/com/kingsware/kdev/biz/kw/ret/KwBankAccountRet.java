package com.kingsware.kdev.biz.kw.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行账户
 * @date 2022/1/5 10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountRet extends BaseManageRet {
    /** 版本id */
    private String mechanismId;
    /** 机构名称 */
    private String mechanismName;
    /** 版本id */
    private String editionId;
    /** 版本名称 */
    private String editionName;
    /** 版本账号id */
    private String editionAccountId;
    /** 账户 */
    private String account;
    /** 账户类型 */
    private Integer accountType;
    /** 账户查询频率 */
    private String cxpl;
    /** 开户行 */
    private String bankDeposit;
    /** 公司名 */
    private String companyName;
    /** 账户余额 */
    private BigDecimal balance;
    /** 账户余额更新时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp balanceUpdateTime;
    /** 账户状态 */
    private Integer status;
    /** 数据来源 */
    private Integer dataSource;
    /** 开户时间 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp createAccountTime;
    /** 销户时间 */
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Timestamp cancelAccountTime;

    /** 账户流水余额 water_balance*/
    private String waterBalance;
    /** 版本登录账号 edition_account*/
    private String editionAccount;


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
    /** 关联类型：0待选择默认值；<br>1为单位；<br>2为项目； */
    private Integer relationType;
    /** 关联id */
    private String relationId;
    /** 关联id */
    private String proId;
    /** 是否流水发送到mbs系统 */
    private Integer mbsSend;
    /** 从视图获取的账户状态 */
    private String accountStatus;

    /** 是否余额检查 */
    private Integer balanceCheck;
}
