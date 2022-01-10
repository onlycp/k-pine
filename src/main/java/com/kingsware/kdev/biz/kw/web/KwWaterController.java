package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwEditionArgv;
import com.kingsware.kdev.biz.kw.argv.KwWaterQueryArgv;
import com.kingsware.kdev.biz.kw.ret.KwWaterRet;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "流水管理", tags = {"流水管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-water")
public class KwWaterController extends BaseController {

    @Autowired
    private KwWaterService kwWaterService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<KwWaterRet>> page(KwWaterQueryArgv argv) {
//        System.out.println(argv);
        return BaseRet.success(kwWaterService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<KwWaterRet> get(@PathVariable String id) {
//        return BaseRet.success(kwEditionService.get(id));
        return BaseRet.success(kwWaterService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody KwEditionArgv argv) {
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
//        kwEditionService.delete(argv);

        return BaseRet.success();
    }
}
