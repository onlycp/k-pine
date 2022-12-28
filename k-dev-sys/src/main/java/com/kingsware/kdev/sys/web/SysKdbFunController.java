package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysKdbFunArgv;
import com.kingsware.kdev.sys.argv.SysKdbFunQueryArgv;
import com.kingsware.kdev.sys.ret.SysKdbFunRet;
import com.kingsware.kdev.sys.service.SysKdbFunService;
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
@Api(value = "KDB-函数库", tags = {"KDB-函数库"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-kdb-funs")
public class SysKdbFunController extends BaseController {

    @Resource
    private SysKdbFunService sysKdbFunService;


    /**
     *  查询
     * @return 分页
     */
    @Dev
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysKdbFunRet>> page(SysKdbFunQueryArgv argv) {
        return BaseRet.success(sysKdbFunService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @Dev
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysKdbFunRet> get(@PathVariable String id) {
        return BaseRet.success(sysKdbFunService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody SysKdbFunArgv argv) {
        sysKdbFunService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysKdbFunArgv argv) {
        sysKdbFunService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysKdbFunService.delete(argv);
        return BaseRet.success();
    }

}
