package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.cache.instance.HostInfo;
import com.kingsware.kdev.core.context.SpringContext;
import lombok.SneakyThrows;

import java.net.Inet4Address;
import java.util.Optional;

/**
 * @author chenp
 * @date 2023/2/21
 */
public class SystemUtil {

    private static String myIp = null;
    /**
     * 获取主机信息
     * @return
     */
    @SneakyThrows
    public static HostInfo getHost() {
        HostInfo hostInfo = new HostInfo();

        String ip = "127.0.0.1";
        // 如果有配置ip，直接从配置里读取
        String configIp = SpringContext.getProperties("network.ip", "");
        if (StringUtils.isNotEmpty(configIp)) {
            ip = configIp;
        }
        else {
            if (myIp != null) {
                ip = myIp;
            }
            else {
                Optional<Inet4Address> inet4Address = IpUtil.
                        getLocalIp4Address();
                if (inet4Address.isPresent()) {
                    ip = inet4Address.get().getHostAddress();
                    myIp = ip;
                }
            }

        }
        hostInfo.setHostName(ip);
        hostInfo.setPort(Integer.parseInt(SpringContext.getProperties("server.port", "8080")));
        return hostInfo;

    }

    /**
     * 获取当前主机的名称。
     * 该方法首先尝试从环境变量"COMPUTERNAME"中获取主机名，这是Windows系统的标准环境变量。
     * 如果在Windows环境中未找到该变量，方法将尝试从"HOSTNAME"环境变量中获取主机名，这是Linux/Unix系统的标准环境变量。
     *
     * @return 返回当前主机的名称。如果无法获取主机名，则返回null。
     */
    public static String getHostName() {
        String machineName = System.getenv("COMPUTERNAME"); // 尝试获取Windows系统的主机名
        if (machineName == null) {
            machineName = System.getenv("HOSTNAME"); // 如果是Linux/Unix系统，尝试获取主机名
        }
        return machineName;
    }

}
