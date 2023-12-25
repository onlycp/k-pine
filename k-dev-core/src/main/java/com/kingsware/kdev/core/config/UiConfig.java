package com.kingsware.kdev.core.config;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.MD5Utils;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        registry.addResourceHandler("/res/**").addResourceLocations("file:./res/");
        log.info("前端目录:{}", ui);
        unzipUi();
        if (new File(ui).exists()) {
            // 替换内容
            String contextPath = SpringContext.getProperties("app.ui.prefix", SpringContext.getBootProperties("server.servlet.context-path", "") );
            if (contextPath.endsWith("/")) {
                contextPath = contextPath.substring(0, contextPath.length()-1);
            }
            log.info("当前上下文：" + contextPath);
            if (StringUtils.isNotEmpty(contextPath)) {
                // 替换字体
                String text = "url(/static/fonts/";
                String replaceText = String.format("url(%s/static/fonts/",contextPath);
                replaceText(new File(ui), text, replaceText);
            }
            log.info("加载前端资源:{}", ui);
            //
            registry.addResourceHandler("/**").addResourceLocations("file:" +  ui);
        }
        super.addResourceHandlers(registry);

    }

    /**
     * 是否静态资源
     * @param url
     * @return
     */
    public boolean isStaticsResource(String url) {
        String path = ui + url;
        path = path.replace("//", "/");
        return Files.exists(Paths.get(path));
    }

    /**
     * 是否前端路由
     * @param url
     * @param request
     * @return
     */

    public boolean isFrontRouter(String url, HttpServletRequest request) {
        // 判断是否前端请求，如果是，直接返回index.html
        if (url.startsWith("/api/")) {
            return false;
        }
        if (url.startsWith("/res/")) {
            log.info(url);
            return false;
        }
        // 只有是get请求才是
        if(!request.getMethod().equalsIgnoreCase("get")) {
            return false;
        }
        if (ServletUtil.isRefererRule(request)) {
            return false;
        }
        return true;

    }

    /**
     * 向前端重定向到首页
     * @param response
     */
    public void redirectToIndex(HttpServletResponse response) {
        String indexPageHtmlFile = ui + "index.html";
        String html = FileUtils.readFileToString(new File(indexPageHtmlFile), StandardCharsets.UTF_8);
        response.setCharacterEncoding("UTF-8");//编码方式
        response.setContentType("text/html");//设置为html格式
        try (PrintWriter writer = response.getWriter()) {
            writer.write(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解压ui
     */
    private void unzipUi() {
        String uiPath = ResourceUtils.CLASSPATH_URL_PREFIX + "ui/**";
        Resource[] resources = SpringContext.getResources(uiPath);
        if (resources == null || resources.length == 0) {
            return;
        }
        log.info("应用存在ui目录，程序将自动解压ui包");
        // 判断解压后的目录是否存在此文件
        String checkFile = ui + "ui-check";
        // 如果存在ui目录，但不存在ui-check文件，此时不覆盖
        if (new File(ui).exists() && !new File(checkFile).exists()) {
            return;
        }
        // 获取历史版本时间
        try {

            String hisMd5 = "";
            if (new File(checkFile).exists()) {
                hisMd5 = Files.readAllLines(new File(checkFile).toPath()).get(0).trim();
            }
            Resource resource = findResource(resources, "index.html");
            if (resource == null) {
                log.info("ui资源不存在index.html文件，跳过替换");
                return;
            }
            String curMd5 = FileUtils.getMD5(resource.getInputStream());
            if (hisMd5.equalsIgnoreCase(curMd5)) {
                log.info("UI包的md5一致，跳过替换动作");
                return;
            }
            if (StringUtils.isEmpty(curMd5)) {
                log.info("UI包的当前的md5不存在，跳过替换动作");
                return;
            }
            // 如果不存在，就创建目录
            if (!new File(ui).exists()) {
                new File(ui).mkdirs();
            }
            log.info("开始解压ui包，资源数:" +  resources.length);
            for (Resource res: resources) {
                if (!res.getURL().toString().endsWith("/")) {
                    String url = res.getURI().toString();
                    log.info("资源名称:{}, url：{}", res.getFilename(), url);
                    String[] arr = res.getURI().toString().split("classes/ui");
                    String filePath  = ui + arr[1];
                    Path parentPath = Paths.get(new File(filePath).getParent());
                    if (!parentPath.toFile().exists()) {
                        parentPath.toFile().mkdirs();
                    }
                    // 写入文件
                    log.info("资源:{}", res.getURI().toString());
                    Files.write(new File(filePath).toPath(), StreamUtils.copyToByteArray(res.getInputStream()));
                }
            }

            // 创建mdk文件
            Files.write(new File(checkFile).toPath(), curMd5.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            log.warn("解压ui错误:{}", e);
        }

    }

    private Resource findResource(Resource[] resources, String name) {
        for (Resource resource: resources) {
            if (resource.getFilename().equals(name)) {
                return resource;
            }
        }
        return null;
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
                        String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        if (fileContent.contains(text)) {
                            log.info(file.getPath());
                            String replacedFileContent = fileContent.replaceAll(Pattern.quote(text), replaceText);
                            FileUtils.writeStringToFile(file, replacedFileContent, StandardCharsets.UTF_8);
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
