package com.kingsware.kdev;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * @author chenp
 * @date 2023/3/6
 */
@Component
@Slf4j
public class ModuleLoaded {

    @PostConstruct
    public void info() {
        log.info("资源模块加载");
    }
}
