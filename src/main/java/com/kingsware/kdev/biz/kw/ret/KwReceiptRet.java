package com.kingsware.kdev.biz.kw.ret;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * 机构返回
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwReceiptRet extends BaseManageRet {
    /** 行别（机构） */
    private String mechanismName;
    /** 行别（机构）id */
    private String mechanismId;
    /** 版本 */
    private String editionName;
    /** 版本id */
    private String editionId;
    /** 版本账号 */
    private String editionAccount;
    /** 版本账号id */
    private String editionAccountId;
    /** 开户行 bank_deposit*/
    private String bankDeposit;
    /** 账户 */
    private String account;
    /** 账户id */
    private String accountId;
    /** 账户名称 account_name */
    private String accountName;
    /**所属项目 pro_name*/
    private String proName;
    /** 流水id */
    private String waterId;


    /**电子回单号码 receipt_number */
    private String receiptNumber;
    /**记帐日期 book_date*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp bookDate;
    /**归属账号 self_account*/
    private String selfAccount;
    /**付款人姓名 drawee_name*/
    private String draweeName;
    /**付款人帐号 drawee_account_number */
    private String draweeaccountnumber;
    /**付款人开户银行 drawee_bank */
    private String draweebank;
    /**收款人姓名 payee_name*/
    private String payeeName;
    /**收款人帐号 payee_account_number*/
    private String payeeAccountNumber;
    /**收款人开户银行 payee_bank */
    private String payeeBank;
    /**金额(元) amount */
    private String amount;
    /**摘要 abstract_info*/
    private String abstractInfo;
    /**用途 purpose */
    private String purpose;
    /**币种 currency*/
    private Integer currency;
    /**验证码 code*/
    private String code;
    /**回单文件路径 file_path*/
    private String filePath;
    /**回单来源 source */
    private Integer source;
    /**其他数据 other_data */
    private String otherData;
    /**数据状态 status*/
    private Integer status;
    /**是否有流水 has_water*/
    private Integer hasWater;
    /**文件 file_id */
    private String fileId;
    /**数据序号 date_index */
    private String dateIndex;
    /**登记时间 register_time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp registerTime;

}
