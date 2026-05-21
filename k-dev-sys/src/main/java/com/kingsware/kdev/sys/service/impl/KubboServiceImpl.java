package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.model.SysInstance;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.*;
import com.kingsware.kdev.sys.argv.ExecuteFaasArgv;
import com.kingsware.kdev.sys.enums.HealthEnum;
import com.kingsware.kdev.sys.ret.ApiRequestRet;
import com.kingsware.kdev.sys.ret.AppInfoRet;
import com.kingsware.kdev.sys.ret.ComponentHealthRet;
import com.kingsware.kdev.sys.ret.HealthRet;
import com.kingsware.kdev.sys.service.KubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenp
 * @date 2024/3/13
 */
@Slf4j
@Service
public class KubboServiceImpl implements KubboService {

    /**
     * 执行Faas流程
     *
     * @param argv
     * @return
     */
    @Override
    public KdbRet<?> executeFaas(ExecuteFaasArgv argv) {
        KdbArgv kdbArgv = new KdbArgv();
        kdbArgv.setFlowID(argv.getFlowId());
        kdbArgv.setVariables(JsonUtil.toMap(argv.getVariables()));
        KdbRet<String> ret = DB.kdbApi().executeFlow(kdbArgv, false, false);
        return ret;
    }

    /**
     * 健康检查
     *
     * @return
     */
    @Override
    public HealthRet health() {
        HealthRet th = new HealthRet();
        th.setStatus(HealthEnum.UP);
        // 系统监控
        ComponentHealthRet systemComponent = new ComponentHealthRet();
        systemComponent.setStatus(HealthEnum.UP);
//        Object obj = metricsEndpoint.listNames();
//        systemComponent.getDetails().put("cpuUsage", metricsEndpoint.metric("system.cpu.usage", null).getMeasurements().get(0).getValue());
//        systemComponent.getDetails().put("cpuCount", metricsEndpoint.metric("system.cpu.count", null).getMeasurements().get(0).getValue());
//        th.getComponents().put("system", systemComponent);
//        // 应用监控
//        ComponentHealthRet processComponent = new ComponentHealthRet();
//        processComponent.setStatus(HealthEnum.UP);
//        systemComponent.getDetails().put("cpu", metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0).getValue());
//        systemComponent.getDetails().put("startTime", metricsEndpoint.metric("process.start.time", null).getMeasurements().get(0).getValue());
//        systemComponent.getDetails().put("uptime", metricsEndpoint.metric("process.start.time", null).getMeasurements().get(0).getValue());
//        systemComponent.getDetails().put("startedTime", metricsEndpoint.metric("application.started.time", null).getMeasurements().get(0).getValue());
//        th.getComponents().put("process", processComponent);
//
//        // jvm
//        ComponentHealthRet jvmComponent = new ComponentHealthRet();
//        jvmComponent.setStatus(HealthEnum.UP);
//        jvmComponent.getDetails().put("memoryMax", metricsEndpoint.metric("jvm.memory.max", null).getMeasurements().get(0).getValue());
//        jvmComponent.getDetails().put("memoryUsed", metricsEndpoint.metric("jvm.memory.used", null).getMeasurements().get(0).getValue());
//        jvmComponent.getDetails().put("bufferCount", metricsEndpoint.metric("jvm.buffer.count", null).getMeasurements().get(0).getValue());
//        jvmComponent.getDetails().put("bufferMemoryUsed", metricsEndpoint.metric("jvm.buffer.memory.used", null).getMeasurements().get(0).getValue());
//        jvmComponent.getDetails().put("bufferTotalCapacity", metricsEndpoint.metric("jvm.buffer.total.capacity", null).getMeasurements().get(0).getValue());
//        th.getComponents().put("jvm", jvmComponent);
//
//        // disk
//        ComponentHealthRet diskComponent = new ComponentHealthRet();
//        diskComponent.setStatus(HealthEnum.UP);
//        diskComponent.getDetails().put("free", metricsEndpoint.metric("disk.free", null).getMeasurements().get(0).getValue());
//        diskComponent.getDetails().put("total", metricsEndpoint.metric("disk.total", null).getMeasurements().get(0).getValue());
//        th.getComponents().put("disk", diskComponent);
        return th;
    }

    /**
     * 获取应用程序信息
     * <p>
     * 本方法无需参数，调用后将返回一个包含应用程序详细信息的对象。
     *
     * @return AppInfoRet 返回一个应用程序信息对象，该对象包含了应用程序的版本号、名称、开发者等信息。
     */
    @Override
    public AppInfoRet info() {
        AppInfoRet appInfoRet = new AppInfoRet();
        appInfoRet.setInstanceId(SystemUtil.getHostName());
        appInfoRet.setServer(SystemUtil.getHost().getHostName());
        appInfoRet.setPort(SystemUtil.getHost().getPort());
        List<String> codes = DB.findSingleAttributeList(String.class, "select short_name from dev_application where short_name is not null and short_name !=''");
        appInfoRet.setCode(StringUtils.joinToString(codes, ","));
        return appInfoRet;
    }

    /**
     * 获取接口列表
     *
     * @return
     */
    @Override
    public List<ApiRequestRet> apis(String appCode) {
        String appid = DB.findSingleAttribute(String.class, "select id from dev_application where short_name = ?", appCode);
        if (StringUtils.isEmpty(appid)) {
            return Collections.emptyList();
        }
        List<ApiInfo> apis = ApiManager.getInstance().getAllApis();
        return apis.stream().filter(it -> appid.equalsIgnoreCase(it.getAppId())).map(apiInfo -> {
            ApiRequestRet apiRequestRet = new ApiRequestRet();
            apiRequestRet.setId(apiInfo.getId());
            apiRequestRet.setApiName(apiInfo.getApiName());
            if (apiInfo.getApiUrl().startsWith("/")) {
                apiRequestRet.setApiUrl("/api"+ apiInfo.getApiUrl());
            }
            else {
                apiRequestRet.setApiUrl("/api/"+ apiInfo.getApiUrl());
            }
            apiRequestRet.setApiMethod(apiInfo.getApiMethod());
            apiRequestRet.setAppId(apiInfo.getAppId());
            apiRequestRet.setWhenModified(apiInfo.getWhenModified());
            return apiRequestRet;

        }).collect(Collectors.toList());
    }

    @Override
    public void cluster() {
        org.springframework.core.io.Resource resource = SpringContext.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + "static/clusters.html");
        String templateContent = getResourceText(resource);

        try {
            // 设置响应的字符编码和内容类型
            ServletUtil.response().setCharacterEncoding("UTF-8");
            ServletUtil.response().setContentType("text/html; charset=UTF-8");
            // 获取 PrintWriter
            PrintWriter writer = ServletUtil.response().getWriter();

            // 渲染模板内容
            Map<String, String> contextMap = new HashMap<>();
            List<SysInstance> instances = DB.findList(SysInstance.class, "select * from sys_instance order by cluster_no asc, reg_time asc");
            StringBuffer sb = new StringBuffer();
            for (SysInstance instance : instances) {
                if (instance.getClusterNo() == null) {
                    instance.setClusterNo(1);
                }
                sb.append("<tr>");
                sb.append("<td>").append(escapeHtml(instance.getClusterNo())).append("</td>");
                sb.append("<td>").append(escapeHtml(instance.getHostName())).append("</td>");
                sb.append("<td>").append(escapeHtml(instance.getPort())).append("</td>");
                sb.append("<td>").append(escapeHtml(instance.getHeartBeatTime())).append("</td>");
                sb.append("<td>").append(instance.getOnline() == 1 ? "在线": "离线").append("</td>");
                sb.append("</tr>");
            }
            SysInstance master = InstanceManager.getInstance().masterInstance();
            contextMap.put("rows", sb.toString());
            contextMap.put("status", InstanceManager.getInstance().isActiveCluster() ? "生产":"灾备");
            contextMap.put("myClusterNo", SystemUtil.getHost().getClusterNo() + "");
            contextMap.put("activeClusterNo", master.getClusterNo() + "");
            contextMap.put("masterNodeName", master.getHostName() + "-" + master.getPort());

            String html = TemplateUtil.render(templateContent, contextMap);
            writer.write(html);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }



    private String getResourceText(org.springframework.core.io.Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            return "";
        }
    }

    private String escapeHtml(Object value) {
        if (value == null) {
            return "";
        }
        return HtmlUtils.htmlEscape(value.toString(), "UTF-8");
    }


}
