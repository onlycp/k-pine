package com.kingsware.kdev.biz.kw.argv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 接收前端回单
 * @version 1.0.0
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode
public class KwReceiptArgv {
    /** id*/
    private String id;
    /** 流水ID*/
    private String waterId;
    /** 账户id accountId */
    private String accountId;
    /** 回单编号*/
    private String receiptNumber;
    /** 记帐日期*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Timestamp bookDate;
    /** 付款人姓名*/
    private String draweeName;
    /** 付款人帐号*/
    private String draweeAccountNumber;
    /** 付款人开户行*/
    private String draweeBank;
    /** 收款人姓名*/
    private String payeeName;
    /** 收款人帐号*/
    private String payeeAccountNumber;
    /** 金额元*/
    private String amount;
    /** 摘要*/
    private String abstractInfo;
    /** 用途*/
    private String purpose;
    /** 交易币种 currency*/
    private Integer currency;
    /** 验证码*/
    private String code;
    /** 回单文件路径*/
    private String filePath;
    /** 回单来源*/
    private Integer source;
    /** 其他数据*/
    private String otherData;
    /** 数据状态 status*/
    private Integer status;
    /** 是否有流水*/
    private Integer hasWater;
    /** 登记时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp registerTime;
    /** 流水号*/
    private String serialNumber;
    /** 收款人开户银行*/
    private String payeeBank;
    /** 数据库文件路径*/
    private String fileId;
    /** 归属账号*/
    private String selfAccount;
    /** 数据序号*/
    private Integer dateIndex;


}
