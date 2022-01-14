package com.kingsware.kdev.biz.kw.web;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.kingsware.kdev.biz.kw.argv.KwQueueTaskArgv;
import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.model.KwWater;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.service.KwQueueTaskService;
import com.kingsware.kdev.biz.kw.service.KwWaterService;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.excel.ExcelWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

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
    @Resource
    KwQueueTaskService kwQueueTaskService;
    @Resource
    KwWaterService kwWaterService;

    /**
     * 添加新的队列任务
     */
    @ApiOperation(value = "新增流水扫描任务", notes = "新增流水扫描任务")
    @PostMapping(path = "/task")
    public BaseRet<?> addNewTask(@RequestBody KwQueueTaskArgv kwQueueTaskArgv){
        System.out.println(kwQueueTaskArgv);
        return BaseRet.success(taskService.addNew(kwQueueTaskArgv));
    }

    @PostMapping(path = "/test")
    public BaseRet<?> addNewTask(){
        //kwWaterService.excel2WaterDto(new File("E:\\迅雷云盘\\上传流水\\Users\\标准流水.xlsx"));
//        List<Map<String,Object>> readAllMap = null;
//        try(ExcelReader reader = ExcelUtil.getReader(new File("E:\\标准流水.xlsx"))){
//            readAllMap = reader.readAll();
//        }
        //System.out.println(readAllMap.toString());
        List<List<String>> read = null;
        try{
            read = ExcelWorker.getInstance().getHandler().read(0, "E:\\\\1.xlsx");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(read.toString());
        return BaseRet.success();
    }

}
