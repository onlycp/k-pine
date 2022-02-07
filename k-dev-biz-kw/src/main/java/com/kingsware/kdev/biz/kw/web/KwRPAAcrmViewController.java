package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwRPAAcrmViewQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPAAcrmViewRet;
import com.kingsware.kdev.biz.kw.service.KwRPAAcrmViewService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * crm视图控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "crm视图", tags = {"crm视图"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-rpa-crm-view")
public class KwRPAAcrmViewController extends BaseController {

    @Resource
    private KwRPAAcrmViewService kwRPAAcrmViewService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwRPAAcrmViewRet>> page(KwRPAAcrmViewQueryArgv argv) {
        return BaseRet.success(kwRPAAcrmViewService.query(argv));
    }

    /**
     *  通过视图更新银行客户
     */
    @ApiOperation(value = "通过视图更新银行客户 " ,notes = "通过视图更新银行客户")
    @GetMapping("/update-manager-by-view")
    public BaseRet<?> updateManagerByView() {
        kwRPAAcrmViewService.updateBankAccountManager();
        return BaseRet.success();
    }

}
