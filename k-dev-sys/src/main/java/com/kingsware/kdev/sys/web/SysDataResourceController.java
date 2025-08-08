package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDataResourceArgv;
import com.kingsware.kdev.sys.argv.SysDataResourceQueryArgv;
import com.kingsware.kdev.sys.ret.SysDataResourceRet;
import com.kingsware.kdev.sys.service.SysDataResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "数据访问配置", tags = {"数据访问配置"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-data-resources")
public class SysDataResourceController extends BaseController {

    @Resource
    private SysDataResourceService sysDataResourceService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ApiCode("sysinfo:dataresources:query")
    public BaseRet<PageDataRet<SysDataResourceRet>> page(SysDataResourceQueryArgv argv) {
        return BaseRet.success(sysDataResourceService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ApiCode("sysinfo:dataresources:query")
    public BaseRet<SysDataResourceRet> get(@PathVariable String id) {
        return BaseRet.success(sysDataResourceService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:dataresources:add")
    public BaseRet<?> add(@RequestBody SysDataResourceArgv argv) {
        sysDataResourceService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:dataresources:edit")
    public BaseRet<?> edit(@RequestBody SysDataResourceArgv argv) {
        sysDataResourceService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:dataresources:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysDataResourceService.delete(argv);
        return BaseRet.success();
    }
}
