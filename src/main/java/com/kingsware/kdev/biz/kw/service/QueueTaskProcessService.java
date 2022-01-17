package com.kingsware.kdev.biz.kw.service;

import cn.hutool.core.lang.Tuple;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kingsware.kdev.biz.kw.enums.QueueTaskStatusEnum;
import com.kingsware.kdev.biz.kw.ret.KwQueueTaskRet;
import com.kingsware.kdev.biz.kw.service.impl.KwQueueTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 队列任务处理逻辑
 *
 * @author amzc
 * @version 1.0.0
 * @date 2022/1/11 18:02
 **/
@Service
@Slf4j
public class QueueTaskProcessService {

    //@Resource
    private DailyReceiptWaterTaskService dailyReceiptWaterTaskService = new DailyReceiptWaterTaskService();

    //@Resource
    private KwQueueTaskService queueTaskService = new KwQueueTaskServiceImpl();

    /**
     * 网络路径正则表达式
     */
    private Pattern pattern = Pattern.compile("\\\\\\\\((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}\\\\(.*)");

    //@Value("${file.WaterPath}")
    String DailyReceiptWaterSinglePath;

    public QueueTaskProcessService(){
        ResourceBundle res = ResourceBundle.getBundle("application");
        this.DailyReceiptWaterSinglePath = res.getString("file.WaterPath");
    }

    /**
     * 扫描单一目录的回单流水
     * @param task      任务信息
     */
    //@ExecuteTime(name = "扫描单个文件夹流水回单")
    public void singleScanTask(KwQueueTaskRet task){
        try
        {
            log.info("扫描单一回单流水");
            String remotePath = task.getData().replaceAll("/", "\\\\");
            Matcher matcher = pattern.matcher(remotePath);
            if (!matcher.find()){
                throw new Exception("路径不匹配：" + task.getData());
            }
            String path = DailyReceiptWaterSinglePath + matcher.group(8);
            log.info("处理任务，本地路径：" + path);
            File folder = new File(path);
            if (!folder.exists() || !folder.isDirectory()){
                throw new Exception("路径不存在或者不是文件夹：" + path);
            }
            Tuple info = dailyReceiptWaterTaskService.convertExcel2ReceiptWater(new File(path));

            // TODO: 2022/1/12 判断是否发送流水到mbs

            log.info("处理任务完成：" + task.getName() + " " + task.getId() + " data：" + task.getData());
            queueTaskService.updateStatus(task.getId(), QueueTaskStatusEnum.DONE);
        }catch (Exception ex){
            log.error("处理任务出错：" + task.getName() + " " + task.getId() + " data：" + task.getData(), ex);
            queueTaskService.updateStatus(task.getId(), QueueTaskStatusEnum.ERROR);
        }
    }
}
