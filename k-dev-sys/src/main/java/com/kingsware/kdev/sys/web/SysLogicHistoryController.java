package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysLogicHistoryArgv;
import com.kingsware.kdev.sys.argv.SysLogicHistoryQueryArgv;
import com.kingsware.kdev.sys.ret.SysLogicHistoryRet;
import com.kingsware.kdev.sys.service.SysLogicHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "逻辑编排历史记录管理", tags = {"逻辑编排历史记录管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-logic-history")
public class SysLogicHistoryController extends BaseController {

    @Resource
    private SysLogicHistoryService sysLogicHistoryService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysLogicHistoryRet>> page(SysLogicHistoryQueryArgv argv) {
        return BaseRet.success(sysLogicHistoryService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysLogicHistoryRet> get(@PathVariable String id) {
        return BaseRet.success(sysLogicHistoryService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiIgnore
    public BaseRet<?> add(@RequestBody SysLogicHistoryArgv argv) {
        sysLogicHistoryService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysLogicHistoryArgv argv) {
        sysLogicHistoryService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysLogicHistoryService.delete(argv);
        return BaseRet.success();
    }


    /**
     *  回滚
     * @return 提示
     */
    @ApiOperation(value = "回滚 " ,notes = "回滚")
    @PostMapping("/rollback")
    public BaseRet<?> rollback(@RequestBody SysLogicHistoryArgv argv) {
        sysLogicHistoryService.rollback(argv);
        return BaseRet.success();
    }

}
