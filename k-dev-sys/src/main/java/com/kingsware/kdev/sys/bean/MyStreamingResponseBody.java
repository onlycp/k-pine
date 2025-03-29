package com.kingsware.kdev.sys.bean;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;

public class MyStreamingResponseBody implements StreamingResponseBody {
    @Override
    public void writeTo(OutputStream outputStream) throws IOException {

    }
}
