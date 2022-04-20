package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysConfigArgv;
import com.kingsware.kdev.sys.argv.SysConfigQueryArgv;
import com.kingsware.kdev.sys.ret.SysConfigRet;
import com.kingsware.kdev.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统配置控制器
 *
 * @author crb
 * @version 1.0.0
 * @date 2021/1/14 11:01 上午
 */
@Api(value = "系统配置管理", tags = {"系统配置管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-config")
public class SysConfigController extends BaseController {

    @Resource
    private SysConfigService sysConfigService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysConfigRet>> page(SysConfigQueryArgv argv) {
        return BaseRet.success(sysConfigService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysConfigRet> get(@PathVariable String id) {
        return BaseRet.success(sysConfigService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody SysConfigArgv argv) {
        sysConfigService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysConfigArgv argv) {
        sysConfigService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysConfigService.delete(argv);
        return BaseRet.success();
    }

    /**
     * 获取当前系统配置
     * @return 获取当前系统配置
     */
    @ApiOperation(value = "获取当前系统配置 " ,notes = "获取当前系统配置")
    @GetMapping("/get-sys-config")
    @ApiIgnore
    public BaseRet<Map<String, Object>> getSysConfig() {
        return BaseRet.success(sysConfigService.getSysConfig());
    }

}
