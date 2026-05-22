package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.BusinessException;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.core.util.ServletUtil;
import com.kingsware.kdev.core.util.StringUtils;
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
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;

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

    private static final String INSTANCE_RECV_LAN_ONLY_ENABLE_KEY = "instance.recv-message.lan-only.enable";
    private static final String INSTANCE_RECV_TRUST_FORWARDED_IP_KEY = "instance.recv-message.trust-forwarded-ip";

    @Resource
    private InstanceService instanceService;

    @ApiOperation(value = "接收消息 " ,notes = "接收消息")
    @PostMapping("/recvMessage")
    @ApiIgnore
    public BaseRet<?> recvMessage(@RequestBody InstanceMessage message, HttpServletRequest request) {
        validateLanAccess(request);
//        log.info("应用间通讯: topic:{}, 消息:{}", message.getTopic(), message.getMessage());
        instanceService.recvMessage(message.getTopic(), message.getMessage());
        return BaseRet.success();
    }

    private void validateLanAccess(HttpServletRequest request) {
        if (!SpringContext.getBoolean(INSTANCE_RECV_LAN_ONLY_ENABLE_KEY, true)) {
            return;
        }
        String clientIp = request.getRemoteAddr();
        boolean trustForwardedIp = SpringContext.getBoolean(INSTANCE_RECV_TRUST_FORWARDED_IP_KEY, false);
        if (trustForwardedIp) {
            clientIp = ServletUtil.getClientIp(request);
        }
        if (!isLanIp(clientIp)) {
            log.warn("Instance recvMessage rejected, ip={}, remoteAddr={}, xForwardedFor={}, xRealIp={}",
                    clientIp, request.getRemoteAddr(), request.getHeader("x-forwarded-for"), request.getHeader("X-Real-IP"));
            throw BusinessException.serviceThrow(I18n.t("InstanceController.lanOnly", "仅允许局域网访问"));
        }
    }

    private boolean isLanIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        try {
            InetAddress address = InetAddress.getByName(ip);
            if (address.isAnyLocalAddress() || address.isLoopbackAddress() || address.isSiteLocalAddress() || address.isLinkLocalAddress()) {
                return true;
            }
            byte[] bytes = address.getAddress();
            // IPv6 ULA: fc00::/7
            if (bytes != null && bytes.length == 16) {
                int b = bytes[0] & 0xFF;
                return (b & 0xFE) == 0xFC;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
}
