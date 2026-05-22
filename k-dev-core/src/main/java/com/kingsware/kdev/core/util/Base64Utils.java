package com.kingsware.kdev.core.util;

import java.util.Base64;
import java.util.regex.Pattern;

public final class Base64Utils {

    private Base64Utils() {
    }

    public static byte[] encode(byte[] src) {
        return Base64.getEncoder().encode(src);
    }

    public static String encodeToString(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public static byte[] decode(byte[] src) {
        return Base64.getDecoder().decode(src);
    }

    public static byte[] decodeFromString(String src) {
        return Base64.getDecoder().decode(src);
    }

    public static boolean isBase64(String str) {
        String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
        return Pattern.matches(base64Pattern, str);
    }
}
