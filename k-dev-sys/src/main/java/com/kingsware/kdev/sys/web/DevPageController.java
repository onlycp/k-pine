package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.DevPageArgv;
import com.kingsware.kdev.sys.argv.DevPageQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageRet;
import com.kingsware.kdev.sys.service.DevPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "应用页面管理", tags = {"应用页面管理"})
@Controller
@RequestMapping("/"+ Version.V1 + "/dev-page")
public class DevPageController extends BaseController {

    @Resource
    private DevPageService devPageService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @ResponseBody
    @GetMapping("/query")
    public BaseRet<PageDataRet<DevPageRet>> page(DevPageQueryArgv argv) {
        return BaseRet.success(devPageService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @ResponseBody
    @GetMapping("/{id}")
    public BaseRet<DevPageRet> get(@PathVariable String id) {
        return BaseRet.success(devPageService.get(id));
    }

    /**
     * 渲染页面
     * @return 渲染页面
     */
    @ApiOperation(value = "渲染页面 " ,notes = "渲染页面")
    @GetMapping("/render/{id}")
    @ApiIgnore
    public void render(@PathVariable String id) {
        devPageService.render(id);
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "根据路径查询页面 " ,notes = "根据路径查询页面")
    @ResponseBody
    @GetMapping("/getByPath")
    @ApiIgnore
    public BaseRet<DevPageRet> getByPath(String path) {
        return BaseRet.success(devPageService.getByPath(path));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @ResponseBody
    @PostMapping
    public BaseRet<?> add(@RequestBody DevPageArgv argv) {
        devPageService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @ResponseBody
    @PutMapping
    public BaseRet<?> edit(@RequestBody DevPageArgv argv) {
        devPageService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @ResponseBody
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        devPageService.delete(argv);
        return BaseRet.success();
    }

}
