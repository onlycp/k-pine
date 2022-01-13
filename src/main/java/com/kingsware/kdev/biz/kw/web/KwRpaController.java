package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwQueueTaskArgv;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description:
 * @author: amzc
 * @date: 2022-01-11 15:20
 **/
@Api(value = "rpa接口", tags = "rpa接口")
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-rpa")
public class KwRpaController {
    @Resource
    KwQueueTaskService taskService;

    /**
     * 添加新的队列任务
     */
    @ApiOperation(value = "新增流水扫描任务", notes = "新增流水扫描任务")
    @PostMapping(path = "/task")
    public BaseRet<?> addNewTask(@RequestBody KwQueueTaskArgv kwQueueTaskArgv){
        System.out.println(kwQueueTaskArgv);
        return BaseRet.success(taskService.addNew(kwQueueTaskArgv));
    }
}
