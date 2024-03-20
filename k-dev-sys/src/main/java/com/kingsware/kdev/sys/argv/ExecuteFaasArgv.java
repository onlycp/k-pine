package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;

/**
 * @author chenp
 * @date 2024/3/13
 */
@Data
public class ExecuteFaasArgv extends BasePageArgv {

    /**
     * flowId
     */
    private String flowId;
    /**
     * variables
     */
    private String variables;

}
