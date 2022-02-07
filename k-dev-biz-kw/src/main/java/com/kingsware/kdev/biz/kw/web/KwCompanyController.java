package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwCompanyArgv;
import com.kingsware.kdev.biz.kw.argv.KwCompanyQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwCompanyRet;
import com.kingsware.kdev.biz.kw.service.KwCompanyService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 单位信息管理控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "单位信息管理", tags = {"单位信息管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-company")
public class KwCompanyController extends BaseController {

    @Resource
    private KwCompanyService kwCompanyService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwCompanyRet>> page(KwCompanyQueryArgv argv) {
        return BaseRet.success(kwCompanyService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<KwCompanyRet> get(@PathVariable String id) {
        return BaseRet.success(kwCompanyService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody KwCompanyArgv argv) {
        kwCompanyService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody KwCompanyArgv argv) {
        kwCompanyService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        kwCompanyService.delete(argv);
        return BaseRet.success();
    }
}
