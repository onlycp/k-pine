package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysRoleArgv;
import com.kingsware.kdev.sys.argv.SysRoleMenuArgv;
import com.kingsware.kdev.sys.argv.SysRoleQueryArgv;
import com.kingsware.kdev.sys.ret.SysRoleRet;
import com.kingsware.kdev.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "角色管理", tags = {"角色管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-roles")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService SysRoleService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ApiCode("sysinfo:role:query")
    public BaseRet<PageDataRet<SysRoleRet>> page(SysRoleQueryArgv argv) {
        return BaseRet.success(SysRoleService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ApiCode("sysinfo:role:query")
    public BaseRet<SysRoleRet> get(@PathVariable String id) {
        return BaseRet.success(SysRoleService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:role:add")
    public BaseRet<Void> add(@RequestBody SysRoleArgv argv) {
        SysRoleService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:role:edit")
    public BaseRet<Void> edit(@RequestBody SysRoleArgv argv) {
        SysRoleService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:role:remove")
    public BaseRet<Void> delete(@RequestBody MultiIdArgv argv) {
        SysRoleService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  修改角色权限
     * @return 提示
     */
    @ApiOperation(value = "修改角色权限 " ,notes = "修改角色权限")
    @PostMapping("update-permission")
    @ApiCode("sysinfo:role:permission")
    public BaseRet<Void> updatePermission(@RequestBody SysRoleMenuArgv argv) {
        SysRoleService.updatePermission(argv);
        return BaseRet.success();
    }
}
