package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwRPABankViewQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPABankViewRet;
import com.kingsware.kdev.biz.kw.service.KwRPABankViewService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 银行账户视图控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "银行账户视图", tags = {"银行账户视图"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-rpa-bank-view")
public class KwRPABankViewController extends BaseController {

    @Resource
    private KwRPABankViewService kwRPABankViewService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwRPABankViewRet>> page(KwRPABankViewQueryArgv argv) {
        return BaseRet.success(kwRPABankViewService.query(argv));
    }

    /**
     *  通过视图更新项目
     */
    @ApiOperation(value = "通过视图更新项目 " ,notes = "通过视图更新项目")
    @GetMapping("/update-pro-by-view")
    public BaseRet<?> updateProByView() {
        kwRPABankViewService.updateBankAccountExpand();
        return BaseRet.success();
    }

    /**
     *  通过视图更新银行账户
     */
    @ApiOperation(value = "通过视图更新银行账户 " ,notes = "通过视图更新银行账户")
    @GetMapping("/update-account-by-view")
    public BaseRet<?> updateAccountByView() {
        kwRPABankViewService.updateBankAccount();
        return BaseRet.success();
    }

    /**
     *  通过视图更新银行账户所有信息
     */
    @ApiOperation(value = "通过视图更新银行账户所有信息 " ,notes = "通过视图更新银行账户所有信息")
    @GetMapping("/update-bank-account-all-by-view")
    public BaseRet<?> updateBankAccountAllByView() {
        kwRPABankViewService.updateBankAccountAllByView();
        return BaseRet.success();
    }

}
