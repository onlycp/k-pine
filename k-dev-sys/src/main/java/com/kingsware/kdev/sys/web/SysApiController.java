package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.argv.SysApiArgv;
import com.kingsware.kdev.sys.argv.SysApiQueryArgv;
import com.kingsware.kdev.sys.ret.SysApiRet;
import com.kingsware.kdev.sys.service.SysApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "接口管理", tags = {"接口管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-apis")
public class SysApiController extends BaseController {

    @Resource
    private SysApiService sysApiService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @Dev
    public BaseRet<PageDataRet<SysApiRet>> page(SysApiQueryArgv argv) {
        return BaseRet.success(sysApiService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @Dev
    public BaseRet<SysApiRet> get(@PathVariable String id) {
        return BaseRet.success(sysApiService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @Dev
    public BaseRet<?> add(@RequestBody SysApiArgv argv) {
        sysApiService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @Dev
    public BaseRet<?> edit(@RequestBody SysApiArgv argv) {
        sysApiService.edit(argv);
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
        sysApiService.delete(argv);
        return BaseRet.success();
    }

    @ApiOperation(value = "调用uniops接口 " ,notes = "调用uniops接口")
    @PostMapping(value = "/callUniOps")
    public BaseRet<?> callUniOps(@RequestBody Map<String, Object> params) {
        return sysApiService.callUniops(params);
    }

    /**
     *  拷贝
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "深度拷贝 " ,notes = "深度拷贝")
    @PostMapping("/copyData")
    public BaseRet<?> copyData(@RequestBody CopyContextArgv argv, String id) {
        sysApiService.copyData(id, argv);
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
        sysApiService.exportPine(argv);
    }

}
