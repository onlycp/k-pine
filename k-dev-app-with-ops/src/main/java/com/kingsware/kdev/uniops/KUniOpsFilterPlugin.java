package com.kingsware.kdev.uniops;//package com.kingsware.kdev.uniops;
//
//import com.kingsware.kdev.core.auth.AppAuthProperties;
//import com.kingsware.kdev.core.auth.BaseUserInfo;
//import com.kingsware.kdev.core.auth.TokenUtil;
//import com.kingsware.kdev.core.config.MyHttpServletRequestWrapper;
//import com.kingsware.kdev.core.context.KClientContext;
//import com.kingsware.kdev.core.context.SpringContext;
//import com.kingsware.kdev.core.plugins.FilterPlugin;
//import com.kingsware.kdev.core.util.HttpUtil;
//import com.kingsware.kdev.core.util.JsonUtil;
//import com.kingsware.kdev.core.util.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.annotation.Resource;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * k-uniops插件
// */
//@Component
//@Slf4j
//public class KUniOpsFilterPlugin implements FilterPlugin {
//
//    @Resource
//    private AppAuthProperties appAuthProperties;
//
//
//
//
//
//    @Override
//    public void doFilter(MyHttpServletRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) {
//        // 获取uniops令牌
//
//        // 加入请求头
//        requestWrapper.addHeader("Authorization","Bearer " + TokenStore.getInstance().get(token));
//    }
//}
