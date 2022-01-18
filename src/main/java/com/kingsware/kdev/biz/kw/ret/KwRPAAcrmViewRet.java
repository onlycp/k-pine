package com.kingsware.kdev.biz.kw.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import lombok.Data;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 用户视图
 * @date 2022/1/18 17:13
 */
@Data
public class KwRPAAcrmViewRet extends BaseSimpleRet {
    /** 预约编号 */
    private String bookNumber;
    /** 客户编号 */
    private String customerNumber;
    /** 客户姓名 */
    private String customerName;
    /** 理财经理编号 */
    private String customerManagerNumber;
    /** 客户经理 */
    private String customerManagerName;
    /** 预约金额 */
    private String amount;
    /** 预约时间 */
    private String bookTime;
    /** 产品编号 */
    private String proPmAccount;
    /** 产品名称 */
    private String proPm;
    /** 项目编号 */
    private String proNum;
    /** 项目名称 */
    private String proName;
}
