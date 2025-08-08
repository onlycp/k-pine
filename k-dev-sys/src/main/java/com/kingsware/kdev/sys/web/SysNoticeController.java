package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiCode;
import com.kingsware.kdev.core.auth.TokenUtil;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.MultiIdArgv;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.sys.argv.SysNoticeArgv;
import com.kingsware.kdev.sys.argv.SysNoticeQueryArgv;
import com.kingsware.kdev.sys.argv.SysNoticeRelationArgv;
import com.kingsware.kdev.sys.ret.SysNoticeRet;
import com.kingsware.kdev.sys.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: crb
 * @version: 1.0.0
 * @date: 2022/01/20/11:36
 * @description: 消息管理控制器
 */
@Api(value = "消息管理", tags = {"消息管理"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-notice")
public class SysNoticeController {

    @Resource
    private SysNoticeService sysNoticeService;


    /**
     *  查询
     * @return 分页
     */
    @ApiOperation(value = "查询 " ,notes = "查询")
    @GetMapping("/query")
    @ApiCode("sysinfo:notice:query")
    public BaseRet<PageDataRet<SysNoticeRet>> page(SysNoticeQueryArgv argv) {
        return BaseRet.success(sysNoticeService.query(argv));
    }

    /**
     * 详细信息
     * @return 详细信息
     */
    @ApiOperation(value = "详情 " ,notes = "详情")
    @GetMapping("/{id}")
    @ApiCode("sysinfo:notice:query")
    public BaseRet<SysNoticeRet> get(@PathVariable String id) {
        return BaseRet.success(sysNoticeService.get(id));
    }

    /**
     *  新增
     * @return 提示
     */
    @ApiOperation(value = "新增 " ,notes = "新增")
    @PostMapping
    @ApiCode("sysinfo:notice:add")
    public BaseRet<?> add(@RequestBody SysNoticeArgv argv) {
        sysNoticeService.add(argv);
        return BaseRet.success();
    }


    /**
     *  编辑
     * @return 提示
     */
    @ApiOperation(value = "编辑 " ,notes = "编辑")
    @PutMapping
    @ApiCode("sysinfo:notice:edit")
    public BaseRet<?> edit(@RequestBody SysNoticeArgv argv) {
        sysNoticeService.edit(argv);
        return BaseRet.success();
    }

    /**
     *  删除
     * @return 提示
     */
    @ApiOperation(value = "删除 " ,notes = "删除")
    @PostMapping(value = "/delete")
    @ApiCode("sysinfo:notice:remove")
    public BaseRet<?> delete(@RequestBody MultiIdArgv argv) {
        sysNoticeService.delete(argv);
        return BaseRet.success();
    }

    /**
     *  发送消息
     * @return 提示
     */
    @ApiOperation(value = "发送消息 " ,notes = "发送消息")
    @PostMapping(value = "/sendNotice")
    @ApiCode("sysinfo:notice:send")
    public BaseRet<?> sendNotice(HttpServletRequest request, @RequestBody SysNoticeRelationArgv argv) {
        String ip = ServletUtil.getClientIp(request);
        String token = TokenUtil.getTokenString(request);
        sysNoticeService.sendNotice(argv, token, ip);
        return BaseRet.success();
    }

}
