package com.kingsware.kdev.sys.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsware.kdev.core.auth.ApiIgnore;
import com.kingsware.kdev.core.base.BaseController;
import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.ExceptionLog;
import com.kingsware.kdev.core.cache.page.PageCacheManager;
import com.kingsware.kdev.core.constants.Version;
import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.exception.ExceptionLogManager;
import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.FileUtils;
import com.kingsware.kdev.core.util.JsonUtil;
import com.kingsware.kdev.core.util.StringUtils;
import com.kingsware.kdev.sys.model.DevPageHistory;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
//import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;
//import org.brotli.dec.BrotliInputStream;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
//import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
        if ("true".equalsIgnoreCase(enableException)) {
            ExceptionLog exceptionLog = ExceptionLogManager.getInstance().read(id);
            if (exceptionLog == null) {
                return BaseRet.failMessage("未找到异常日志");
            }
            else {
                return BaseRet.success(exceptionLog);
            }
        }
        else {
            return BaseRet.failMessage("未开启异常日志");
        }

    }

    @GetMapping("/clear-page")
    @ApiIgnore
    public BaseRet<?> clearPageCache() {
        PageCacheManager.getInstance().clear();
        return BaseRet.success();
    }



}
