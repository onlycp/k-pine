package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.BaseUserInfo;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.sys.argv.SysMenuArgv;
import com.kingsware.kdev.sys.argv.SysMenuQueryArgv;
import com.kingsware.kdev.sys.ret.SysMenuRet;
import com.kingsware.kdev.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "部门管理", tags = {"部门管理"})
@RestController
@Slf4j
@RequestMapping("/"+ Version.V1 + "/sys-menus")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService SysMenuService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysMenuRet>> page(SysMenuQueryArgv argv) {
        return BaseRet.success(SysMenuService.query(argv));
    }

    /**
     *  查询树结构
     * @return 查询树结构
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/treeOptions/{excludeId}")
    public BaseRet<List<TreeDataRet<Object>>> treeOptions(@PathVariable String excludeId, String roleIds, boolean isMobile) {
        return BaseRet.success(SysMenuService.treeOptions(excludeId, roleIds, isMobile));
    }

    /**
     *  查询树结构
     * @return 查询树结构
     */
    @ApiOperation(value = "我的菜单 " ,notes = "我的菜单")
    @GetMapping("/my")
    public BaseRet<List<TreeDataRet<Object>>> my(boolean isMobile) {
        try {
            return BaseRet.success(SysMenuService.myMenus(isMobile));
        }
        catch (Exception e) {
            log.error("error", e);
        }
        return null;

    }


    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysMenuRet> get(@PathVariable String id) {
        return BaseRet.success(SysMenuService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:menu:add")
    public BaseRet<?> add(@RequestBody SysMenuArgv argv) {
        SysMenuService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:menu:edit")
    public BaseRet<?> edit(@RequestBody SysMenuArgv argv) {
        SysMenuService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:menu:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        SysMenuService.delete(argv);
        return BaseRet.success();
    }
}
