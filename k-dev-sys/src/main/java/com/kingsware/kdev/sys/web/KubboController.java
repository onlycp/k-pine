package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.ExecuteFaasArgv;
import com.kingsware.kdev.sys.ret.ApiRequestRet;
import com.kingsware.kdev.sys.ret.AppInfoRet;
import com.kingsware.kdev.sys.ret.HealthRet;
import com.kingsware.kdev.sys.service.KubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenp
 * @date 2024/3/13
 */
@Api(value = "kubbo服务", tags = {"kubbo服务"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kubbo")
public class KubboController extends BaseController {

    @Resource
    private KubboService kubboService;


    /**
     * 健康检查
     * @return 详细信息
     */
    @ApiOperation(value = "健康检查 " ,notes = "健康检查")
    @ApiIgnore
    @GetMapping("/health")
    public BaseRet<HealthRet> health() {
        return BaseRet.success(kubboService.health());
    }

    /**
     * 应用信息
     * @return 应用信息
     */
    @ApiOperation(value = "应用信息 " ,notes = "应用信息")
    @ApiIgnore
    @GetMapping("/info")
    public BaseRet<AppInfoRet> info() {
        return BaseRet.success(kubboService.info());
    }

    /**
     * 接口列表
     * @return 接口列表
     */
    @ApiOperation(value = "接口列表 " ,notes = "接口列表")
    @ApiIgnore
    @GetMapping("/apis/{appId}")
    public BaseRet<List<ApiRequestRet>> apis(@PathVariable String appId) {
        return BaseRet.success(kubboService.apis(appId));
    }



    /**
     * 执行faas流程
     * @return 详细信息
     */
    @ApiOperation(value = "执行Faas " ,notes = "执行Faas")
    @ApiIgnore
    @PostMapping("/execute/faas")
    public BaseRet<Void> executeFaas(@RequestBody ExecuteFaasArgv argv) {
        return BaseRet.success(kubboService.executeFaas(argv));
    }



}
