package com.kingsware.kdev.core.bean;

import com.kingsware.kdev.core.util.StringUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JdbcUrl {
    /** jdbc的标签，一般固定为jdbc **/
    private String jdbcTag;
    /**　数据库类型　**/
    private String dbType;
    /** 服务器信息， ip:port **/
    private String server;
    /** 数据库名称 **/
    private String dbName;
    /** 参数对 **/
    private Map<String, String> params = new HashMap<>();

    public String build() {
        String url = String.format("%s:%s://%s/%s", jdbcTag, dbType, server, dbName);
        if (!params.isEmpty()) {
            List<String> paramList = new ArrayList<>();
            for (Map.Entry<String, String> entry: params.entrySet()) {
                paramList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }
            String paramSting = StringUtils.joinToString(paramList, "&");
            url += "?";
            url += paramSting;
        }
        return url;
    }
}
