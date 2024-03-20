package com.kingsware.kdev.sys.ret;

import com.kingsware.kdev.sys.enums.HealthEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenp
 * @date 2024/3/15
 */
@Data
public class ComponentHealthRet {
    /**
     * 健康状态
     */
    private HealthEnum status;
    /**
     * 健康详情
     */
    private Map<String, Object> details = new HashMap<>();
}
