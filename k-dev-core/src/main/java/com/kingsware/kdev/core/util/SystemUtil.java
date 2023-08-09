package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.cache.instance.HostInfo;
import com.kingsware.kdev.core.context.SpringContext;
import lombok.SneakyThrows;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Optional;

/**
 * @author chenp
 * @date 2023/2/21
 */
public class SystemUtil {

    /**
     * 获取主机信息
     * @return
     */
    @SneakyThrows
    public static HostInfo getHost() {
        HostInfo hostInfo = new HostInfo();
        Optional<Inet4Address> inet4Address = IpUtil.
                getLocalIp4Address();
        String ip = "127.0.0.1";
        if (inet4Address.isPresent()) {
            ip = inet4Address.get().getHostAddress();
        }
        // 如果有配置ip，直接从配置里读取
        String configIp = SpringContext.getProperties("network.ip", "");
        if (StringUtils.isNotEmpty(configIp)) {
            ip = configIp;
        }
        hostInfo.setHostName(ip);
        hostInfo.setPort(Integer.parseInt(SpringContext.getProperties("server.port", "8080")));
        return hostInfo;

    }
}
