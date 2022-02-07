package com.kingsware.kdev.core.kflow.handler;

import java.util.List;
import java.util.Map;

/**
 * //todo 描述当前类是干什么用的.
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2022/1/18 3:17 下午
 */
public class KObjectHandler implements KFlowResultHandler {

    @Override
    public Object execute(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
