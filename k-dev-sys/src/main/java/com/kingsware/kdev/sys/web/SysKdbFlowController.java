package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.*;
import com.kingsware.kdev.sys.ret.SysFlowDebugRet;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "KDB-流程管理", tags = {"KDB-流程管理"})
@Controller
@RequestMapping("/"+ Version.V1 + "/sys-kdb-flows")
public class SysKdbFlowController extends BaseController {

    @Resource
    private SysKdbFlowService sysKdbFlowService;


    /**
     *  查询
     * @return 分页
     */
    @Dev
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ResponseBody
    public BaseRet<PageDataRet<SysKdbFlowRet>> page(SysKdbFlowQueryArgv argv) {
        return BaseRet.success(sysKdbFlowService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ResponseBody
//    @Dev
    public BaseRet<SysKdbFlowRet> get(@PathVariable String id) {
        return BaseRet.success(sysKdbFlowService.get(id));
    }

    /**
     * 拷贝
     * @return 拷贝
     */
    @Dev
    @ApiOperation(value = "拷贝 " ,notes = "拷贝")
    @GetMapping("/copy/{id}")
    @ResponseBody
    public BaseRet<?> copy(@PathVariable String id) {
        sysKdbFlowService.copy(id);
        return BaseRet.success();
    }


    /**
     * 详细信息
     * @return 详细信息
     */
    @Dev
    @ApiOperation(value = "流程定义 " ,notes = "流程定义")
    @GetMapping("/define/{id}")
    @ResponseBody
    public BaseRet<SysFlowDefineRet> getDefine(@PathVariable String id) {
        return BaseRet.success(sysKdbFlowService.getDefine(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ResponseBody
    public BaseRet<?> add(@RequestBody SysKdbFlowArgv argv) {
        sysKdbFlowService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ResponseBody
    public BaseRet<?> edit(@RequestBody SysKdbFlowArgv argv) {
        sysKdbFlowService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  编辑流程定义
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "编辑流程定义 " ,notes = "编辑流程定义")
    @PutMapping("/define")
    @ResponseBody
    public BaseRet<?> edit(@RequestBody SysFlowDefineArgv argv) {
        sysKdbFlowService.editDefine(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ResponseBody
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysKdbFlowService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  流程调试
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "流程调试 " ,notes = "流程调试")
    @PostMapping("/debug")
    @ResponseBody
    public BaseRet<SysFlowDebugRet> debug(@RequestBody SysFlowDebugArgv argv) {
        return BaseRet.success(sysKdbFlowService.debug(argv));
    }

    /**
     *  流程调试
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "深度拷贝 " ,notes = "深度拷贝")
    @PostMapping("/copyData")
    @ResponseBody
    public BaseRet<?> copyData(@RequestBody CopyContextArgv argv, String id) {
        sysKdbFlowService.copyData(id, argv);
        return BaseRet.success();
    }

    /**
     *  导出pine
     * @return 提示
     */
    @ApiOperation(value = "导出pine " ,notes = "导出pine")
    @PostMapping("/export-pine")
    @Dev
    public void exportPine(@RequestBody MultiIdArgv argv) {
        sysKdbFlowService.exportPine(argv);
    }

}
