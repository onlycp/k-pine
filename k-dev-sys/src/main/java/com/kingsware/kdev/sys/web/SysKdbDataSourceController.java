package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceArgv;
import com.kingsware.kdev.sys.argv.SysKdbDataSourceQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbDataSourceRet;
import com.kingsware.kdev.sys.service.SysKdbDataSourceService;
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
@Api(value = "KDB-数据源管理", tags = {"KDB-数据源管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-kdb-data-sources")
public class SysKdbDataSourceController extends BaseController {

    @Resource
    private SysKdbDataSourceService sysKdbDataSourceService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @Dev
    public BaseRet<PageDataRet<SysKdbDataSourceRet>> page(SysKdbDataSourceQueryArgv argv) {
        return BaseRet.success(sysKdbDataSourceService.query(argv));
    }

    /**
     *  通过APPID查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/queryByAppId")
    @Dev
    public BaseRet<PageDataRet<SysKdbDataSourceRet>> pageByAppId(SysKdbDataSourceQueryArgv argv) {
        return BaseRet.success(sysKdbDataSourceService.queryByAppId(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @Dev
    public BaseRet<SysKdbDataSourceRet> get(@PathVariable String id) {
        return BaseRet.success(sysKdbDataSourceService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @Dev
    public BaseRet<?> add(@RequestBody SysKdbDataSourceArgv argv) {
        sysKdbDataSourceService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @Dev
    public BaseRet<?> edit(@RequestBody SysKdbDataSourceArgv argv) {
        sysKdbDataSourceService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @Dev
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysKdbDataSourceService.delete(argv);
        return BaseRet.success();
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "刷新数据源 " ,notes = "刷新数据源")
    @GetMapping("/refresh-base-flow")
    @ApiIgnore
    public BaseRet<?> refreshBaseFlow() {
        sysKdbDataSourceService.refreshBaseFlow();
        return BaseRet.success();
    }

}
