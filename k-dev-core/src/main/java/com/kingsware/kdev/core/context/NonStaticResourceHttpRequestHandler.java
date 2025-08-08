package com.kingsware.kdev.core.context;

import com.kingsware.kdev.core.util.FileTypeChecker;
import com.kingsware.kdev.core.util.HttpUtil;
import com.kingsware.kdev.core.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/6/16 18:14
 */
@Component
@Slf4j
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    public final static String ATTR_FILE = "NON-STATIC-FILE";
    public final static String URL_CONNECTION = "URL-CONNECTION";
    public final static String URL_PATH = "URL_PATH";


    @Override
    protected Resource getResource(HttpServletRequest request) {
        Object attrFile = request.getAttribute(ATTR_FILE);
        Resource resource = null;
        if (attrFile instanceof File) {
            File file = (File) attrFile;
            try {
//                byte[] fileBytes = FileUtils.readFileToByteArray(file);
                resource = new InputStreamResource(new FileInputStream(file));
                request.setAttribute("fileName", file.getName());
                file.delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (attrFile instanceof InputStream) {
            long t1 = System.currentTimeMillis();
            resource = new InputStreamResource((InputStream) attrFile);
            log.info("InputStreamResource: " + (System.currentTimeMillis() - t1));
        }
        else {
            String path = (String) attrFile;
            Path filePath = Paths.get(path);
            String fileName = filePath.getFileName().toString();
            request.setAttribute("fileName", fileName);
            resource = new FileSystemResource(filePath);
        }
        return resource;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Resource resource = this.getResource(request);
        if (resource == null) {
            log.debug("Resource not found");
            response.sendError(404);
        } else if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setHeader("Allow", this.getAllowHeader());
        } else {
            this.checkRequest(request);
            if (resource.getFilename() != null && this.isUseLastModified() && (new ServletWebRequest(request, response)).checkNotModified(resource.lastModified())) {
                log.trace("Resource not modified");
            } else {
                this.prepareResponse(response);

                MediaType mediaType = this.getMediaType(request, resource);
                if (mediaType == null) {
                    String paramFileName = request.getParameter("fileName");
                    if (paramFileName == null) {
                        paramFileName = (String) request.getAttribute("fileName");
                    }
                    if (!StringUtils.isEmpty(paramFileName) && FileTypeChecker.isVideoFile(paramFileName)) {
                        mediaType = MediaType.parseMediaType("video/mp4");
                    }
                    if (!StringUtils.isEmpty(paramFileName) && FileTypeChecker.isAudioFile(paramFileName)) {
                        mediaType = MediaType.parseMediaType("audio/mp4");
                    }
                }

                this.setHeaders(response, resource, mediaType);
                ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
                if (request.getHeader("Range") == null) {
                    Assert.state(this.getResourceHttpMessageConverter() != null, "Not initialized");
                    this.getResourceHttpMessageConverter().write(resource, mediaType, outputMessage);
                } else {
                    Assert.state(this.getResourceHttpMessageConverter() != null, "Not initialized");
                    ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);

                    try {
                        List<HttpRange> httpRanges = inputMessage.getHeaders().getRange();
                        response.setStatus(206);
                        if (resource instanceof FileSystemResource || resource instanceof ByteArrayResource) {
                            this.getResourceRegionHttpMessageConverter().write(HttpRange.toResourceRegions(httpRanges, resource), mediaType, outputMessage);
                        } else if (resource instanceof InputStreamResource) {
                            URLConnection urlConnection = (URLConnection)request.getAttribute(URL_CONNECTION);

                            String localFile = "C:\\Users\\chenp\\Downloads\\1.mp4";
                            // 获取输入流长度
                            int contentLength = urlConnection.getContentLength();

                            // 解析 Range 请求头
                            HttpRange httpRange = httpRanges.isEmpty() ? HttpRange.createByteRange(0) : httpRanges.get(0);

                            long start = httpRange.getRangeStart(contentLength);
                            long end = httpRange.getRangeEnd(contentLength);
                            long rangeLength = end - start + 1;  // 获取请求的块大小
                            // 连接视频流
                            try (InputStream inputStream = urlConnection.getInputStream()) {
                                long t0 = System.currentTimeMillis();
                                // 设置响应头
                                response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
                                response.setContentType(MediaTypeFactory.getMediaType(resource)
                                        .orElse(MediaType.APPLICATION_OCTET_STREAM).toString());
                                response.setHeader("Content-Range", "bytes " + start + "-" + (start + rangeLength - 1) + "/" + contentLength);
                                response.setHeader("Accept-Ranges", "bytes");
                                response.setHeader("Content-Length", String.valueOf(rangeLength));

                                // 跳过开始位置的字节
                                long t1 = System.currentTimeMillis();
                                inputStream.skip(start);
                                long t2 = System.currentTimeMillis();
                                long t3 = System.currentTimeMillis();
                                log.info("Content-Range:" + response.getHeader("Content-Range"));
                                log.info("t2-t1:{}, t1-t0:{}" , (t2 - t1), (t1-t0));
                                // 写入响应
                                byte[] buffer = new byte[512 * 1024];
                                long bytesRemaining = rangeLength;
                                int bytesRead;
                                long totalBytesWritten = 0;
                                try {
                                    while ((bytesRead = inputStream.read(buffer, 0, (int) Math.min(buffer.length, bytesRemaining))) != -1 && bytesRemaining > 0) {
                                        log.info("want:{}, bytesRead:{}, writed:{}", rangeLength,  bytesRead, totalBytesWritten);
                                        response.getOutputStream().write(buffer, 0, bytesRead);
                                        bytesRemaining -= bytesRead;
                                        totalBytesWritten += bytesRead;
                                        response.getOutputStream().flush();
//                                        response.flushBuffer();
                                    }
                                    response.flushBuffer();  // 确保所有数据都被发送
                                }
                                catch (Exception e) {
                                    // 其他IO异常，记录日志并重新抛出
                                    log.warn("Error while reading/writing video stream: " + e.getMessage());
                                    throw e;
                                }


                            }

                        }




                    } catch (IllegalArgumentException var8) {
                        response.setHeader("Content-Range", "bytes */" + resource.contentLength());
                        response.sendError(416);
                    }
                }

            }
        }
    }

}
