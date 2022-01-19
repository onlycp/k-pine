package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 接收前端回单
 * @version 1.0.0
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode
public class KwReceiptArgv {
    private String editionId;
    private String editionName;
    private String account;
    private String startDate;
    private String endDate;
}
