package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageModel;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.orm.annotation.LogicDelete;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行账户视图
 * @date 2022/1/18 17:13
 */
@Data
public class KwRPABankViewRet extends BaseSimpleRet {
    /** 银行账号 */
    private String account;
    /** 总行银行名称 */
    private String parentBankName;
    /** 支行银行名称 */
    private String bankName;
    /** 账户类型 */
    private String accountType;
    /** 账户状态 */
    private String accountStatus;
    /** 开户日期 */
    private String createAccountTime;
    /** 销户日期 */
    private String cancelAccountTime;
    /** 项目编号 */
    private String proNum;
    /** 项目名称 */
    private String proName;
    /** 项目阶段 */
    private String proPhase;
    /** 上架日期 */
    private String upDate;
    /** 下架日期 */
    private String downDate;
    /** 项目经理编号 */
    private String proPmAccount;
    /** 项目经理名称 */
    private String proPm;
    /** 信托会计编号 */
    private String trustAccountingAccount;
    /** 信托会计名称 */
    private String trustAccounting;
}
