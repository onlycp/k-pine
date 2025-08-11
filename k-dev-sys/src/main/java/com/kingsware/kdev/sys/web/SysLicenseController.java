package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysLicenseActive;
import com.kingsware.kdev.sys.ret.LicenseRet;
import com.kingsware.kdev.sys.service.LicenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "License", tags = {"License"})
@RestController
@RequestMapping("/"+ Version.V1 + "/licenses")
public class SysLicenseController extends BaseController {

    @Resource
    private LicenseService licenseService;

    /**
     *  登录信息
     * @return 提示
     */
    @ApiOperation(value = "获取当前license " ,notes = "获取当前license")
    @GetMapping()
    @ApiIgnore
    public BaseRet<LicenseRet> info(HttpServletRequest request) {
        return BaseRet.success(licenseService.getLicense());
    }


    @PostMapping("/active")
    @ApiIgnore
    public BaseRet<LicenseRet> active(@RequestBody SysLicenseActive licenseActive) {
        return BaseRet.success(licenseService.active(licenseActive), "授权成功");
    }

}
