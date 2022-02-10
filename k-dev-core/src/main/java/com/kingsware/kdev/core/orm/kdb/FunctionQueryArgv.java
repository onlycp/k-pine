package com.kingsware.kdev.core.orm.kdb;

import lombok.Data;

/**
 * 函数信息
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/10 6:12 下午
 */
@Data
public class FunctionQueryArgv {
    /** id **/
    private String id;
    /** name **/
    private String name;
    /** 脚本类型 **/
    private String type;
}
