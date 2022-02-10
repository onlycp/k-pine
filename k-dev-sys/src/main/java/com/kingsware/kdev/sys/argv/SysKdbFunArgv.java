package com.kingsware.kdev.sys.argv;

import lombok.Data;

/**
 * 新增函数参数
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/5 3:06 下午
 */
@Data
public class SysKdbFunArgv {
    /** id **/
    private String id;
    /** name **/
    private String name;
    /** 脚本 **/
    private String script;
    /** 脚本类型 **/
    private String type;
    /** 描述 **/
    private String desc;
}
