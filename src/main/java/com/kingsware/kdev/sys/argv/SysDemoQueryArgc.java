package com.kingsware.kdev.sys.argv;

import com.kingsware.kdev.core.bean.BasePageArgc;

/**
 * 演示查询传参
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 9:47 上午
 */
public class SysDemoQueryArgc extends BasePageArgc {
    /** 名称 **/
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
