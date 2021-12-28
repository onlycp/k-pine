package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 演示查询传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
@Data
@EqualsAndHashCode
public class SysDemoQueryArgv extends BasePageArgv {
    /** 名称 **/
    private String name;
}
