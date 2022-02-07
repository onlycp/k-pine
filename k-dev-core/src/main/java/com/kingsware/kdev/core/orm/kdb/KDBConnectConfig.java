package com.kingsware.kdev.core.orm.kdb;

import com.kingsware.kdev.core.orm.DBConnectConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * KDB配置文件
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/21 2:51 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KDBConnectConfig extends DBConnectConfig {
    /** 服务器地址 **/
    private String server;
    /** sql透传接口 **/
    private String executeSqlApi;
    /** 数据库名称 **/
    private String dbName;
    /** 数据源名称 **/
    private String dataSource;

    /**
     * 将配置转为多个, 针对kdb的内部多数据源
     * @return  多个数据源
     */
    public List<KDBConnectConfig> toMany() {
        String[] dbs = this.dbName.split(",");
        List<KDBConnectConfig> configs = new ArrayList<>();
        for (String db: dbs) {
            KDBConnectConfig config = new KDBConnectConfig();
            config.setDatabaseType(this.getDatabaseType());
            config.setChannel(this.getChannel());
            config.setServer(this.getServer());
            config.setExecuteSqlApi(this.getExecuteSqlApi());
            String[] arr = db.split(":");
            config.setDbName(arr[0]);
            config.setInnerType(arr[2]);
            config.setDataSource(arr[1]);
            configs.add(config);
        }
        return configs;
    }
}
