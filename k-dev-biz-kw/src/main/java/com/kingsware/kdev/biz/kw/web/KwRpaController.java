package com.kingsware.kdev.biz.kw.web;

import com.kingsware.kdev.biz.kw.argv.KwQueueTaskArgv;
import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.excel.ExcelWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提供给rpa调用的相关接口
 *
 * @author amzc
 * @version 1.0.0
 * @date 2022/1/11 15:20
 **/
@Api(value = "rpa接口", tags = "rpa接口")
@RestController
@RequestMapping("/"+ Version.V1 + "/kw-rpa")
public class KwRpaController {
    @Resource
    KwQueueTaskService taskService;
    @Resource
    KwQueueTaskService kwQueueTaskService;
    @Resource
    KwWaterService kwWaterService;

    /**
     * 添加新的队列任务
     * @return 详细信息
     */
    @ApiOperation(value = "新增流水扫描任务", notes = "新增流水扫描任务")
    @PostMapping(path = "/task")
    public BaseRet<?> addNewTask(@RequestBody KwQueueTaskArgv kwQueueTaskArgv){
        System.out.println(kwQueueTaskArgv);
        return BaseRet.success(taskService.addNew(kwQueueTaskArgv));
    }

    /**
     * 测试接口
     * @return
     */
    @PostMapping(path = "/test")
    public BaseRet<?> addNewTask(){
        try {
            List<List<String>> read = ExcelWorker.getInstance().getHandler().read(0, "E:\\\\标准流水.xlsx");
            System.out.println(read.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //kwWaterService.findByDateAndAccount("758872354272","2022-01-10 00:00:00.0");
        return BaseRet.success();
    }

}
