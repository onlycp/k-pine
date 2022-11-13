package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.*;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDataAccessArgv;
import com.kingsware.kdev.sys.argv.SysDataAccessQueryArgv;
import com.kingsware.kdev.sys.ret.SysDataAccessRet;
import com.kingsware.kdev.sys.service.SysDataAccessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "数据访问策略", tags = {"数据访问策略"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-data-accesses")
public class SysDataAccessController extends BaseController {

    @Resource
    private SysDataAccessService sysDataAccessService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysDataAccessRet>> page(SysDataAccessQueryArgv argv) {
        return BaseRet.success(sysDataAccessService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysDataAccessRet> get(@PathVariable String id) {
        return BaseRet.success(sysDataAccessService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:dataaccess:add")
    public BaseRet<?> add(@RequestBody SysDataAccessArgv argv) {
        sysDataAccessService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:dataaccess:edit")
    public BaseRet<?> edit(@RequestBody SysDataAccessArgv argv) {
        sysDataAccessService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:dataaccess:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysDataAccessService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  保存用户关联
     * @return 提示
     */
    @ApiOperation(value = "保存用户关联" ,notes = "保存用户关联")
    @PostMapping("/saveDataAccessUser")
    @ApiCode("sysinfo:dataaccess:user")
    public BaseRet<?> saveDataAccessUser(@RequestBody BaseRelationArgv argv) {
        sysDataAccessService.saveDataAccessUser(argv);
        return BaseRet.success();
    }

    /**
     *  查询关联用户
     * @return 关联用户
     */
    @ApiOperation(value = "查询关联用户 " ,notes = "查询关联用户")
    @GetMapping("/querySelectedUserIds/{id}")
    public BaseRet<List<String>> querySelectedUserIds(@PathVariable String id) {
        return BaseRet.success(sysDataAccessService.querySelectedUserIds(id));
    }

    /**
     *  查询关联数据
     * @return 查询关联数据
     */
    @ApiOperation(value = "查询关联数据 " ,notes = "查询关联数据")
    @GetMapping("/querySelectedDataIds/{id}/{resourceId}")
    public BaseRet<List<String>> querySelectedDataIds(@PathVariable String id, @PathVariable String resourceId) {
        return BaseRet.success(sysDataAccessService.querySelectedDataIds(resourceId, id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "保存数据关联" ,notes = "保存数据关联")
    @PostMapping("/saveDataAccessResource/{resourceId}")
    @ApiCode("sysinfo:dataaccess:data")
    public BaseRet<?> saveDataAccessResource(@RequestBody BaseRelationArgv argv,  @PathVariable String resourceId) {
        sysDataAccessService.saveDataAccessResource(resourceId, argv);
        return BaseRet.success();
    }


    /**
     *  查询关联数据
     * @return 查询关联数据
     */
    @ApiOperation(value = "获取分类数据列表 " ,notes = "获取分类数据列表")
    @GetMapping("/queryCategoryData/{resourceId}")
    public BaseRet<List<TreeDataRet<?>>> queryCategoryData(@PathVariable String resourceId) {
        return BaseRet.success(sysDataAccessService.queryCategoryData(resourceId));
    }


}
