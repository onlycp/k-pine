package com.kingsware.kdev.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/14 6:03 PM
 */
@Configuration
public class UiConfig extends WebMvcConfigurationSupport {

    @Value("${app.ui:./ui/}")
    private String ui;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断只有目录真实存在的时候才生效
        if (new File(ui).exists()) {
            registry.addResourceHandler("/**").addResourceLocations("file:" +  ui);
            super.addResourceHandlers(registry);
        }

    }
}
