package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.bean.TreeDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysUnitArgv;
import com.kingsware.kdev.sys.argv.SysUnitQueryArgv;
import com.kingsware.kdev.sys.ret.SysUnitRet;
import com.kingsware.kdev.sys.service.SysUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "部门管理", tags = {"部门管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-units")
public class SysUnitController extends BaseController {

    @Resource
    private SysUnitService SysUnitService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysUnitRet>> page(SysUnitQueryArgv argv) {
        return BaseRet.success(SysUnitService.query(argv));
    }

    /**
     *  查询树结构
     * @return 查询树结构
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/treeOptions/{excludeId}")
    public BaseRet<List<TreeDataRet<Object>>> treeOptions(@PathVariable String excludeId) {
        return BaseRet.success(SysUnitService.treeOptions(excludeId));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysUnitRet> get(@PathVariable String id) {
        return BaseRet.success(SysUnitService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:unit:add")
    public BaseRet<Void> add(@RequestBody SysUnitArgv argv) {
        SysUnitService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:unit:edit")
    public BaseRet<Void> edit(@RequestBody SysUnitArgv argv) {
        SysUnitService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:unit:remove")
    public BaseRet<Void> delete(@RequestBody MultiIdArgv argv) {
        SysUnitService.delete(argv);
        return BaseRet.success();
    }
}
