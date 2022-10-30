package com.kingsware.kdev.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Data
public class FileProperties {

    private String basePath;

    private String writeList;
}
