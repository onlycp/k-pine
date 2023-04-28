package com.kingsware.kdev.sys.manager;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
        Object attrFile = request.getAttribute(ATTR_FILE);
        Resource resource = null;
        if (attrFile instanceof File) {
            File file = (File) attrFile;
            resource = new FileSystemResource(file);
            file.delete();
        } else {
            String path = (String) attrFile;
            Path filePath = Paths.get(path);
            resource = new FileSystemResource(filePath);
        }
        return resource;
    }
}
