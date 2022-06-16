package com.kingsware.kdev.sys.manager;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author andyzheng
 * @version 1.0.0
 * @description: TODO
 * @date 2022/6/16 18:14
 */
@Component
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    public final static String ATTR_FILE = "NON-STATIC-FILE";

    @Override
    protected Resource getResource(HttpServletRequest request) {
        String path = (String) request.getAttribute(ATTR_FILE);
        Path filePath = Paths.get(path);
        return new FileSystemResource(filePath);
    }
}
