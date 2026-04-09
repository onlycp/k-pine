package com.kingsware.kdev.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS配置类
 */
@Configuration
public class CorsConfig {

    // 从配置文件读取允许的域，默认值设置为本地开发环境
    @Value("${app.cors.allowed-origins:http://localhost:8080,http://127.0.0.1:8080}")
    private String allowedOrigins;

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 使用配置的允许域
        String[] origins = allowedOrigins.split(",");
        for (String origin : origins) {
            corsConfiguration.addAllowedOriginPattern(origin.trim()); // 使用pattern更灵活且安全
        }
        corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 允许所有HTTP方法
        corsConfiguration.setAllowCredentials(true); // 允许携带凭证
        corsConfiguration.setMaxAge(MAX_AGE);

        // 对特定接口配置跨域设置
        source.registerCorsConfiguration("/api/v1/sys-files/download/**", corsConfiguration);
        // 为所有API接口配置跨域
        source.registerCorsConfiguration("/api/**", corsConfiguration);
        // 为页面访问配置跨域
        source.registerCorsConfiguration("/k-pages/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
