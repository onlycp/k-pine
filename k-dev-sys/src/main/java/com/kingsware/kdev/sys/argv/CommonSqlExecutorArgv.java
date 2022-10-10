package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CommonSqlExecutorArgv {

    private String sourceName;

    private String sql;

}
