package com.kingsware.kdev.core.kflow;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * k-flow配置相关
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/2/7 10:19 上午
 */
@Component
@ConfigurationProperties(prefix = "app.k-flow")
@Data
public class KflowProperties {
    /** 是否启用 **/
    private boolean enable;
}
