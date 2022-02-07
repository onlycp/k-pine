package com.kingsware.kdev.core.kflow.handler;

import com.kingsware.kdev.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理器工厂
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/19 10:33 上午
 */
@Slf4j
public class KFlowResultHandlerFactory {

    /**
     * 获取处理器
     * @param className 类名
     * @return  返回处理结果
     */
    public static KFlowResultHandler getHandler(String className) {

        try {
            Class tClass = Class.forName(className);
            if (tClass.isAssignableFrom(KFlowResultHandler.class)) {
                throw BusinessException.serviceThrow(String.format("流程结果处理器不合法，应继承:%s, 当前类名: %s", KFlowResultHandler.class.getSimpleName(), className));
            }
            KFlowResultHandler handler = (KFlowResultHandler)tClass.newInstance();
            return handler;

        }
        catch (Exception e) {
            log.error("error", e);
            throw BusinessException.serviceThrow(String.format("获取结果处理器失败，当前类名: %s", className));
        }

    }
}
