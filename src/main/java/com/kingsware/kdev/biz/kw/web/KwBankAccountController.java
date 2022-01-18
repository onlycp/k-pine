package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwBankAccountArgv;
import com.kingsware.kdev.biz.kw.argv.KwBankAccountQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwBankAccountRet;
import com.kingsware.kdev.biz.kw.service.KwBankAccountService;
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
 * 银行账户信息管理控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "银行账户信息管理", tags = {"银行账户信息管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-bank-account")
public class KwBankAccountController extends BaseController {

    @Resource
    private KwBankAccountService kwBankAccountService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwBankAccountRet>> page(KwBankAccountQueryArgv argv) {
        return BaseRet.success(kwBankAccountService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<KwBankAccountRet> get(@PathVariable String id) {
        return BaseRet.success(kwBankAccountService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody KwBankAccountArgv argv) {
        kwBankAccountService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody KwBankAccountArgv argv) {
        kwBankAccountService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        kwBankAccountService.delete(argv);
        return BaseRet.success();
    }

    /**
     * 批量更新银行版本、账户、账号的关系
     * @param argv
     * @return
     */
    @ApiOperation(value = "批量更新银行版本、账户、账号的关系 " ,notes = "批量更新银行版本、账户、账号的关系")
    @PostMapping(value = "/updateAccountList")
    public BaseRet<?> addAccountListAndEditions(@RequestBody KwBankAccountArgv argv){
        System.out.println(argv.toString());
        kwBankAccountService.addAccountListAndEditions(argv);
        return BaseRet.success();
    }

}
