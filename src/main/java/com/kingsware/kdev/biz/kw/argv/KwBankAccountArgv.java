package com.kingsware.kdev.biz.kw.argv;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.TimeZone;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行账户
 * @date 2022/1/5 10:13
 */
@Data
public class KwBankAccountArgv {
    /** 账户id */
    private String id;
    /** 版本id */
    private String editionId;
    /** 账户 */
    private String account;
    /** 账户类型 */
    private Integer accountType;
    /** 账户查询频率 */
    private String cxpl;
    /** 开户行 */
    private String bankDeposit;
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
    /** 是否流水发送到mbs系统 */
    private Integer mbsSend;
}
