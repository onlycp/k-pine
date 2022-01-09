package com.kingsware.kdev.biz.kw.argv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行账户列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwBankAccountQueryArgv extends BasePageArgv {
    /** 行别ID */
    private String mechanismId;
    /** 账户 */
    private String account;
    /** 所属公司 */
    private String companyName;
    /** 开始日期 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp updateDateStartDate;
    /** 结束日期 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp updateDateEndDate;

}
