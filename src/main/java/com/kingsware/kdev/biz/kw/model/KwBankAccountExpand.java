package com.kingsware.kdev.biz.kw.model;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 项目扩展表
 * @date 2022/1/5 10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table
public class KwBankAccountExpand extends BaseManageModel {
    /** 账户id */
    private String bankAccountId;
    /** 账户 */
    private String account;
    /** 项目编号 */
    private String proNum;
    /** 项目名称 */
    private String proName;
    /** 项目经理 */
    private String proPm;
    /** 项目阶段 */
    private String proPhase;
    /** 项目经理账号 */
    private String proPmAccount;
    /** 信托会计 */
    private String trustAccounting;
    /** 信托会计账号 */
    private String trustAccountingAccount;
    /** 项目上架日期 */
    private Timestamp upDate;
    /** 项目下架日期 */
    private Timestamp downDate;
    /** 项目录入日期 */
    private Timestamp inDate;
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
