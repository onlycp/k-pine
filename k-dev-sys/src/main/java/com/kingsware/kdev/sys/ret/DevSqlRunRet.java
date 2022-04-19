package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.orm.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/4/19 14:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DevSqlRunRet extends BaseSimpleRet {
    private Integer max;
}
