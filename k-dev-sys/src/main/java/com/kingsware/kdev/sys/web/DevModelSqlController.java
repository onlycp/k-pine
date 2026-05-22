package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.service.DevModelSqlService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.annotation.Resource;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2024/9/4 16:54
 */
@Api(value = "模型脚本管理", tags = {"模型脚本管理"})
@Controller
@RequestMapping("/"+ Version.V1 + "/dev-model-sql")
public class DevModelSqlController  extends BaseController {

    @Resource
    private DevModelSqlService devModelSqlService;

    @GetMapping("/executeBySource/{appId}/{sourceName}")
    @ResponseBody
    @Dev
    public BaseRet<?> executeBySource(@PathVariable String appId, @PathVariable String sourceName) {
        devModelSqlService.execute(appId, sourceName);
        return BaseRet.success();
    }

    @GetMapping("/executeByApp/{appId}")
    @ResponseBody
    @Dev
    public BaseRet<?> executeByApp(@PathVariable String appId, @PathVariable String sourceName) {
        devModelSqlService.execute(appId);
        return BaseRet.success();
    }

    @GetMapping("/executeById/{id}")
    @ResponseBody
    @Dev
    public BaseRet<?> executeById(@PathVariable String id) {
        devModelSqlService.executeById(id);
        return BaseRet.success();
    }

}
