package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwRPAUserViewQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwRPAUserViewRet;
import com.kingsware.kdev.biz.kw.service.KwRPAUserViewService;
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
 * 银行用户视图控制器
 *
 * @author AndyZheng
 * @version 1.0.0
 * @date 2021/1/5 11:23 上午
 */
@Api(value = "银行用户视图", tags = {"银行用户视图"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-rpa-user-view")
public class KwRPAUserViewController extends BaseController {

    @Resource
    private KwRPAUserViewService kwRPAUserViewService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwRPAUserViewRet>> page(KwRPAUserViewQueryArgv argv) {
        return BaseRet.success(kwRPAUserViewService.query(argv));
    }

    /**
     *  通过视图更新用户
     */
    @ApiOperation(value = "通过视图更新用户 " ,notes = "通过视图更新用户")
    @GetMapping("/update-by-view")
    public BaseRet<?> updateByView() {
        kwRPAUserViewService.updateUsers();
        return BaseRet.success();
    }

}
