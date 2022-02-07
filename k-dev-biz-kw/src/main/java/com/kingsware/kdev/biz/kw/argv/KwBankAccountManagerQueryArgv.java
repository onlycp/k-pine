package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 项目客户经理列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountManagerQueryArgv extends BasePageArgv {
    /** 预约编号 */
    private String bookNumber;
    /** 项目编号 */
    private String proNum;

}
