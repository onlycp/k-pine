package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.CopyContextArgv;
import com.kingsware.kdev.sys.argv.DevPageArgv;
import com.kingsware.kdev.sys.argv.DevPageQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageRet;
import com.kingsware.kdev.sys.service.DevPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Dev
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
    @Dev
    public BaseRet<DevPageRet> get(@PathVariable String id) {
        return BaseRet.success(devPageService.get(id));
    }

    /**
     * 渲染页面
     * @return 渲染页面
     */
    @ApiOperation(value = "渲染页面 " ,notes = "渲染页面")
    @GetMapping("/render/{id}")
    @Dev
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
    @Dev
    public BaseRet<Void> add(@RequestBody DevPageArgv argv) {
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
    @Dev
    public BaseRet<Void> edit(@RequestBody DevPageArgv argv) {
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
    @Dev
    public BaseRet<Void> delete(@RequestBody MultiIdArgv argv) {
        devPageService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  拷贝
     * @return 提示
     */
    @Dev
    @ApiOperation(value = "深度拷贝 " ,notes = "深度拷贝")
    @PostMapping("/copyData")
    @ResponseBody
    public BaseRet<Void> copyData(@RequestBody CopyContextArgv argv, String id) {
        devPageService.copyData(id, argv);
        return BaseRet.success();
    }

    /**
     *  导出pine
     * @return 提示
     */
    @ApiOperation(value = "导出pine " ,notes = "导出pine")
    @PostMapping("/export-pine")
    @Dev
    public void exportPine(@RequestBody MultiIdArgv argv) {
        devPageService.exportPine(argv);
    }

}
