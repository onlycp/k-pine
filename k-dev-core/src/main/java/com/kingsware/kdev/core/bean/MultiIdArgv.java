package com.kingsware.kdev.core.bean;

import java.util.Set;

/**
 * 多选id传值
 * @author chenpeng
 * @date  2021-12-22
 */
public class MultiIdArgv extends BaseArgv{
    /** 多选id **/
    private Set<String> ids;

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }
}
