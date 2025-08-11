package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.mode.AppModeProperties;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: 系统启动配置
 * @date 2022/4/5 15:03
 */
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-props")
public class SysPropertiesController {

    @Resource
    private AppModeProperties appModeProperties;

    /**
     *  是否开发者模式
     * @return
     */
    @GetMapping("dev")
    public BaseRet<Void> isDevMode() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("dev", String.valueOf(appModeProperties.getDev()));
        return BaseRet.success(resultMap);
    }
}
