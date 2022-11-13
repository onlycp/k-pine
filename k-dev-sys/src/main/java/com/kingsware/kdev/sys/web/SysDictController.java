package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDictArgv;
import com.kingsware.kdev.sys.argv.SysDictQueryArgv;
import com.kingsware.kdev.sys.ret.SysDictRet;
import com.kingsware.kdev.sys.service.SysDictService;
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
@Api(value = "字典类型管理", tags = {"字典类型管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-dict")
public class SysDictController extends BaseController {

    @Resource
    private SysDictService sysDictService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysDictRet>> page(SysDictQueryArgv argv) {
        return BaseRet.success(sysDictService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysDictRet> get(@PathVariable String id) {
        return BaseRet.success(sysDictService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:dict:add")
    public BaseRet<?> add(@RequestBody SysDictArgv argv) {
        sysDictService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:dict:edit")
    public BaseRet<?> edit(@RequestBody SysDictArgv argv) {
        sysDictService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:dict:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysDictService.delete(argv);
        return BaseRet.success();
    }
}
