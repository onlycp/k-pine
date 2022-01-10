package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwAbnormalService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
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
     * 手动调用检查流水余额方法
     */
    @ApiOperation(value = "检查流水余额" ,notes = "检查流水余额")
    @GetMapping("/checkBalance")
    public BaseRet checkBalance(){
        abnormalService.checkbalance();
        return BaseRet.success();
    }

    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwWaterRet>> page(KwWaterQueryArgv argv) {
//        System.out.println(argv);
        return BaseRet.success(abnormalService.queryAbnormalWater(argv));
    }

    @ApiOperation(value = "流水上下文 " ,notes = "查询流水上下文")
    @GetMapping("/getWaterContext")
    public BaseRet getWaterContext(String waterId) {
        return BaseRet.success(abnormalService.getWaterContext(waterId));
    }


}
