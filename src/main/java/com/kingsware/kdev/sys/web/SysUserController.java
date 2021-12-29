package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.util.IpAddressUtils;
import com.kingsware.kdev.sys.argv.SysUserArgv;
import com.kingsware.kdev.sys.argv.SysUserLoginArgv;
import com.kingsware.kdev.sys.argv.SysUserQueryArgv;
import com.kingsware.kdev.sys.ret.SysUserRet;
import com.kingsware.kdev.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "用户管理", tags = {"用户管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-users")
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    /**
     *  登录
     * @return 提示
     */
    @ApiIgnore
    @ApiOperation(value = "登录 " ,notes = "登录")
    @PostMapping(value = "login")
    public BaseRet<?> login(HttpServletRequest request, @RequestBody SysUserLoginArgv sysUserLoginArgv) {
        sysUserLoginArgv.setIp(IpAddressUtils.getIpAddress(request));
        return BaseRet.success(sysUserService.login(sysUserLoginArgv));
    }
    /**
     *  登录信息
     * @return 提示
     */
    @ApiOperation(value = "登录信息 " ,notes = "登录信息")
    @GetMapping(value = "info")
    public BaseRet<?> info(HttpServletRequest request, String token) {
        String ip = IpAddressUtils.getIpAddress(request);
        return BaseRet.success(sysUserService.getBaseUserInfo(token, ip));
    }
    /**
     *  登出
     * @return 提示
     */
    @ApiOperation(value = "登出 " ,notes = "登出")
    @PostMapping(value = "logout")
    public BaseRet<?> logout() {
        return BaseRet.success();
    }

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysUserRet>> page(SysUserQueryArgv argv) {
        return BaseRet.success(sysUserService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysUserRet> get(@PathVariable String id) {
        return BaseRet.success(sysUserService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody SysUserArgv argv) {
        sysUserService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysUserArgv argv) {
        sysUserService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysUserService.delete(argv);
        return BaseRet.success();
    }
}
