package com.kingsware.kdev.core.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import com.kingsware.kdev.core.context.SpringContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 黑名单配置
 *
 * @author system
 * @version 1.0.0
 * @date 2024/01/01
 */

@Component
@Data
@Slf4j
public class BlacklistConfig implements CommandLineRunner {

    @Value("${app.blacklist.path:blacklist.yml}")
    private String blacklistPath;

    private List<String> apis = new ArrayList<>();
    private List<String> flows = new ArrayList<>();
    private List<String> pages = new ArrayList<>();


    /**
     * 加载黑名单配置
     * 优先级：当前运行目录 > classpath
     */
    private void loadBlacklistConfig() {
        try {
            // 首先尝试从当前运行目录加载
            FileSystemResource fileResource = new FileSystemResource(blacklistPath);
            if (fileResource.exists()) {
                log.info("从当前运行目录加载黑名单配置: {}", blacklistPath);
                loadFromResource(fileResource);
                return;
            }

            // 如果当前运行目录不存在，尝试从classpath加载
            ClassPathResource classPathResource = new ClassPathResource("blacklist.yml");
            if (classPathResource.exists()) {
                log.info("从classpath加载黑名单配置: classpath:blacklist.yml");
                loadFromResource(classPathResource);
                return;
            }

            log.warn("未找到黑名单配置文件，使用默认空配置");

        } catch (Exception e) {
            log.error("加载黑名单配置失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 从资源文件加载配置
     */
    private void loadFromResource(Resource resource) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = resource.getInputStream()) {
            Map<String, Object> config = yaml.load(inputStream);

            if (config != null && config.containsKey("blacklist")) {
                Map<String, Object> blacklist = (Map<String, Object>) config.get("blacklist");

                // 解析API列表
                if (blacklist.containsKey("apis")) {
                    List<Object> apiList = (List<Object>) blacklist.get("apis");
                    apis.clear();
                    for (Object item : apiList) {
                        String api = decryptValue(item.toString());
                        if (api != null && !api.isEmpty()) {
                            apis.add(api);
                        }
                    }
                    log.info("加载API黑名单: {} 条", apis.size());
                }

                // 解析流程列表
                if (blacklist.containsKey("flows")) {
                    List<Object> flowList = (List<Object>) blacklist.get("flows");
                    flows.clear();
                    for (Object item : flowList) {
                        String flow = decryptValue(item.toString());
                        if (flow != null && !flow.isEmpty()) {
                            flows.add(flow);
                        }
                    }
                    log.info("加载流程黑名单: {} 条", flows.size());
                }

                // 解析页面列表
                if (blacklist.containsKey("pages")) {
                    List<Object> pageList = (List<Object>) blacklist.get("pages");
                    pages.clear();
                    for (Object item : pageList) {
                        String page = decryptValue(item.toString());
                        if (page != null && !page.isEmpty()) {
                            pages.add(page);
                        }
                    }
                    log.info("加载页面黑名单: {} 条", pages.size());
                }
            }
        }
    }

    /**
     * 解密值
     * @param value 加密的值
     * @return 解密后的值
     */
    private String decryptValue(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        // 检查是否是ENC()格式
        if (value.startsWith("ENC(") && value.endsWith(")")) {
            String encryptedValue = value.substring(4, value.length() - 1);
            try {
                // 使用jasypt解密
                org.jasypt.encryption.StringEncryptor encryptor =
                    SpringContext.getBean(org.jasypt.encryption.StringEncryptor.class);
                if (encryptor != null) {
                    return encryptor.decrypt(encryptedValue);
                }
            } catch (Exception e) {
                log.warn("解密失败: {}", e.getMessage());
            }
        }

        return value;
    }

    /**
     * 重新加载配置
     */
    public void reload() {
        loadBlacklistConfig();
    }

    @Override
    public void run(String... args) throws Exception {
        loadBlacklistConfig();
    }
}
