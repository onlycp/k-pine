package com.kingsware.kdev.core.util;

import java.util.regex.Pattern;

public class Base64Utils extends org.springframework.util.Base64Utils {

    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }
}
