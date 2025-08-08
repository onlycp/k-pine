package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.cache.instance.InstanceManager;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.sys.argv.InstanceMessage;
import com.kingsware.kdev.core.cache.instance.InstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/7/12 11:42
 */
@Api(value = "实例管理", tags = {"实例管理"})
@RestController
@Slf4j
@RequestMapping("/"+ Version.V1 + "/instances")
public class InstanceController {

    @Resource
    private InstanceService instanceService;

    @ApiOperation(value = "接收消息 " ,notes = "接收消息")
    @PostMapping("/recvMessage")
    @ApiIgnore
    public BaseRet<?> recvMessage(@RequestBody InstanceMessage message) {
//        log.info("应用间通讯: topic:{}, 消息:{}", message.getTopic(), message.getMessage());
        instanceService.recvMessage(message.getTopic(), message.getMessage());
        return BaseRet.success();
    }
}
