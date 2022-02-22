package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysFlowDefineArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowArgv;
import com.kingsware.kdev.sys.argv.SysKdbFlowQueryArgv;
import com.kingsware.kdev.sys.ret.SysFlowDefineRet;
import com.kingsware.kdev.sys.ret.SysKdbFlowRet;
import com.kingsware.kdev.sys.service.SysKdbFlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "KDB-流程管理", tags = {"KDB-流程管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-kdb-flows")
public class SysKdbFlowController extends BaseController {

    @Resource
    private SysKdbFlowService sysKdbFlowService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysKdbFlowRet>> page(SysKdbFlowQueryArgv argv) {
        return BaseRet.success(sysKdbFlowService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysKdbFlowRet> get(@PathVariable String id) {
        return BaseRet.success(sysKdbFlowService.get(id));
    }


    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "流程定义 " ,notes = "流程定义")
    @GetMapping("/define/{id}")
    public BaseRet<SysFlowDefineRet> getDefine(@PathVariable String id) {
        return BaseRet.success(sysKdbFlowService.getDefine(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody SysKdbFlowArgv argv) {
        sysKdbFlowService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysKdbFlowArgv argv) {
        sysKdbFlowService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  编辑流程定义
     * @return 提示
     */
    @ApiOperation(value = "编辑流程定义 " ,notes = "编辑流程定义")
    @PutMapping("/define")
    public BaseRet<?> edit(@RequestBody SysFlowDefineArgv argv) {
        sysKdbFlowService.editDefine(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysKdbFlowService.delete(argv);
        return BaseRet.success();
    }

}
