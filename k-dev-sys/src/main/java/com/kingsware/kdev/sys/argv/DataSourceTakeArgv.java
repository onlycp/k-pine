package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BaseArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataSourceTakeArgv extends BaseArgv {

    private String sourceName;
    private String appId;
}
