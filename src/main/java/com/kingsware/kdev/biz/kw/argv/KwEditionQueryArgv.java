package com.kingsware.kdev.biz.kw.argv;

import com.kingsware.kdev.core.bean.BaseArgv;
import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 银行版本列表参数
 * @date 2022/1/4 18:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KwEditionQueryArgv extends BasePageArgv {
    /** 机构id */
    private String mechanismId;
    /** 版本名称 */
    private String name;
}
