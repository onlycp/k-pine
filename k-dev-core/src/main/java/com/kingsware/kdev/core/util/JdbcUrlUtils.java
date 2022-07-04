package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.bean.JdbcUrl;

/**
 * jdbc url工具
 */
public class JdbcUrlUtils {

    private JdbcUrlUtils(){};

    /**
     * 解析url
     * @param url   url
     * @return      jdbc连接信息
     */
    public static JdbcUrl parseUrl(String url) {

        JdbcUrl jdbcUrl = new JdbcUrl();
        // 解析jdbc url
        String str = url;
        int tagLastIndex = str.indexOf(":");
        String jdbcTag = str.substring(0, tagLastIndex );
        // 获取数据权类型
        str = str.substring(tagLastIndex + 1);
        int tagDbTypeIndex = str.indexOf(":");
        String dbType = str.substring(0, tagDbTypeIndex);
        // 获取服务器ip和端口
        str = str.substring(tagDbTypeIndex + 3);
        int serverIndex = str.indexOf("/");
        if (serverIndex == -1) {
            serverIndex = str.indexOf(";");
        }
        String server = str.substring(0, serverIndex);
        // 获取数据库名
        str = str.substring(serverIndex + 1);
        int dbIndex = str.indexOf("?");
        boolean hasParams = true;
        if (dbIndex == -1) {
            dbIndex = str.length();
            hasParams = false;
        }
        String dbName = str.substring(0, dbIndex);
        jdbcUrl.setJdbcTag(jdbcTag);
        jdbcUrl.setDbType(dbType);
        jdbcUrl.setServer(server);
        jdbcUrl.setDbName(dbName);
        // 获取后面的参数
        if (hasParams) {
            str = str.substring(dbIndex + 1);
            String[] params = str.split("&");
            for (String paramPair: params) {
                if (StringUtils.isEmpty(paramPair)) {
                    continue;
                }
                String[] kv = paramPair.split("=");
                if (kv.length == 2) {
                    jdbcUrl.getParams().put(kv[0], kv[1]);
                }
            }

        }
        return jdbcUrl;
    }
}
