package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysTaskArgv;
import com.kingsware.kdev.sys.argv.SysTaskQueryArgv;
import com.kingsware.kdev.sys.ret.SysTaskRet;
import com.kingsware.kdev.sys.service.SysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "任务调度", tags = {"任务调度"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-tasks")
public class SysTaskController extends BaseController {

    @Resource
    private SysTaskService sysTaskService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ApiCode("sysinfo:task:query")
    public BaseRet<PageDataRet<SysTaskRet>> page(SysTaskQueryArgv argv) {
        return BaseRet.success(sysTaskService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ApiCode("sysinfo:task:query")
    public BaseRet<SysTaskRet> get(@PathVariable String id) {
        return BaseRet.success(sysTaskService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:task:add")
    public BaseRet<Void> add(@RequestBody SysTaskArgv argv) {
        sysTaskService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:task:edit")
    public BaseRet<Void> edit(@RequestBody SysTaskArgv argv) {
        sysTaskService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:task:remove")
    public BaseRet<Void> delete(@RequestBody MultiIdArgv argv) {
        sysTaskService.delete(argv);
        return BaseRet.success();
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/executeTask/{id}")
    @ApiIgnore
    public BaseRet<Void> executeTask(@PathVariable String id) {
        sysTaskService.executeTask(id);
        return BaseRet.successMessage("任务执行成功");
    }
}
