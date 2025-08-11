package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysOperateLogQueryArgv;
import com.kingsware.kdev.sys.ret.SysOperateLogRet;
import com.kingsware.kdev.sys.service.SysOperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示控制器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2021/12/23 11:23 上午
 */
@Api(value = "操作日志", tags = {"操作日志"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-operate-logs")
public class SysOperateLogController extends BaseController {

    @Resource
    private SysOperateLogService sysOperateLogService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysOperateLogRet>> page(SysOperateLogQueryArgv argv) {
        return BaseRet.success(sysOperateLogService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysOperateLogRet> get(@PathVariable String id) {
        return BaseRet.success(sysOperateLogService.get(id));
    }

    /**
     * 导出
     */
    @ApiOperation(value = "导出 " ,notes = "导出")
    @GetMapping("/export")
    @ApiCode("sysinfo:operationlog:export")
    public void download(SysOperateLogQueryArgv argv) {
        sysOperateLogService.export(argv);
    }

    /**
     *  模块列表
     */
    @ApiOperation(value = "模块列表 " ,notes = "模块列表")
    @GetMapping("/module-list")
    public BaseRet<PageDataRet<SysOperateLogRet>> moduleList(SysOperateLogQueryArgv argv) {
        return BaseRet.success(sysOperateLogService.moduleList(argv));
    }

    /**
     * 动作列表
     */
    @ApiOperation(value = "动作列表", notes = "动作列表")
    @GetMapping("/action-list")
    public BaseRet<PageDataRet<SysOperateLogRet>> actionList(SysOperateLogQueryArgv argv) {
        return BaseRet.success(sysOperateLogService.actionList(argv));
    }
}
