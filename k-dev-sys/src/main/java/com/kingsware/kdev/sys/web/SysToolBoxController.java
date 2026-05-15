package com.kingsware.kdev.sys.web;

import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.auth.Dev;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.bean.Option;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.encrypt.EncryptWorker;
import com.kingsware.kdev.core.exception.ExceptionLogManager;
import com.kingsware.kdev.core.i18n.I18n;
import com.kingsware.kdev.sys.service.DevModelSqlService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 系统工具箱
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/6/16 11:17
 */
@Slf4j
@Api(value = "系统工具器", tags = {"系统工具器"})
@RestController
@RequestMapping("/"+ Version.V1 + "/sys-tool-box")
public class SysToolBoxController extends BaseController {

    @Resource
    private DevModelSqlService devModelSqlService;

    @PostMapping("/encrypt-password")
    @ApiIgnore
    public BaseRet<?> encryptPassword(String source, String slat) {
        String encrypt = EncryptWorker.getInstance().encrypt(source, slat);
        return BaseRet.success(encrypt);
    }


    @GetMapping("/to-url")
    @ApiIgnore
    public void toUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getParameter("url");
        String deUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
        log.info("Url302跳转:{}", deUrl);
        response.sendRedirect(deUrl);
    }

    @GetMapping("/exception")
    @ApiIgnore
    public BaseRet<?> getExceptionDetail(String id) throws UnsupportedEncodingException {
        String enableException = SpringContext.getProperties("app.exception.enable", "false");
        ExceptionLog exceptionLog = ExceptionLogManager.getInstance().read(id);
        if (exceptionLog == null) {
            return BaseRet.failMessage("未找到异常日志");
        }
        else {
            return BaseRet.success(exceptionLog);
        }
    }

    @GetMapping("/removeRepeatI18nKey")
    @ApiIgnore
    public BaseRet<?> removeRepeatI18nKey() {
        return BaseRet.success(I18n.removeRepeatI18nKey());
    }



    @GetMapping("/exceptionList")
    @ApiIgnore
    public BaseRet<?> findException(String term) throws UnsupportedEncodingException {
        List<Option> options = ExceptionLogManager.getInstance().getExceptionList();
        List<Option> optionList = options.stream().filter(option -> option.getLabel().contains(term)).collect(Collectors.toList());
       return BaseRet.success(optionList);
    }


    @GetMapping("/clear-page")
    @ApiIgnore
    public BaseRet<?> clearPageCache() {
        PageCacheManager.getInstance().clear();
        return BaseRet.success();
    }

    @GetMapping("/translate")
    @ApiIgnore
    public BaseRet<?> translate(String i18n) {
        return BaseRet.success(I18n.translate(i18n));
    }


    @GetMapping("/translateApp")
    @ApiIgnore
    public BaseRet<?> translateApp(String appId) {
        I18n.translateApp(appId);
        return BaseRet.success();
    }

    @GetMapping("/translateAll")
    @ApiIgnore
    public BaseRet<?> translateAll(String i18n) {
        I18n.translateAll();
        return BaseRet.success();
    }

    @PostMapping("/i18n-test")
    @ApiIgnore
    public BaseRet<?> i18nTest(@RequestBody Map<String, String> body) {
        return BaseRet.success(I18n.parseScript(KClientContext.getCurrentAppId(), body.get("text")));
    }


    @GetMapping("/executeScript/{appId}/{sourceName}")
    @Dev
    public BaseRet<?> executeScript(@PathVariable String appId, @PathVariable String sourceName) {
        devModelSqlService.execute(appId, sourceName);
        return BaseRet.success();
    }



}
