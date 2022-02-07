package com.kingsware.kdev.core.cache.access;

import com.kingsware.kdev.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问配置
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 5:29 下午
 */
public class AccessManager {
    private static AccessManager instance;

    /** 超级管理员id **/
    private String supperAdminRoleId;
    /** 权限资源map **/
    private Map<String, String> accessTables = new HashMap<>();



    public static AccessManager getInstance() {
        if (instance == null) {
            synchronized (AccessManager.class) {
                if (instance == null) {
                    instance = new AccessManager();
                }
            }
        }
        return instance;
    }

    private AccessManager() {
    }

    /**
     * 判断是否超级管理员
     * @param roleIds   用户角色列表
     * @return          是否超级管理员
     */
    public boolean isSupperAdmin(String roleIds) {
        if (StringUtils.isEmpty(supperAdminRoleId)) {
            return false;
        }
        if (StringUtils.isEmpty(roleIds)) {
            return false;
        }
        String[] arr = roleIds.split(",");
        for (String a: arr) {
            if (supperAdminRoleId.equals(a)) {
                return true;
            }
        }
        return false;

    }

    public void setSupperAdminRoleId(String supperAdminRoleId) {
        this.supperAdminRoleId = supperAdminRoleId;
    }

    public void setAccessTables(Map<String, String> accessTables) {
        this.accessTables = accessTables;
    }

    public Map<String, String> getAccessTables() {
        return accessTables;
    }
}
