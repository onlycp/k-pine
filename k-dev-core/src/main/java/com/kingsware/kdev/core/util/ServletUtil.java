package com.kingsware.kdev.core.util;

import com.kingsware.kdev.core.context.KClientContext;
import com.kingsware.kdev.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

/**
 * servlet工具类
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/4 3:53 下午
 */
@Slf4j
public class ServletUtil {

    private ServletUtil() {
    }

    /**
     * 获取http请求
     * @return http请求
     */
    public static HttpServletRequest request() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取http请求
     * @return http响应
     */
    public static HttpServletResponse response() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 响应文件
     * @param localFile 文件
     */
    public static void responseFile(File localFile, String fileName) {

        HttpServletResponse response =  KClientContext.getContext().getResponse();
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + UriEncoder.encode(fileName));

        if (!localFile.exists()) {
            throw BusinessException.serviceThrow("文件不存在，可能被移动或删除！");
        }
        FileInputStream ins = null;
        BufferedInputStream bis = null;
        try {
            response.setContentLength((int)localFile.length());
            ins = new FileInputStream(localFile);
            bis = new BufferedInputStream(ins);
            byte[] buff = new byte[1024];
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                response.getOutputStream().write(buff, 0, i);
                response.getOutputStream().flush();
            }
            response.getOutputStream().close();

        }
        catch (FileNotFoundException e) {
            throw BusinessException.serviceThrow("文件不存在");
        }
        catch (IOException e) {
            throw BusinessException.serviceThrow("文件读取失败");
        }
        finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (bis != null) {
                    bis.close();
                }
            }
            catch (Exception e) {
                log.info("文件关闭失败");
            }
        }
    }
}
