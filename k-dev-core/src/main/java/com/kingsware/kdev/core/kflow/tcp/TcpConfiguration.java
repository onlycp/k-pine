package com.kingsware.kdev.core.kflow.tcp;

import com.kingsware.kdev.core.util.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TcpConfiguration {

    @Value("${app.kfaas.tcp.servers:}")
    private String servers;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(servers)) {
            return;
        }
        String[] arr = servers.trim().split(";");
        for (String it: arr) {
            String[] host = it.split(":");
            String ip = host[0];
            int port = Integer.parseInt(host[1]);
            TcpClientContext.getInstance().addClient(ip, port);
        }
    }
}
