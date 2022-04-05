package com.kingsware.kdev.core.mode;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 应用启动模式
 * @date 2022/4/5 14:42
 */
@Component
@ConfigurationProperties(prefix = "app.mode")
@Data
public class AppModeProperties {

    /** 当前启动的是否为开发者模式 **/
    private Boolean dev = false;
}
