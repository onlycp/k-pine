package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysDemoArgv;
import com.kingsware.kdev.sys.argv.SysDemoQueryArgc;
import com.kingsware.kdev.sys.ret.SysDemoRet;
import com.kingsware.kdev.sys.service.SysDemoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-demos")
public class SysDemoController extends BaseController {

    @Resource
    private SysDemoService sysDemoService;


    /**
     *  查询
     * @return 分页
     */
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysDemoRet>> page(SysDemoQueryArgc argv) {
        return BaseRet.success(sysDemoService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @GetMapping("/{id}")
    public BaseRet<SysDemoRet> get(@PathVariable String id) {
        return BaseRet.success(sysDemoService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @PostMapping
    public BaseRet<?> add(@RequestBody SysDemoArgv argv) {
        sysDemoService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @PutMapping
    public BaseRet<?> edit(@RequestBody SysDemoArgv argv) {
        sysDemoService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysDemoService.delete(argv);
        return BaseRet.success();
    }
}
