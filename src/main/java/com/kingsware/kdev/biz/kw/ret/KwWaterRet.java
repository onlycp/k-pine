package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import com.kingsware.kdev.core.orm.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 机构返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwWaterRet extends BaseManageRet {
    // 行别（机构）
    private String mechanismName;
    // 版本
    private String editionName;
    // 版本id
    private String editionId;
    // 账户
    private String account;
    // 账户id
    private String accountId;

    // 版本账号
    private String editionAccount;
    // 版本账号id
    private String editionAccountId;

    // 开户行 bank_deposit
    private String bankDeposit;

    // 所属项目 pro_name
    private String proName;

    // 回单id false
    private String receiptId;
    // 账户名称
    private String accountName;
    // 交易日期
    private Date transactionDate;
    // 交易时间
    private Date transactionTime;
    // 金额 transaction_amount
    private String transactionAmount;
    // 登记时间 register_time
    private Date registerTime;
    // 收支方向 revenue
    private Integer revenue;
    // 收支方式 cash_transfer
    private Integer cashTransfer;
    // 余额 account_balance
    private String accountBalance;
    // 数据来源 data_source
    private String dataSource;
    // 流水编号 serial_number
    private String serialNumber;
    // 交易类型 transaction_type
    private String transactionType;
    // 交易币种 currency
    private Integer currency;
    // 对方名称 other_name
    private String otherName;
    // 对方账户 other_account
    private String otherAccount;
    // 用途 purpose
    private String purpose;
    // 摘要 abstract
    private String abstractInfo;
    // 是否异常 abnormal
    private Integer abnormal;
    // 数据次序
    private Integer dateIndex;

}
