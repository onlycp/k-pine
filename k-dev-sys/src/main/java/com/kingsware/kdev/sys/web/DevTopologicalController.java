package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.DevTopologicalArgv;
import com.kingsware.kdev.sys.argv.DevTopologicalQueryArgv;
import com.kingsware.kdev.sys.ret.DevTopologicalRet;
import com.kingsware.kdev.sys.service.DevTopologicalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "拓扑图管理", tags = {"拓扑图管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/dev-topological")
public class DevTopologicalController extends BaseController {

    @Resource
    private DevTopologicalService DevTopologicalService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<DevTopologicalRet>> page(DevTopologicalQueryArgv argv) {
        return BaseRet.success(DevTopologicalService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<DevTopologicalRet> get(@PathVariable String id) {
        return BaseRet.success(DevTopologicalService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody DevTopologicalArgv argv) {
        DevTopologicalService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody DevTopologicalArgv argv) {
        DevTopologicalService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        DevTopologicalService.delete(argv);
        return BaseRet.success();
    }

}
