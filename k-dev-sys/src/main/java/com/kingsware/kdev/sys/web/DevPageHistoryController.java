package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.DevPageHistoryArgv;
import com.kingsware.kdev.sys.argv.DevPageHistoryQueryArgv;
import com.kingsware.kdev.sys.ret.DevPageHistoryRet;
import com.kingsware.kdev.sys.service.DevPageHistoryService;
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
@Api(value = "应用页面历史记录管理", tags = {"应用页面历史记录管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/dev-page-history")
public class DevPageHistoryController extends BaseController {

    @Resource
    private DevPageHistoryService DevPageHistoryService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<DevPageHistoryRet>> page(DevPageHistoryQueryArgv argv) {
        return BaseRet.success(DevPageHistoryService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<DevPageHistoryRet> get(@PathVariable String id) {
        return BaseRet.success(DevPageHistoryService.get(id));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "根据路径查询页面 " ,notes = "根据路径查询页面")
    @GetMapping("/getByPath")
    public BaseRet<DevPageHistoryRet> getByPath(String path) {
        return BaseRet.success(DevPageHistoryService.getByPath(path));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    public BaseRet<?> add(@RequestBody DevPageHistoryArgv argv) {
        DevPageHistoryService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    public BaseRet<?> edit(@RequestBody DevPageHistoryArgv argv) {
        DevPageHistoryService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        DevPageHistoryService.delete(argv);
        return BaseRet.success();
    }

}
