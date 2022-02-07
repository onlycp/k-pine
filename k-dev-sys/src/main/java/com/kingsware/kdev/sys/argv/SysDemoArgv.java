package com.kingsware.kdev.sys.argv;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  演示传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysDemoArgv {

    /** id c**/
    private String id;
    /** 名称 **/
    private String name;
    /** 性别 0：男 1： 女 **/
    private String sex;
    /** 生日 **/
    private String birthday;
    /** 描述 **/
    private String note;
    /** 吃饭时间 **/
    private String eattime;

}
