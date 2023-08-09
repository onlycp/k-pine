package com.kingsware.kdev.core.util;

public class LoginCryptoUtils {

    private static String privateKey = "009308ea577dad81c8ef52b5b98c9ed80d0aedef8256914cd31708b6a470d313c3";
    private static String publicKey = "0492073b8f6b9efed4fc75ce6f9dbf0359c451e3d128fff000d5414d6cf50304c71f326854f024b00584abd999a6c0fabb10c95ee21658f5b186678b7c9614f97f";

    public static String loginDecrypt(String data) throws Exception {
        return SM2Utils.decryptData(data, privateKey, publicKey);
    }

}
