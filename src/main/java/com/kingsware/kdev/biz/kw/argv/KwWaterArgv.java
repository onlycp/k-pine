package com.kingsware.kdev.biz.kw.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 接收前端流水
 * @version 1.0.0
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode
public class KwWaterArgv {
    private String editionId;
    private String editionName;
    private String account;
    private String startDate;
    private String endDate;
}
