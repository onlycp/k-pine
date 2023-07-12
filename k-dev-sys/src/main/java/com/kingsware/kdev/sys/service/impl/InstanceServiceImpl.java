package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.cache.api.ApiInfo;
import com.kingsware.kdev.core.cache.api.ApiManager;
import com.kingsware.kdev.core.cron.DynamicTask;
import com.kingsware.kdev.core.model.SysTask;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.cache.instance.InstanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/12 11:03
 */
@Service
@Slf4j
public class InstanceServiceImpl implements InstanceService {

    @Resource
    private DynamicTask dynamicTask;

    @Override
    public void recvMessage(String topic, String message) {
        log.info("应用内通讯: topic:{}, 消息:{}", topic, message);
        // 任务执行
        if ("task-execute".equalsIgnoreCase(topic)) {
            SysTask task = JsonUtil.toBean(message, SysTask.class);
            new Thread(() -> dynamicTask.executeTask(task)).start();
        }
        // 新增或编辑api
        else if ("api-add-update".equalsIgnoreCase(topic)) {
            ApiInfo apiInfo = JsonUtil.toBean(message, ApiInfo.class);
            ApiManager.getInstance().addOrUpdateApi(apiInfo);
        }
        // 删除api
        else if ("api-delete".equalsIgnoreCase(topic)) {
            ApiManager.getInstance().removeApi(message);
        }

    }
}
