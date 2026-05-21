package com.kingsware.kdev.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

    @Value("${spring.servlet.multipart.max-file-size:${server.servlet.multipart.max-file-size:2048MB}}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size:${server.servlet.multipart.max-request-size:2048MB}}")
    private String maxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个数据大小
        factory.setMaxFileSize(DataSize.parse(maxFileSize)); // KB,MB
        // 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse(maxRequestSize));
        return factory.createMultipartConfig();
    }
}
