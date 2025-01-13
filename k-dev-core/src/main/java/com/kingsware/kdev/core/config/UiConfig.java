package com.kingsware.kdev.core.config;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.dict.DictManager;
import com.kingsware.kdev.core.cache.kcache.LruCache;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.kflow.KFlowContext;
import com.kingsware.kdev.core.kflow.KdbFlowExecutor;
import com.kingsware.kdev.core.kflow.bean.KdbFlowResult;
import com.kingsware.kdev.core.model.DevPage;
import com.kingsware.kdev.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/14 6:03 PM
 */
@Configuration
@Slf4j
@SuppressWarnings("all")
public class UiConfig extends WebMvcConfigurationSupport {

    @Value("${app.ui:./ui/}")
    private String ui;

    private LruCache cache = new LruCache(100);

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 判断只有目录真实存在的时候才生效
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/html/").setCacheControl(CacheControl.noStore());
        registry.addResourceHandler("/res/**").addResourceLocations("file:./res/").setCacheControl(CacheControl.noStore());
        log.info("前端目录:{}", ui);
        // unzipUi();
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
            registry.addResourceHandler("/**").addResourceLocations("file:" +  ui);
//            registry.addResourceHandler("/**").addResourceLocations("file:" +  ui).setCacheControl(CacheControl.noCache());
            //registry.addResourceHandler("/**").addResourceLocations("file:" +  ui).setCacheControl(CacheControl.noStore());;
        }
        super.addResourceHandlers(registry);

    }

    protected AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("mvc-async-");
        executor.initialize();
        return executor;
    }
    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(getAsyncExecutor());
        configurer.setDefaultTimeout(0);
        super.configureAsyncSupport(configurer);
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

        if (url.startsWith("/api/")) {
            return false;
        }
        if (url.startsWith("/res/")) {
//            log.info(url);
            return false;
        }
        // 只有是get请求才是
        if(!request.getMethod().equalsIgnoreCase("get")) {
            return false;
        }
        if (ServletUtil.isAjaxRequest(request)) {
            return false;
        }
        return true;

    }

    public String getRouterPageHtml(String path, String token, Map<String, Object> extraData) {
        String indexPageHtmlFile = ui + "index.html";
        String html = FileUtils.readFileToString(new File(indexPageHtmlFile), StandardCharsets.UTF_8);
        boolean enableSSR = SpringContext.getProperties("app.ui.enableSSR", "true").equalsIgnoreCase("true");
        if (enableSSR) {
            Document doc = Jsoup.parse(html);
            // 选择所有的 <script> 标签
            Elements scripts = doc.select("script");
            if (PageCacheManager.getInstance().contains(path)) {
                // 寻找id=ssr的script
                for (Element script : scripts) {
                    if (script.attr("id").equals("ssr")) {
                        Element ssrScript = script;
                        StringBuilder builder = new StringBuilder();
                        builder.append("\n");
                        if (StringUtils.isNotEmpty(token)) {
                            builder.append(String.format("localStorage.setItem('vue_admin_template_token', '%s');\n", token));
                        }
                        if (path.startsWith("/open/")) {
                            builder.append("window.ssrOpen=true;\n");
                        }
                        else {
                            builder.append("window.ssrOpen=false;\n");
                        }
                        builder.append("window.ssr=true;\n");
                        builder.append("window.ssrConfig = ").append(getSysConfig()).append(";\n");
                        builder.append("window.ssrPage = ").append(getPageJson(path, extraData)).append(";\n");
                        builder.append("window.ssrDict = ").append(getDict()).append(";\n");
                        ssrScript.append(builder.toString());
                    }
                }
            }

            html = doc.html();
        }
        return html;
    }

    /**
     * 向前端重定向到首页
     * @param response
     */
    public void redirectToIndex(HttpServletRequest request ,HttpServletResponse response) {
        String uri = request.getRequestURI();
        String action = request.getParameter("act");
        if (StringUtils.isNotEmpty(action) && (action.equalsIgnoreCase("whoisyourdaddy") || action.equalsIgnoreCase("dota"))) {
            DevPage page = PageCacheManager.getInstance().getByPath(uri);
            if (page != null) {
                try {
                    response.sendRedirect("/dev/designer?id=" + page.getId());
                    return;
                }
                catch (Exception e) {
                    log.error("redirectToIndex error", e);
                }

            }
        }
        String html = getRouterPageHtml(request.getServletPath(), TokenUtil.getTokenString(request), null);
        response.setCharacterEncoding("UTF-8");//编码方式
        response.setContentType("text/html");//设置为html格式
        try (PrintWriter writer = response.getWriter()) {
            writer.write(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getSysConfig() {
        KFlowContext context = KFlowContext.createBaseContext( "{}",  "{}", null);
        // 加入应用id
        Map<String, Object> flowArgvMap = new HashMap<>();
        long t1 = System.currentTimeMillis();
        KdbFlowResult result = KdbFlowExecutor.getInstance().execute("f09c30463acc44e58a8562b79312fbae", "", flowArgvMap, context, false, false);
        long t2 = System.currentTimeMillis();
        log.info("getSysConfig time:" + (t2 - t1));
        return JsonUtil.toJson(BaseRet.success(result.getData()));
//        return FileUtils.readFileToString(new File("data/config.json"), StandardCharsets.UTF_8);
    }

    private String getPageJson(String path, Map<String, Object> extraData) {
        DevPage backPage = PageCacheManager.getInstance().getByPath(path);
        DevPage page = BeanUtils.copyObject(backPage, DevPage.class);
        if (extraData != null && !extraData.isEmpty()) {
            Map<String, Object> pageJson = JsonUtil.toMap(page.getPageJson());
            if (pageJson != null) {
                if (pageJson.containsKey("data")) {
                    Map<String, Object> currentData = (Map<String, Object>) pageJson.get("data");
                    boolean hasChanged = false;
                    for (String key : extraData.keySet()) {
                        hasChanged = true;
                        currentData.put(key, extraData.get(key));
                    }
                    if (hasChanged) {
                        page.setPageJson(JsonUtil.toJson(pageJson));
                    }
                }
            }
        }
        page.setPageJson(i18nTranslatePage(page.getAppId(), page.getPageJson()));
        return JsonUtil.toJson(BaseRet.success(page));
//        return FileUtils.readFileToString(new File("data/page.json"), StandardCharsets.UTF_8);
    }

    private String getDict() {
        Map<String, Object> allDict = DictManager.getInstance().getAllDict();
        return JsonUtil.toJson(BaseRet.success(allDict));
        // return FileUtils.readFileToString(new File("data/dict.json"), StandardCharsets.UTF_8);
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


    public static String i18nTranslatePage(String appId, String pageJson) {
        long t1 = System.currentTimeMillis();
        // 解析 JSON
        DocumentContext context = JsonPath.parse(pageJson);
        // 查找所有匹配的键
        List<String> matchKeys = new ArrayList<>();
        matchKeys.add("label");
        matchKeys.add("title");
        matchKeys.add("confirmText");
        matchKeys.add("cancelText");
        matchKeys.add("onText");
        matchKeys.add("offText");
        matchKeys.add("remark");
        matchKeys.add("placeholder");
        matchKeys.add("tpl");
        matchKeys.add("map");
        matchKeys.add("option");
        matchKeys.add("btnLabel");
        matchKeys.add("tooltip");
        matchKeys.add("checkAllLabel");
        matchKeys.add("description");
        matchKeys.add("msg");
        matchKeys.add("addBtnLabel");
        matchKeys.add("editBtnLabel");
        matchKeys.add("copyBtnLabel");
        matchKeys.add("success");
//        matchKeys.add("unitOptions");
        List<String> pathKeys = new ArrayList<>();
        for (String key : matchKeys) {
            pathKeys.add("@." + key);
        }
        List<Map<String, Object>> matches = context.read("$..[?(" + StringUtils.joinToString(pathKeys, " || ") + ")]");
        for (Map<String, Object> match : matches) {
            for (String key : matchKeys) {
                if (match.containsKey(key)) {
                    if (match.get(key) instanceof String) {
                        String text = match.get(key).toString();
                        if (StringUtils.containsChinese(text)) {
                            if (key.equals("tpl") || key.equalsIgnoreCase("description") || key.equalsIgnoreCase("msg") ) {
                                org.w3c.dom.Document doc = StringUtils.parseXml(text);
                                if (doc == null) {
                                    String translatedText = I18n.parseScript(appId, text);
                                    translatedText = translatedText.replace("\\ ${","\\${");
                                    translatedText = translatedText.replace("$ ","$");
                                    if (!translatedText.equals(text)) {
                                        match.put(key, StringUtils.capitalizeFirstLetter(translatedText));
                                    }
                                }
                                else {
                                    replaceTextNodes(doc.getDocumentElement(), appId);
                                    match.put(key, StringUtils.documentToString(doc));
                                }
                            }
                            else {
                                String translatedText = I18n.parseScript(appId, text);
                                if (!translatedText.equals(text)) {
                                    match.put(key, StringUtils.capitalizeFirstLetter(translatedText));
                                }
                            }

                        }
                    }
                    else if (match.get(key) instanceof List) {
                        List<Object> list = (List<Object>) match.get(key);
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i) instanceof String) {
                                String translatedText = I18n.parseScript(appId, list.get(0).toString());
                                translatedText = translatedText.replace("\\ ${","\\${");
                                translatedText = translatedText.replace("$ ","$");
                                list.set(i, translatedText);
                            }

                        }
                        match.put(key, list);

                    }
                    else if (match.get(key) instanceof Map && "map".equalsIgnoreCase(key)) {
                        Map<String, Object> map = (Map<String, Object>) match.get(key);
                        if (map != null) {
                            Map<String,Object> replacedMap = new HashMap<>();
                            for (String k : map.keySet()) {
                                String text = map.get(k).toString();
                                org.w3c.dom.Document doc = StringUtils.parseXml(text);
                                if (doc == null) {
                                    String translatedText = I18n.parseScript(appId, text);
                                    translatedText = translatedText.replace("\\ ${","\\${");
                                    translatedText = translatedText.replace("$ ","$");
                                    if (!translatedText.equals(text)) {
                                        replacedMap.put(k, StringUtils.capitalizeFirstLetter(translatedText));
                                    }
                                }
                                else {
                                    replaceTextNodes(doc.getDocumentElement(), appId);
                                    replacedMap.put(k, StringUtils.documentToString(doc));
                                }
                            }
                            if (!replacedMap.isEmpty()) {
                                map.putAll(replacedMap);
                            }
                        }

                    }
                }
            }
            System.currentTimeMillis();
        }
        if (!matches.isEmpty()) {
            return context.jsonString();
        }
        long t2 = System.currentTimeMillis();
        log.info("解析页面JSON耗时:{}ms", t2 - t1);
        return pageJson;
    }

    // 递归方法，用于遍历和替换文本
    private static void replaceTextNodes(Node node,  String appId) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            String text = node.getTextContent();
            if (StringUtils.isNotEmpty(text) && StringUtils.containsChinese(text))  {
                String translatedText = I18n.parseScript(appId, text);
                if (!translatedText.equals(text)) {
                    node.setTextContent(StringUtils.capitalizeFirstLetter(translatedText));  // 替换文本内容
                }
            }
        }

        // 遍历所有子节点
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            replaceTextNodes(children.item(i),  appId);
        }
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
