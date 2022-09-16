package com.kingsware.kdev.core.auth;

import com.kingsware.kdev.core.cache.access.AccessManager;
import com.kingsware.kdev.core.cache.access.DataResourceInfo;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.orm.kdb.DataSourceInfo;
import com.kingsware.kdev.core.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param sqlLink   sql拼接方式， in或exists
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

        // 如果不包括相关表，则不处理
        if (!AccessManager.getInstance().getAccessTables().containsKey(table.toLowerCase())) {
            return null;
        }

        // 获取附加sql
        DataResourceInfo dataSourceInfo = AccessManager.getInstance().getAccessTables().get(table.toLowerCase());
        String queryColumn = StringUtils.isEmpty(dataSourceInfo.getValueField()) ? "id" : dataSourceInfo.getValueField();
        // 如果用户的数据id为空， 返回永远不可能成真的条件
        String extraSql = dataSourceInfo.getExtraSql();
        if (StringUtils.isEmpty(userInfo.getAccessIds()) && StringUtils.isEmpty(extraSql)) {
            return "1 != 1";
        }

        // 环境变量
        if (StringUtils.isNotEmpty(extraSql)) {
            Map<String, String> contextMap = new HashMap<>();
            contextMap.put("userId", "'" + userInfo.getId() + "'");
            contextMap.put("username", "'" + userInfo.getUsername() + "'");
            contextMap.put("alias", alias);
            for (Map.Entry<String, String> entry: contextMap.entrySet()) {
                extraSql = extraSql.replaceAll("\\$\\{"+entry.getKey() +"}", entry.getValue());
            }
        }
        // 处理in
        String[] arr = userInfo.getAccessIds().split(",");
        List<String> inSet = new ArrayList<>(arr.length);
        // 加一个不可能存在的id进去，避免出现in空的问题
        inSet.add("'-1'");
        for (String a: arr) {
            inSet.add("'" + a + "'");
        }
        // 拼接权限sql(由于id是uuid，这里忽略table_name)
        if (sqlLink == SqlLink.EXISTS) {
            return MessageFormat.format("exists (" +
                    "select ar.data_id from sys_data_access_resource ar where ({0}.{1}=ar.data_id and ar.access_id in ({2}) ) {3} " +
                    ")", alias, queryColumn, StringUtils.joinToString(inSet, ","), extraSql == null ? "" : extraSql);
        }
        else if (sqlLink == SqlLink.IN) {
            return MessageFormat.format("{0}.{1} in (select ar.data_id from sys_data_access_resource ar where (ar.table_name=''{2}'' and  ar.access_id in ({3})) {4})", alias, queryColumn, table, StringUtils.joinToString(inSet, ","), extraSql);
        }
        return null;
    }
}
