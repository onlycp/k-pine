package com.kingsware.kdev.core.cache.instance;

import lombok.Data;

/**
 * 主机信息
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/2/20 11:06
 */
@Data
public class HostInfo {
    /** 主机名 **/
    private String hostName;
    /** 端口 **/
    private Integer port;
    /** 集群号 **/
    private Integer clusterNo;

    public String instanceName() {
        return hostName + ":" + port;
    }
}
