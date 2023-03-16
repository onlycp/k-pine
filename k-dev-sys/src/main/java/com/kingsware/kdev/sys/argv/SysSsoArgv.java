package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * @author chenp
 * @date 2023/3/15
 */
@Data
public class SysSsoArgv {
    /** 类型 1: 用户名 2:用户id **/
    private Integer type;
    /** uid **/
    private String uid;
}
