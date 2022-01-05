package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwEditionAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwEditionAccountQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwEditionAccountRet;
import com.kingsware.kdev.biz.kw.service.KwEditionAccountService;
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
 * 账号管理控制类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "账号管理", tags = {"账号管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-edition-accounts")
public class KwEditionAccountController extends BaseController {

    @Resource
    private KwEditionAccountService kwEditionAccountService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwEditionAccountRet>> page(KwEditionAccountQueryArgv argv) {
        return BaseRet.success(kwEditionAccountService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<KwEditionAccountRet> get(@PathVariable String id) {
        return BaseRet.success(kwEditionAccountService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody KwEditionAccountArgv argv) {
        kwEditionAccountService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody KwEditionAccountArgv argv) {
        kwEditionAccountService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        kwEditionAccountService.delete(argv);
        return BaseRet.success();
    }
}
