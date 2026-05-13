package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysSsoArgv;
import com.kingsware.kdev.sys.ret.SysUserLoginRet;
import com.kingsware.kdev.sys.service.SysSsoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "sso", tags = {"sso"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-sso")
public class SysSsoController extends BaseController{

    @Resource
    private SysSsoService sysSsoService;

//
//    /**
//     * sso
//     *
//     * @return 分页
//     */
//    @ApiOperation(value = "sso登录 ", notes = "sso登录")
//    @PostMapping("/login")
//    @ApiIgnore
//    public BaseRet<SysUserLoginRet> autoLogin(@RequestBody SysSsoArgv argv) {
//        return BaseRet.success(sysSsoService.doLogin(argv));
//    }



}
