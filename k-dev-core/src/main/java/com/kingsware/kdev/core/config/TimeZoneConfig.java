package com.kingsware.kdev.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 时区设定
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/16 3:34 下午
 */
@Component
@Order(0)
public class TimeZoneConfig {

    @Value("${app.time-zone:Asia/Shanghai}")
    private String timeZone;

    @PostConstruct
    public void execute(){
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

}
