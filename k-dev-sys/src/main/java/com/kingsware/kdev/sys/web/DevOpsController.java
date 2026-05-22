package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.devops.DataCopyParam;
import com.kingsware.kdev.sys.devops.DataCopyTask;
import com.kingsware.kdev.sys.ret.DevPageRet;
import com.kingsware.kdev.sys.service.DevOpsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@Api(value = "DevOPS", tags = {"DevOPS"})
// @RestController
// @RequestMapping("/"+ Version.V1 + "/devops")
public class DevOpsController extends BaseController {

    @Resource
    private DevOpsService devOpsService;


    @ApiOperation(value = "步骤1 " ,notes = "步骤1")
    @ResponseBody
    @PostMapping("/step1")
    @ApiIgnore
    public BaseRet<?> step1(@RequestBody DataCopyParam copyParam) {
        devOpsService.step1(copyParam);
        return BaseRet.success();
    }

    /**
     * 启动所有任务
     * @return
     */
    @ApiOperation(value = "启动所有任务 " ,notes = "启动所有任务")
    @ResponseBody
    @PostMapping("/startAll")
    @ApiIgnore
    public BaseRet<?> startAll() {
        devOpsService.startAll();
        return BaseRet.success();
    }


    /**
     * 任务列表
     * @return
     */
    @ApiOperation(value = "同步任务 " ,notes = "同步任务")
    @ResponseBody
    @GetMapping("/tasks")
    @ApiIgnore
    public BaseRet<?> tasks() {
        return BaseRet.success(devOpsService.tasks());
    }






}
