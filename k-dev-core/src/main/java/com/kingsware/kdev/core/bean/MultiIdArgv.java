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

    /**
     * 所属应用ID
     * 原因：根据id删除时，需要同步删除Git仓库数据，需要根据appId来判断仓库位置
     */
    private String appId;

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
