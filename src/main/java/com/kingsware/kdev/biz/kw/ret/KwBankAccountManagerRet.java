package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseManageRet;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 项目客户经理表
 * @date 2022/1/5 10:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountManagerRet extends BaseManageRet {
    /** 项目id */
    private String proId;
    /** 产品编码 */
    private String proPm;
    /** 产品名称 */
    private String proPmAccount;
    /** 客户经理姓名 */
    private String customerManagerName;
    /** 客户名称 */
    private String customerName;
    /** 预约金额（元） */
    private String amount;
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
    /** 预约编号 */
    private String bookNumber;
    /** 客户编号 */
    private String customerNumber;
    /** 客户经理姓名 */
    private String customerManagerNumber;
    /** 预约时间 */
    private String bookTime;
}
