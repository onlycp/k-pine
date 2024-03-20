package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.orm.kdb.KdbArgv;
import com.kingsware.kdev.core.orm.kdb.KdbRet;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.core.util.SystemUtil;
import com.kingsware.kdev.sys.argv.ExecuteFaasArgv;
import com.kingsware.kdev.sys.enums.HealthEnum;
import com.kingsware.kdev.sys.ret.ApiRequestRet;
import com.kingsware.kdev.sys.ret.AppInfoRet;
import com.kingsware.kdev.sys.ret.ComponentHealthRet;
import com.kingsware.kdev.sys.ret.HealthRet;
import com.kingsware.kdev.sys.service.KubboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
            apiRequestRet.setApiUrl("/api/"+ apiInfo.getApiUrl());
            apiRequestRet.setApiMethod(apiInfo.getApiMethod());
            apiRequestRet.setAppId(apiInfo.getAppId());
            apiRequestRet.setWhenModified(apiInfo.getWhenModified());
            return apiRequestRet;

        }).collect(Collectors.toList());
    }


}
