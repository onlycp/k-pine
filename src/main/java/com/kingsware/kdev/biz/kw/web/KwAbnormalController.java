package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwAbnormalQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwReceiptQueryArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwReceiptRet;
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
     * 异常总汇首页
     * @param argv
     * @return
     */
    @ApiOperation(value = "异常总汇首页 " ,notes = "异常总汇首页")
    @GetMapping("/query")
    public BaseRet query(KwAbnormalQueryArgv argv) {
//        System.out.println(argv);
        return BaseRet.success(abnormalService.queryAbnormal(argv));
    }

    /**
     *  手动调用检查流水余额方法
     * @return
     */
    @ApiOperation(value = "检查流水余额" ,notes = "检查流水余额")
    @GetMapping("/checkBalance")
    public BaseRet checkBalance(){
        abnormalService.checkBalance();
        return BaseRet.success();
    }

    /**
     * 余额异常页面
     * @param argv
     * @return
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/queryWater")
        public BaseRet<PageDataRet<KwWaterRet>> page(KwWaterQueryArgv argv) {
        return BaseRet.success(abnormalService.queryAbnormalWater(argv));
    }


    /**
     * 查找流水的前后几条，返回有序列表
     * @param waterId
     * @return
     */
    @ApiOperation(value = "流水上下文 " ,notes = "查询流水上下文")
    @GetMapping("/getWaterContext")
    public BaseRet getWaterContext(String waterId) {
        return BaseRet.success(abnormalService.getWaterContext(waterId));
    }


    /**
     * 导出
     */
    @ApiOperation(value = "导出余额异常" ,notes = "导出余额异常")
    @GetMapping("/exportBalanceAbnormal")
    public void exportBalanceAbnormal(KwWaterQueryArgv argv) {
        argv.setPageQuery(false);
        abnormalService.exportBalanceAbnormal(argv);
    }

    /**
     * 异常流水及区间
     */
    @ApiOperation(value = "查询余额异常" ,notes = "查询余额异常")
    @GetMapping("/queryBalanceAbnormal")
    public BaseRet queryBalanceAbnormal(KwWaterQueryArgv argv) {
        argv.setPageQuery(false);
        return BaseRet.success(abnormalService.queryBalanceAbnormal(argv));
    }

    /**
     * 流水异常页面 无回单
     * @param argv
     * @return
     */
    @ApiOperation(value = "查询流水异常 " ,notes = "查询流水异常")
    @GetMapping("/queryNoReceipt")
    public BaseRet<PageDataRet<KwWaterRet>> queryNoReceipt(KwWaterQueryArgv argv) {
        return BaseRet.success(abnormalService.queryNoReceipt(argv));
    }

    /**
     * 回单异常页面 无流水
     * @return 分页
     */
    @ApiOperation(value = "查询回单异常" ,notes = "查询回单异常")
    @GetMapping("/queryNoWater")
    public BaseRet<PageDataRet<KwReceiptRet>> queryNoWater(KwReceiptQueryArgv argv) {
        return BaseRet.success(abnormalService.queryNoWater(argv));
    }

}
