//package com.kingsware.kdev.core.proxy;
//
//import org.springframework.boot.context.properties.bind.BindResult;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.util.Properties;
//
///**
// * @author chenp
// * @date 2023/11/24
// */
//@Configuration
//public class SolrProxyServletConfiguration implements EnvironmentAware {
//
//    private BindResult bindResult;
//
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() {
//        Properties properties= (Properties) bindResult.get();
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), properties.getProperty("servlet_url"));
//        servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, properties.getProperty("target_url"));
//        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, properties.getProperty("logging_enabled", "false"));
//        return servletRegistrationBean;
//    }
//
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        Iterable sources = ConfigurationPropertySources.get(environment);
//        Binder binder = new Binder(sources);
//        BindResult bindResult = binder.bind("proxy.solr", Properties.class);
//        this.bindResult = bindResult;
//    }
//}
