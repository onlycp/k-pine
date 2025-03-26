package com.kingsware.kdev.core.config;

import com.kingsware.kdev.core.bean.BaseRet;
import com.kingsware.kdev.core.bean.BaseSimpleRet;
import com.kingsware.kdev.core.bean.PageDataRet;
import com.kingsware.kdev.core.util.ServletUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    // 判断是否支持对该方法的响应进行处理
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 这里可以根据条件判断是否需要处理
        return true; // 对所有响应进行处理
    }

    // 在响应体写入到客户端之前进行处理
    @Override
    public Object beforeBodyWrite(Object body , MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
//        // 在这里对响应体进行处理
//        if (body instanceof String) {
//            // 如果响应体是字符串类型，可以进行处理
//            return "Processed: " + body;
//        } else if (body instanceof BaseRet) {
//            BaseRet baseRet = (BaseRet) body;
//            // 如果getData是对象、Collection、数组等类型，可以进行处理
//            if (baseRet.getData() != null) {
//                if (baseRet.getData() instanceof Map || baseRet.getData() instanceof Iterable || baseRet.getData().getClass().isArray() || baseRet.getData() instanceof BaseSimpleRet || baseRet.getData() instanceof PageDataRet) {
//                    Object data = ((BaseRet) body).getData();
//                    baseRet.setData(ServletUtil.encryptObject(data));
//                }
//            }
//            return baseRet;
//        }
        // 返回处理后的响应体
        return body;
    }
}