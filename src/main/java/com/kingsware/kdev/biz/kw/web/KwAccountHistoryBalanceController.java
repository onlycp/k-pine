package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.biz.kw.service.KwAccountHistoryBalanceService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "账户历史余额管理", tags = {"账户历史余额管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-balance")
public class KwAccountHistoryBalanceController extends BaseController {
    @Autowired
    private KwAccountHistoryBalanceService accountHistoryBalanceService;

    /**
     * 账户历史余额首页
     * @param argv
     * @return
     */
    @ApiOperation(value = "账户历史余额首页 " ,notes = "账户历史余额首页")
    @GetMapping("/query")
    public BaseRet query(KwWaterQueryArgv argv) {

        return BaseRet.success(accountHistoryBalanceService.query(argv));
    }

    /**
     * 导出
     */
    @ApiOperation(value = "导出账户历史余额" ,notes = "账户历史余额")
    @GetMapping("/export")
    public void export(KwWaterQueryArgv argv) {
        accountHistoryBalanceService.export(argv);
    }



}
