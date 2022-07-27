package com.kingsware.kdev.core.cache.controller;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.ApiDefine;
import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.util.ClassUtils;
import com.kingsware.kdev.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 控制器管理
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/4/26 4:11 PM
 */
@Component
@Slf4j
public class ControllerManager {

    /** 扫描控制器的根目录 **/
    @Value("${controller.scan-package:com.kingsware.kdev}")
    private String scanPackage ;

    /** 控制器接口列表 **/
    private final List<ApiDefine> apiDefineList = new ArrayList<>();


    /**
     * 获取接口定义
     * @param method    方法
     * @param url       url
     * @return          接口定义
     */
    public ApiDefine getApiDefine(String method, String url) {
        try {
            // 如果url为空
            List<ApiDefine> myApis = new ArrayList<>();
            for (ApiDefine api : apiDefineList) {
                if (StringUtils.isNotEmpty(api.getUrl())) {
                    myApis.add(api);
                }
            }
            // 先匹配相等的
            Optional<ApiDefine> optional = myApis.stream().filter(f -> f.getMethod().contains(method)).filter(api -> api.getUrl().equals(url)).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
            // 匹配正则
            optional = myApis.stream().filter(f -> f.getMethod().contains(method)).filter(api -> {
                // url路径
                String[] reqArr = url.split("/");
                String[] restArr = api.getUrl().split("/");
                if (reqArr.length == restArr.length) {
                    for (int i = 0; i < reqArr.length; i++) {
                        // segment部分跳过
                        if (restArr[i].startsWith("{") && restArr[i].endsWith("}")) {
                            continue;
                        }
                        if (!reqArr[i].equals(restArr[i])) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;

            }).findFirst();
            return optional.orElse(null);
        } catch (Exception e) {
            log.error("error", e);
            return null;

        }
    }

    /**
     * 扫描所有控制器
     */
    @SuppressWarnings("all")
    @PostConstruct
    public void scanController() {
        // 扫描所有的class
        List<Class<?>> controllerList = ClassUtils.getClassesByParentClass(scanPackage, BaseController.class);
        // 获取所有的接口请求
        for(Class<?> clazz: controllerList) {
            // 如果没有@Controller注释或@RestController，跳过
            if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(RestController.class)) {
                continue;
            }
            String module = clazz.getName();
            if (clazz.isAnnotationPresent(Api.class)) {
                module = clazz.getAnnotation(Api.class).value();
            }
            // 获取顶级url
            String rootUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                String[] values = clazz.getAnnotation(RequestMapping.class).value();
                if (values.length > 0) {
                    rootUrl = values[0];
                }
            }
            // 获取所有方法
            Method[] methods = clazz.getMethods();
            for (Method method: methods) {
                ApiDefine apiDefine = new ApiDefine();
                // 模块名
                apiDefine.setModule(module);
                // class 名称
                apiDefine.setClassName(clazz.getName());
                // 获取接口名称
                String apiName = method.getName();
                if (method.isAnnotationPresent(ApiOperation.class)) {
                    apiName = method.getAnnotation(ApiOperation.class).value();
                }
                apiDefine.setName(apiName);
                String actionUrl = "";
                // 请求方法
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    // 方法
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping.method().length > 0) {
                        for (RequestMethod requestMethod: requestMapping.method()) {
                            apiDefine.getMethod().add(requestMethod.name().toLowerCase());
                        }
                    }
                    else {
                        apiDefine.getMethod().add(RequestMethod.GET.name().toLowerCase());
                        apiDefine.getMethod().add(RequestMethod.POST.name().toLowerCase());
                        apiDefine.getMethod().add(RequestMethod.PUT.name().toLowerCase());
                        apiDefine.getMethod().add(RequestMethod.PATCH.name().toLowerCase());
                        apiDefine.getMethod().add(RequestMethod.DELETE.name().toLowerCase());
                    }

                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                else if (method.isAnnotationPresent(GetMapping.class)) {
                    // 方法
                    GetMapping requestMapping = method.getAnnotation(GetMapping.class);
                    apiDefine.getMethod().add(RequestMethod.GET.name().toLowerCase());
                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                else if (method.isAnnotationPresent(PostMapping.class)) {
                    // 方法
                    PostMapping requestMapping = method.getAnnotation(PostMapping.class);
                    apiDefine.getMethod().add(RequestMethod.POST.name().toLowerCase());
                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                else if (method.isAnnotationPresent(PutMapping.class)) {
                    // 方法
                    PutMapping requestMapping = method.getAnnotation(PutMapping.class);
                    apiDefine.getMethod().add(RequestMethod.PUT.name().toLowerCase());
                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                else if (method.isAnnotationPresent(PatchMapping.class)) {
                    // 方法
                    PatchMapping requestMapping = method.getAnnotation(PatchMapping.class);
                    apiDefine.getMethod().add(RequestMethod.PATCH.name().toLowerCase());
                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    // 方法
                    DeleteMapping requestMapping = method.getAnnotation(DeleteMapping.class);
                    apiDefine.getMethod().add(RequestMethod.PATCH.name().toLowerCase());
                    // url
                    if (requestMapping.value().length > 0) {
                        actionUrl = requestMapping.value()[0];
                    }
                }
                if (!actionUrl.startsWith("/")) {
                    actionUrl = "/" + actionUrl;
                }
                apiDefine.setUrl(rootUrl + actionUrl);
                // 是否忽略权限
                if (method.isAnnotationPresent(ApiIgnore.class)) {
                    apiDefine.setIgnore(true);
                }
                // 获取权限码
                if (method.isAnnotationPresent(ApiCode.class)) {
                    ApiCode apiCode = method.getAnnotation(ApiCode.class);
                    apiDefine.setApiCode(apiCode.value());
                }
                if (apiDefine.getMethod().isEmpty()) {
                    continue;
                }
                apiDefineList.add(apiDefine);
//                log.info("控制器接口定义： {}", apiDefine);
            }
        }
    }



}
