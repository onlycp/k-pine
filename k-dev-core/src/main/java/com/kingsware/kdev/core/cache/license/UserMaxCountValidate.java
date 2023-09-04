package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.orm.DB;
import com.kingsware.kdev.core.util.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/8/31 17:32
 */
@Component
public class UserMaxCountValidate implements LicenseValidate{
    @Override
    public String key() {
        return "user-max";
    }

    @Override
    public String errorMessage() {
        return "用户数超过限制";
    }

    @Override
    public boolean execute(String data) {
        if (StringUtils.isEmpty(data)) {
            return true;
        }
        long count = DB.findCount("select count(1) from sys_user");
        return  (Long.parseLong(data) > count);
    }
}
