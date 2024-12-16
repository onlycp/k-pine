package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.argv.ExecuteFaasArgv;
import com.kingsware.kdev.sys.argv.LogTailArgv;
import com.kingsware.kdev.sys.log.LogSourceManager;
import com.kingsware.kdev.sys.ret.ApiRequestRet;
import com.kingsware.kdev.sys.ret.AppInfoRet;
import com.kingsware.kdev.sys.ret.HealthRet;
import com.kingsware.kdev.sys.service.KubboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.*;


/**
 * @author chenp
 * @date 2024/3/13
 */
@Api(value = "kubbo服务", tags = {"kubbo服务"})
@Controller
@Slf4j
@RequestMapping("/"+ Version.V1 + "/kubbo")
public class KubboController extends BaseController {

    @Resource
    private KubboService kubboService;


    /**
     * 健康检查
     * @return 详细信息
     */
    @ApiOperation(value = "健康检查 " ,notes = "健康检查")
    @ResponseBody
    @ApiIgnore
    @GetMapping("/health")
    public BaseRet<HealthRet> health() {
        return BaseRet.success(kubboService.health());
    }

    /**
     * 应用信息
     * @return 应用信息
     */
    @ApiOperation(value = "应用信息 " ,notes = "应用信息")
    @ResponseBody
    @ApiIgnore
    @GetMapping("/info")
    public BaseRet<AppInfoRet> info() {
        return BaseRet.success(kubboService.info());
    }

    /**
     * 接口列表
     * @return 接口列表
     */
    @ApiOperation(value = "接口列表 " ,notes = "接口列表")
    @ResponseBody
    @ApiIgnore
    @GetMapping("/apis/{appId}")
    public BaseRet<List<ApiRequestRet>> apis(@PathVariable String appId) {
        return BaseRet.success(kubboService.apis(appId));
    }



    /**
     * 执行faas流程
     * @return 详细信息
     */
    @ApiOperation(value = "执行Faas " ,notes = "执行Faas")
    @ResponseBody
    @ApiIgnore
    @PostMapping("/execute/faas")
    public BaseRet<?> executeFaas(@RequestBody ExecuteFaasArgv argv) {
        return BaseRet.success(kubboService.executeFaas(argv));
    }

    /**
     * 详细信息
     */
    @ApiOperation(value = "cluster " ,notes = "cluster")
    @RequestMapping("/cluster")
    @ApiIgnore
    public void cluster() {
        kubboService.cluster();
    }


    /**
     * 日志查看
     * @param argv 参数
     * @return 日志
     */
    @ApiOperation(value = "日志查看 " ,notes = "日志查看")
    @GetMapping("/logs")
    @ApiIgnore
    @SneakyThrows
    public ResponseEntity<StreamingResponseBody> tailLog(LogTailArgv argv) {
        if (StringUtils.isEmpty(argv.getApp()) || StringUtils.isEmpty(argv.getClientId())) {
            return ResponseEntity.ok().body(null);
        }
        // 管道实现线程间通信
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        LogSourceManager.getInstance().registerLogSource(argv, pipedOutputStream);
        // 返回 StreamingResponseBody，将输入流数据写入响应
        StreamingResponseBody stream = out -> {
            byte[] buffer = new byte[1024];
            int bytesRead;
            try {
                while ((bytesRead = pipedInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    out.flush();
                }
            } catch (IOException e) {
                // 当客户端断开连接或发生IO异常时，记录调试级别日志，不必频繁输出错误堆栈
                log.debug("Client disconnected or IO error occurred, stopping streaming", e);
            } catch (Exception e) {
                // 对其他异常记录错误日志进行排查
                log.error("Unexpected error during streaming", e);
            } finally {
                // 确保在结束时关闭相关资源
                try {
                    pipedInputStream.close();
                } catch (IOException ignored) {}
            }
        };

        return new ResponseEntity<>(stream, HttpStatus.OK);
    }



}
