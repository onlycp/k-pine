package com.kingsware.kdev.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnClass(name = {
        "org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory",
        "org.apache.catalina.connector.Connector"
})
public class TomcatPathSecurityConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatPathSecurityCustomizer() {
        return factory -> {
            String allowEncodedSlash = System.getProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH");
            if ("true".equalsIgnoreCase(allowEncodedSlash)) {
                log.warn("Unsafe system property org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true detected. Forcing it to false.");
                System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "false");
            }
            factory.addConnectorCustomizers(connector -> {
                // Ensure encoded slash and backslash are rejected at connector level.
                connector.setProperty("encodedSolidusHandling", "reject");
                connector.setProperty("encodedReverseSolidusHandling", "reject");
            });
        };
    }
}
