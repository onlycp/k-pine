package com.kingsware.kdev.core.kflow.handler;

import com.kingsware.kdev.core.util.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理器
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/3/3 4:11 下午
 */
@Slf4j
public class KResultHandlers {

    private static KResultHandlers instance;

    /** 处理器map **/
    private Map<String, KFlowResultHandler> handlerMap = new HashMap<>();

    public static KResultHandlers getInstance() {
        if (instance == null) {
            instance = new KResultHandlers();
        }
        return instance;
    }

    private KResultHandlers() {
        registerHandlers();;
    }

    /**
     * 注册处理器
     */
    private void registerHandlers() {
        String scanPackage = this.getClass().getPackage().getName();
        List<Class<?>> classList =  ClassUtils.getClassesByParentClass(scanPackage, KFlowResultHandler.class);
        for (Class<?> tClass: classList) {
            // 生成实例
            try {
                KFlowResultHandler handler = (KFlowResultHandler) tClass.newInstance();
                handlerMap.put(handler.name(), handler);
            } catch (Exception e) {
                log.error("扫描处理类失败:{}" , e.getMessage());
            }
        }
    }

    /**
     * 获取解析器
     * @param name  名称
     * @return      解析器
     */
    public KFlowResultHandler getHandler(String name) {
        return handlerMap.get(name);
    }

}
