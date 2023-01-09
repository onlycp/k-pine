package com.kingsware.kdev.core.config;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Pattern;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/14 6:03 PM
 */
@Configuration
@Slf4j
public class UiConfig extends WebMvcConfigurationSupport {

    @Value("${app.ui:./ui/}")
    private String ui;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断只有目录真实存在的时候才生效
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/");
        if (new File(ui).exists()) {
            // 替换内容
            String contextPath = ServletUtil.getContextPath();
            if (contextPath.endsWith("/")) {
                contextPath = contextPath.substring(0, contextPath.length()-1);
            }
            if (StringUtils.isNotEmpty(contextPath)) {
                // 替换字体
                String text = "url(/static/fonts/";
                String replaceText = String.format("url(%s/static/fonts/",contextPath);
                replaceText(new File(ui), text, replaceText);
            }
            registry.addResourceHandler("/**").addResourceLocations("file:" +  ui);

        }
        super.addResourceHandlers(registry);

    }

    /**
     * 替换文本内容
     * @param path  路径
     */
    private void replaceText(File path , String text, String replaceText) {
        File[] files = path.listFiles();
        assert files != null;
        for (File file: files) {
            if (file.isDirectory()) {
                replaceText(file, text, replaceText);
            }
            else {
                if (file.getName().endsWith(".js") || file.getName().endsWith(".css") || file.getName().endsWith(".html")) {
                    try {
                        String fileContent = org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        if (fileContent.contains(text)) {
                            log.info(file.getPath());
                            String replacedFileContent = fileContent.replaceAll(Pattern.quote(text), replaceText);
                            org.apache.commons.io.FileUtils.writeStringToFile(file, replacedFileContent, StandardCharsets.UTF_8);
                            log.info("文件内容替换:{}，{} -> {}", file.getPath(), text, replaceText);
                        }
                    }
                    catch (Exception e) {
                        log.error("error",e);
                    }

                }
            }
        }
    }
}
