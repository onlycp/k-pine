package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.SysNoticeRecordQueryArgv;
import com.kingsware.kdev.sys.ret.SysNoticeRecordRet;
import com.kingsware.kdev.sys.service.SysNoticeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:36
 * @description: 消息管理控制器
 */
@Api(value = "消息记录管理", tags = {"消息记录管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-notice-record")
public class SysNoticeRecordController {

    @Resource
    private SysNoticeRecordService sysNoticeRecordService;

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    public BaseRet<PageDataRet<SysNoticeRecordRet>> page(SysNoticeRecordQueryArgv argv) {
        return BaseRet.success(sysNoticeRecordService.query(argv));
    }

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/my-unread")
    public BaseRet<PageDataRet<SysNoticeRecordRet>> myUnRead() {
        return BaseRet.success(sysNoticeRecordService.myUnRead());
    }

    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "所有标志为已读 " ,notes = "所有标志为已读")
    @GetMapping("/will-read-all")
    public BaseRet<?> willReadAll() {
        sysNoticeRecordService.willReadAll();
        return BaseRet.success();
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    public BaseRet<SysNoticeRecordRet> get(@PathVariable String id) {
        return BaseRet.success(sysNoticeRecordService.get(id));
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysNoticeRecordService.delete(argv);
        return BaseRet.success();
    }

}
