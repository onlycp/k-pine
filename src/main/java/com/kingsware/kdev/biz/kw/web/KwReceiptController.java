package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwReceiptQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.model.KwReceipt;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwReceiptRet;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwReceiptService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回单管理页面接口，需要数据权限
 */
@Api(value = "回单管理", tags = {"回单管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-receipt")
public class KwReceiptController {

    @Autowired
    private KwReceiptService receiptService;
    @Autowired
    private KwWaterService waterService;

    public KwReceiptController(){
    }

    /**
     * 查询回单
     * @return 分页
     */
    @ApiOperation(value = "查询回单" ,notes = "查询回单")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwReceiptRet>> page(KwReceiptQueryArgv argv) {
        return BaseRet.success(receiptService.query(argv));
    }

    /**
     * 导出回单
     */
    @ApiOperation(value = "导出回单" ,notes = "导出回单")
    @GetMapping("/export")
    public void export(KwReceiptQueryArgv arge){
        receiptService.export(arge);
    }

    @ApiOperation(value = "回单的流水" ,notes = "回单的流水")
    @GetMapping("/getWaterById")
    public BaseRet getWaterById(KwReceiptQueryArgv argv){

        return BaseRet.success(waterService.get(argv.getWaterId()));
    }


}
