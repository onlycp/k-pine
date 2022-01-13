package com.kingsware.kdev.biz.kw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.Column;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 流水表
 * @author
 * @version 1.0.0
 * @date 2022-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class KwWater extends BaseManageModel {

    // 账户 account
    private String account;
    // 账户id accountId
    private String accountId;
    // 回单id false
    private String receiptId;
    // 账户名称 accountName
    private String accountName;

    // 交易日期 transactionDate
//    private Date transactionDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Timestamp transactionDate;
    // 交易时间 transactionTime
//    private Date transactionTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone="GMT+8")
    private Timestamp transactionTime;
    // 登记时间 register_time
//    private Date registerTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp registerTime;

    // 金额 transaction_amount
    private String transactionAmount;
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
    // 数据状态 status
    private Integer status;
    // 是否异常 abnormal
    private Integer abnormal;
    // 异常处理状态 abnormal_status
    private Integer abnormalStatus;
    // 是否有回单 has_receipt
    private Integer hasReceipt;
    // 数据次序 date_index
    private Integer dateIndex;
    // 删除标志 deleted
    private Integer deleted;


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

}
