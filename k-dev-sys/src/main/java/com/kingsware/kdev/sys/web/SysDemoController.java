package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDemoArgv;
import com.kingsware.kdev.sys.argv.SysDemoQueryArgv;
import com.kingsware.kdev.sys.ret.SysDemoRet;
import com.kingsware.kdev.sys.service.SysDemoService;
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
@Api(value = "demo示例", tags = {"demo示例"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-demos")
public class SysDemoController extends BaseController {

    @Resource
    private SysDemoService sysDemoService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @Dev
    public BaseRet<PageDataRet<SysDemoRet>> page(SysDemoQueryArgv argv) {
        return BaseRet.success(sysDemoService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @Dev
    public BaseRet<SysDemoRet> get(@PathVariable String id) {
        return BaseRet.success(sysDemoService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @Dev
    public BaseRet<?> add(@RequestBody SysDemoArgv argv) {
        sysDemoService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @Dev
    public BaseRet<?> edit(@RequestBody SysDemoArgv argv) {
        sysDemoService.edit(argv);
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
        sysDemoService.delete(argv);
        return BaseRet.success();
    }
}
