package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwEditionArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwEditionRet;
import com.kingsware.kdev.biz.kw.service.KwEditionService;
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
 * 银行版本管理控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "银行版本管理", tags = {"银行版本管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-editions")
public class KwEditionController extends BaseController {

    @Resource
    private KwEditionService kwEditionService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwEditionRet>> page(KwEditionQueryArgv argv) {
        return BaseRet.success(kwEditionService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<KwEditionRet> get(@PathVariable String id) {
        return BaseRet.success(kwEditionService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody KwEditionArgv argv) {
        kwEditionService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody KwEditionArgv argv) {
        kwEditionService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        kwEditionService.delete(argv);
        return BaseRet.success();
    }
}
