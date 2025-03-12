package com.kingsware.kdev.core.util;

public class CaesarCipher {

    // 加密函数
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                // 处理字母
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                char encryptedChar = (char) ((character - base + shift) % 26 + base);
                result.append(encryptedChar);
            } else if (Character.isDigit(character)) {
                // 处理数字
                char base = '0';
                char encryptedChar = (char) ((character - base + shift) % 10 + base);
                result.append(encryptedChar);
            } else {
                // 非字母和数字的字符直接保留
                result.append(character);
            }
        }

        return result.toString();
    }

    // 解密函数
    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift); // 解密是加密的逆过程
    }

    public static void main(String[] args) {
        String originalText = "Hello, World==!";
        int shift = 3;

        String encryptedText = encrypt(originalText, shift);
        System.out.println("Encrypted: " + encryptedText);

        String decryptedText = decrypt(encryptedText, shift);
        System.out.println("Decrypted: " + decryptedText);
    }
}
