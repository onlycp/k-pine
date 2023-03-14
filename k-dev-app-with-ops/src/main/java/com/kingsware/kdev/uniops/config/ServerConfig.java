package com.kingsware.kdev.uniops.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author chenp
 * @date 2023/3/6
 */
@Configuration
public class ServerConfig  implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    //获取内置tomcat端口号
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }

    // 获取项目IP
    public String getIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(this.serverPort == 0){ // 获取外置tomcat端口号
            this.serverPort = getTomcatPort();
        }
        return "http://"+address.getHostAddress() +":"+this.serverPort;
    }

    public int getTomcatPort() {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            QueryExp protocol = Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"));
            ObjectName name = new ObjectName("*:type=Connector,*");
            Set<ObjectName> objectNames = beanServer.queryNames(name, protocol);
            for (ObjectName objectName : objectNames) {
                String catalina = objectName.getDomain();
                if ("Catalina".equals(catalina)) {
                    return Integer.parseInt(objectName.getKeyProperty("port"));
                }
            }
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        return 8080;
    }
}
