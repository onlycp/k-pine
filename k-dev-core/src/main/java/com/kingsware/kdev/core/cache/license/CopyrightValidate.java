package com.kingsware.kdev.core.cache.license;

import com.kingsware.kdev.core.context.SpringContext;
import com.kingsware.kdev.core.i18n.I18n;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author chen peng
 * @version 1.0.0
 * @date 2023/8/31 17:32
 */
@Component
public class CopyrightValidate implements LicenseValidate{
    @Override
    public String key() {
        return "copyright";
    }

    @Override
    public String errorMessage() {
        return I18n.t("CopyrightValidate.message", new String(Base64.getDecoder().decode("6K645Y+v6K+B5Y+R6KGM5pa55LiN5ZCI5rOV"), StandardCharsets.UTF_8)) ;
    }

    @Override
    public boolean execute(String data) {

        String copyright = SpringContext.getProperties("app.copyright", "VGVhbUBQaW5l");
        String deCopyright = new String(Base64.getDecoder().decode(copyright), StandardCharsets.UTF_8);
        return deCopyright.equalsIgnoreCase(data);
    }
}
