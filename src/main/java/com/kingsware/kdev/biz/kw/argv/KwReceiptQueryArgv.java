package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 回单参数查询
 * @version 1.0.0
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class KwReceiptQueryArgv extends BasePageArgv {

    private String waterId;
    private String editionId;
    private String editionName;
    private String account;
    private String startDate;
    private String endDate;
}
