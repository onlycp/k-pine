package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/11 5:51 下午
 */
public class DataAccessUtil {

    public static final String IN_OR_EXISTS =  "IN";

    private DataAccessUtil() {
    }

    /**
     * 生成权限查询sql
     * @param table     表名
     * @param alias     简写
     * @return          权限sql
     */
    public static String getDataAccessSql(String table, String alias, SqlLink sqlLink ) {
        // 获取用户信息
        BaseUserInfo userInfo = KClientContext.getContext().getUserInfo();
        // 如果不是web登录或者不登录
        if (userInfo == null) {
            return null;
        }
        // 如果是超级管理员
        if (AccessManager.getInstance().isSupperAdmin(userInfo.getRoleIds())) {
            return null;
        }
        // 如果用户的数据id为空， 返回永远不可能成真的条件
        if (StringUtils.isEmpty(userInfo.getAccessIds())) {
            return "1 != 1";
        }
        // 处理in
        String[] arr = userInfo.getAccessIds().split(",");
        List<String> inSet = new ArrayList<>(arr.length);
        for (String a: arr) {
            inSet.add("'" + a + "'");
        }
        // 拼接权限sql(由于id是uuid，这里忽略table_name)
        if (sqlLink == SqlLink.EXISTS) {
            return "exists (select ar.data_id from sys_data_access_resource ar where " + alias + ".id=ar.data_id and ar.access_id in (" + StringUtils.joinToString(inSet, ",") + "))";
        }
        else if (sqlLink == SqlLink.IN) {
            return  alias +".id in (select ar.data_id from sys_data_access_resource ar where ar.table_name='"+ table+ "' and  ar.access_id in (" + StringUtils.joinToString(inSet, ",") + "))";
        }
        return null;
    }
}
