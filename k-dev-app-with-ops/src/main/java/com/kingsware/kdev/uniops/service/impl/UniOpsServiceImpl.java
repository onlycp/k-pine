package com.kingsware.kdev.uniops.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.kingsware.kdev.core.auth.AppAuthProperties;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.kflow.FlowUtils;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.DevPine;
import com.kingsware.kdev.sys.manager.UniOpsTokenStore;
import com.kingsware.kdev.sys.model.SysMenu;
import com.kingsware.kdev.sys.service.DevApplicationService;
import com.kingsware.kdev.uniops.argv.DevPublishArgv;
import com.kingsware.kdev.uniops.argv.ToPageArgv;
import com.kingsware.kdev.uniops.argv.UniOpsMenu;
import com.kingsware.kdev.uniops.config.ServerConfig;
import com.kingsware.kdev.uniops.service.UniOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenp
 * @date 2023/1/6
 */
@Slf4j
@Service
public class UniOpsServiceImpl implements UniOpsService {

    @Resource
    private AppAuthProperties appAuthProperties;
    @Resource
    private DevApplicationService devApplicationService;

    @Resource
    private ServerConfig serverConfig;

    @PostConstruct
    public void init() {
        log.info("Spring Bean initialize:{}", this.getClass().getName());
    }
    /**
     * 跳转到页面
     *
     * @param to 页面
     */
    @Override
    public void page(ToPageArgv to) {
        String pineToken = "";
        String toUrl = serverConfig.getIp();
        org.springframework.core.io.Resource resource500 = SpringContext.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "template/500.html");
        String templateContent = getResourceText(resource500);
        String exceptionStack = "";
        HttpServletRequest request = ServletUtil.request();
        if (StringUtils.isNotEmpty(to.getOpsToken()) ) {
            String token = to.getOpsToken();
            if (StringUtils.isNotEmpty(token)) {
                if (!UniOpsTokenStore.getInstance().containKey(token)) {
                    // 发起http请求
                    String uniopsServer = SpringContext.getProperties("uniops.server", "http://localhost:3456");
                    String url = uniopsServer + "/ops/userInfo";
                    Map<String, String> headers = new HashMap<>();
//                    Enumeration<String> hEnums = request.getHeaderNames();
//                    while (hEnums.hasMoreElements()) {
//                        String header = hEnums.nextElement();
//                        headers.put(header, request.getHeader(header));
//                    }
                    headers.put("token", to.getOpsToken());
                    String body = HttpUtil.postBody(url, "{}", headers);
                    BaseUserInfo userInfo = JsonUtil.toBean(body, BaseUserInfo.class);
                    userInfo.setAvatar(null);
                    String pineKey = TokenUtil.createToken(appAuthProperties.getTokenSecret(), appAuthProperties.getIss(), KClientContext.getContext().getIp(), "-1", userInfo);
                    UniOpsTokenStore.getInstance().put(token, pineKey);
                }
                pineToken = UniOpsTokenStore.getInstance().get(token);
                toUrl = to.getTo();
                // 模板数据变量
                org.springframework.core.io.Resource resource = SpringContext.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "template/sso.html");
                templateContent = getResourceText(resource);
            }
        }
        else {
            org.springframework.core.io.Resource resource = SpringContext.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "template/sso.html");
            templateContent = getResourceText(resource);
            toUrl = to.getTo();
        }

        // 模板变量
        Map<String, String> contextMap = new HashMap<>();
        contextMap.put("pineToken", pineToken);
        contextMap.put("to", toUrl);
        contextMap.put("exceptionStack", exceptionStack);
        // 渲染模板内容
        String html = TemplateUtil.render(templateContent, contextMap);
        ServletUtil.response().setCharacterEncoding("UTF-8");//编码方式
        ServletUtil.response().setContentType("text/html");//设置为html格式
        try (PrintWriter writer = ServletUtil.response().getWriter()) {

            writer.write(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发布appId
     *
     * @param argv
     */
    @Override
    @SuppressWarnings("all")
    public void publish(DevPublishArgv argv) {
        try {
//            log.info(argv.getAppData());
            Map<String, Object> data = JsonUtil.toMap(argv.getAppData());
            JsonNode jsonNode = JsonUtil.toTree("{}");
            Object obj = FlowUtils.processData(data, null, jsonNode );
            String appData = JsonUtil.toJson(obj);
            // 安装应用
            devApplicationService.importApp(appData);
            // 注册菜单
            publishMenu(appData);

        }
        catch (Exception e) {
            log.error("error:{}" ,e);
            throw BusinessException.serviceThrow("应用包数据解析异常");
        }
    }

    /**
     * 发布菜单
     *
     * @param appData
     */
    @Override
    public void publishMenu(String appData) {

        try {
            DevPine devPine = devApplicationService.appData2Pine(appData);;
            // 处理menu
            if (devPine.getMenus() == null) {
                return;
            }
            // 生成uniops菜单
            List<SysMenu> uniopsMenus = devPine.getMenus().stream().filter(it -> devPine.getInfo().getId().equals(it.getAppId())).collect(Collectors.toList());
            List<UniOpsMenu> uMenus = new ArrayList<>();
            // 找到一级菜单
            for (SysMenu menu: uniopsMenus) {
                if (StringUtils.isEmpty(menu.getParentId())) {
                    UniOpsMenu um = toUniOpsMenu(menu);
                    recurseMenu(um, uniopsMenus);
                    uMenus.add(um);
                }
            }
            // 生成临时文件
            Path jsonPath = Files.createTempFile("uniops-menu", "json");
            FileUtils.writeToFile(jsonPath.toFile(), JsonUtil.toJson(uMenus).getBytes(StandardCharsets.UTF_8));
            // 导入到UniOps
            String uniopsServer = serverConfig.getIp();
            String url = uniopsServer + "/ops/system/menu/import";
            Map<String, Object> params = new HashMap<>();
            params.put("cover", false);
            // 请求头
            Map<String,String> header = new HashMap<>();

            header.put("token", getUniOpsToken());
            log.info("菜单内容: {}", JsonUtil.toJson(uMenus) );
            String uploadRet = HttpUtil.uploadFile(url,"menu.json", "file", Files.newInputStream(jsonPath.toFile().toPath()), params, header);
            log.info("菜单注册返回信息:" + uploadRet);
            log.info("发布成功");
        } catch (Exception e) {
            log.error("error:{}",e);
            throw BusinessException.serviceThrow("应用包数据解析异常");
        }
    }

    /**
     * 卸载
     *
     * @param menus 菜单
     */
    @Override
    public void uninstall(List<SysMenu> menus) {
        if (menus.isEmpty()) {
            return;
        }
        List<String> keys = new ArrayList<>();
        for (SysMenu menu: menus) {
            String name = getOpsName(menu.getRouterPath());
            if (StringUtils.isNotEmpty(menu.getParentId())) {
                keys.add(name);
            }
            else {
                keys.add("/" + name);
            }
        }
        // 删除菜单
        String uniopsServer = serverConfig.getIp();
        String url = uniopsServer + "/ops/script/deleteMenuByKeys";
        Map<String, String> headers = new HashMap<>();
        headers.put("token", getUniOpsToken());
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("keys", StringUtils.joinToString(keys, ","));
        String body = HttpUtil.postBody(url, JsonUtil.toJson(bodyMap), headers);
        Map<String, Object> retMap = JsonUtil.toMap(body);
        int errorCode = (int)retMap.get("errorCode");
        if (errorCode != 0) {
            throw BusinessException.serviceThrow("message");
        }
        log.info("删除菜单:  Keys:{}， 响应：{}", JsonUtil.toJson(keys), body);



    }

    private String getUniOpsToken() {
        String username = SpringContext.getProperties("uniops.user", "admin");
        String password = SpringContext.getProperties("uniops.pwd", "WzcwLDIwNiwxMTUsNTksNjUsMTk1LDIzMiw5OSwxMDksOTAsMTM3LDcyLDYsMTQxLDkxLDE1OF0=");
        return UniOpsUtil.getUniOpsToken(serverConfig.getIp(), username, password);
    }




    private void recurseMenu(UniOpsMenu uniOpsMenu, List<SysMenu> pineMenus) {
        for (SysMenu menu: pineMenus) {
            if (StringUtils.isNotEmpty(menu.getParentId()) && menu.getParentId().equals(uniOpsMenu.getPineId())) {
                UniOpsMenu um = toUniOpsMenu(menu);
                recurseMenu(um, pineMenus);
                uniOpsMenu.getChildren().add(um);
            }
        }
    }

    private String getOpsName(String path) {
        String str = path.replace("/", "_");
        return StringUtils.lineToHump(str);
    }

    /**
     * 将青松菜单转为uniops菜单
     * @param menu  青松菜单
     * @return uniops菜单
     */
    private UniOpsMenu toUniOpsMenu(SysMenu menu) {
        String name = getOpsName(menu.getRouterPath());
        UniOpsMenu uniOpsMenu = new UniOpsMenu();
        uniOpsMenu.setTitle(menu.getName());
        uniOpsMenu.setCache(true);
        uniOpsMenu.setHidden(menu.getHidden());
        uniOpsMenu.setIcon(menu.getIcon());

        uniOpsMenu.setName(name);
        uniOpsMenu.setTarget("oneTab");
        uniOpsMenu.setSort(menu.getOrderNum());
        uniOpsMenu.setComponent(menu.getId());
        uniOpsMenu.setPineId(menu.getId());
        uniOpsMenu.setControl(Arrays.asList("hidden", "outernet"));
        if (StringUtils.isNotEmpty(menu.getParentId())) {
            uniOpsMenu.setLink("/pine/api/v1/uniops/page?to=" + menu.getFullPath());
            uniOpsMenu.setKey( name);
        }
        else {
            uniOpsMenu.setKey("/" + name);

        }
        return uniOpsMenu;
    }

    private String getResourceText(org.springframework.core.io.Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            return "";
        }
    }


}
