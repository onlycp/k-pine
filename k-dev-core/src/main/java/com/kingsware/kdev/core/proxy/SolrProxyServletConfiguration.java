//package com.kingsware.kdev.core.proxy;
//
//import com.kingsware.kdev.core.context.SpringContext;
//import org.springframework.boot.context.properties.bind.BindResult;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.Resource;
//import java.util.Properties;
//
///**
// * @author chenp
// * @date 2023/11/24
// */
//@Component
//public class SolrProxyServletConfiguration {
//
//    @Resource
//    private ProxyProperties proxyProperties;
//
//    @PostConstruct
//    public void servletRegistrationBean() {
//        if(proxyProperties == null || proxyProperties.getProxies() == null || proxyProperties.getProxies().size() == 0) {
//            return;
//        }
//        for (ProxyConfig proxyConfig : proxyProperties.getProxies()) {
//            ServletRegistrationBean<?> servletRegistrationBean = new ServletRegistrationBean<>(new ProxyServlet(), proxyConfig.getLocation());
//            servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, proxyConfig.getProxyPass());
//            servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "true");
//            SpringContext.registerBean("servletRegistrationBean", servletRegistrationBean);
//        }
//    }
//
//}
