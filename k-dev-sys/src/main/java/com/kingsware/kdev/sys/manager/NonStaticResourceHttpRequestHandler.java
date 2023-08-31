package com.kingsware.kdev.sys.manager;

import com.kingsware.kdev.core.util.FileTypeChecker;
import com.kingsware.kdev.core.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    private static final Log logger = LogFactory.getLog(NonStaticResourceHttpRequestHandler.class);
    public final static String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) {
        Object attrFile = request.getAttribute(ATTR_FILE);
        Resource resource = null;
        if (attrFile instanceof File) {
            File file = (File) attrFile;
            try {
                byte[] fileBytes = FileUtils.readFileToByteArray(file);
                resource = new ByteArrayResource(fileBytes);
                request.setAttribute("fileName", file.getName());
                file.delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
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
            logger.debug("Resource not found");
            response.sendError(404);
        } else if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setHeader("Allow", this.getAllowHeader());
        } else {
            this.checkRequest(request);
            if (resource.getFilename() != null && this.isUseLastModified() && (new ServletWebRequest(request, response)).checkNotModified(resource.lastModified())) {
                logger.trace("Resource not modified");
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
                        this.getResourceRegionHttpMessageConverter().write(HttpRange.toResourceRegions(httpRanges, resource), mediaType, outputMessage);
                    } catch (IllegalArgumentException var8) {
                        response.setHeader("Content-Range", "bytes */" + resource.contentLength());
                        response.sendError(416);
                    }
                }

            }
        }
    }

}
