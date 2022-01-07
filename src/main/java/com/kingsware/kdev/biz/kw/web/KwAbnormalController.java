package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "异常管理", tags = {"异常管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-abnormal")
public class KwAbnormalController extends BaseController {
    @Autowired
    private KwAbnormalService abnormalService;

    /**
     * 临时测试 service 方法的接口
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/test")
    public int test(){

        abnormalService.checkbalance();


        return 1;
    }

}
