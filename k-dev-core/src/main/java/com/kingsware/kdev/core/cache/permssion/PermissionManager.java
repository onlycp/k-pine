package com.kingsware.kdev.core.cache.permssion;

import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 接口权限管理
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/26 5:12 PM
 */
@Slf4j
public class PermissionManager {

    /** 实例 **/
    private static PermissionManager instance;
    /** 权限map **/
    private final Map<String, Set<String>> permissionMap = new HashMap<>();

    public static PermissionManager getInstance() {
        if (instance == null) {
            instance = new PermissionManager();
        }
        return instance;
    }

    /**
     * 私有构造
     */
    private PermissionManager() {}


    /**
     * 刷新接口权限编码
     * @param roleIds   角色id
     */
    public void refreshPermissions(String roleIds) {
        log.info("刷新权限: {}", roleIds);
        // 如果是管理员，直接跳过
        if (AccessManager.getInstance().isSupperAdmin(roleIds)) {
            return;
        }
        if (StringUtils.isEmpty(roleIds)) {
            return;
        }

        String sql = "select DISTINCT(mn.path) from sys_role_menu rm \n" +
                "left join sys_menu mn on mn.id = rm.sys_menu_id\n" +
                "where rm.sys_role_id in  (";
        List<String> sortedList = sortRoleIds(roleIds);
        String key = StringUtils.joinToString(sortedList, ",");
        sortedList.add("a-1");
        List<String> params = new ArrayList<>();
        for (String str: sortedList) {
            params.add("?");
        }
        sql += StringUtils.joinToString(params, ",");
        sql += ")";
        List<String> paths = DB.findSingleAttributeList(String.class, sql, (Object[]) sortedList.toArray(new String[]{}));
        // 获取菜单ids
        Set<String> menuIds = new HashSet<>();
        for (String path: paths) {
            if (StringUtils.isEmpty(path)) {
                continue;
            }
            String[] ids = path.split("/");
            for (String mid: ids) {
                String tm = mid.trim();
                if (StringUtils.isNotEmpty(tm)) {
                    menuIds.add(tm);
                }
            }
        }
        Set<String> permissions = new HashSet<>();
        if (!menuIds.isEmpty()) {
            // 查询用户所有的菜单
            String selectApiCodeSql = "select api_codes from sys_menu where api_codes is not null  and id in (";
            params.clear();;
            for (int i=0; i< menuIds.size(); i++) {
                params.add("?");
            }
            selectApiCodeSql += StringUtils.joinToString(params, ",");
            selectApiCodeSql += ")";
            List<String> apiCodeList =  DB.findSingleAttributeList(String.class, selectApiCodeSql, (Object[]) menuIds.toArray(new String[]{}));

            for (String apiCode: apiCodeList) {
                if (StringUtils.isNotEmpty(apiCode)) {
                    String[] codes = apiCode.split(",");
                    permissions.addAll(Sets.newHashSet(codes));
                }
            }
        }
        permissionMap.put(key, permissions);
    }

    /**
     * 刷新权限
     */
    public void refreshAll() {
        log.info("刷新权限");
        Set<String> keys = permissionMap.keySet();
        for (String key: keys) {
            refreshPermissions(key);
        }

    }

    /**
     * 排序角色
     * @param roleIds   角色ids
     * @return  排序后的角色
     */
    private List<String> sortRoleIds(String roleIds) {
        String[] roleArr = roleIds.split(",");
        List<String> ids = Arrays.asList(roleArr);
        return ids.stream().sorted().collect(Collectors.toList());
    }

    /**
     * 获取是否有权限
     * @param roleIds   角色id
     * @param apiCode   接口码
     * @return  返回是否有权限
     */
    public boolean hasPermission(String roleIds, String apiCode) {
        // 如果是管理员，直接跳过
        if (AccessManager.getInstance().isSupperAdmin(roleIds)) {
             return true;
        }
        // 校验接口权限
        if (StringUtils.isEmpty(apiCode)) {
            return true;
        }
        if (StringUtils.isEmpty(roleIds)) {
            return false;
        }

        List<String> sortedList = sortRoleIds(roleIds);
        String key = StringUtils.joinToString(sortedList, ",");
        if (!permissionMap.containsKey(key)) {
            refreshPermissions(roleIds);
        }
        Set<String> permissions = permissionMap.get(key);
        return permissions.contains(apiCode);

    }

}
