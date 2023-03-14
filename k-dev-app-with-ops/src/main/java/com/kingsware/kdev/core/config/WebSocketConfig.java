package com.kingsware.kdev.core.config;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**

 * @author chenpeng
 * @version 1.0
 * @since JDK1.8
 */
//@Configuration
public class WebSocketConfig {
//    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
