package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.DevApplicationArgv;
import com.kingsware.kdev.sys.argv.DevApplicationQueryArgv;
import com.kingsware.kdev.sys.ret.DevApplicationRet;
import com.kingsware.kdev.sys.service.DevApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "应用管理", tags = {"应用管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/dev-app")
public class DevApplicationController extends BaseController {

    @Resource
    private DevApplicationService devApplicationService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<DevApplicationRet>> page(DevApplicationQueryArgv argv) {
        return BaseRet.success(devApplicationService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<DevApplicationRet> get(@PathVariable String id) {
        return BaseRet.success(devApplicationService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody DevApplicationArgv argv) {
        devApplicationService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody DevApplicationArgv argv) {
        devApplicationService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        devApplicationService.delete(argv);
        return BaseRet.success();
    }

}
