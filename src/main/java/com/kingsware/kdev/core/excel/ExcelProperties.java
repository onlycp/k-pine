package com.kingsware.kdev.core.excel;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Excel配置类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/20 1:55 下午
 */
@Component
@ConfigurationProperties(prefix = "app.excel")
@Data
public class ExcelProperties {

    /** 解析器 poi、jxl**/
    private String parser = "jxl";
    /** 临时文件夹 **/
    private String tmpPath = "tmp";

}
