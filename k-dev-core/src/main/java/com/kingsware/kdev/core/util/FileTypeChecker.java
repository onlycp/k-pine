package com.kingsware.kdev.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileTypeChecker {

    private static final int FILE_HEAD_READ_LENGTH = 512;

    private static final Set<String> audioFormats = new HashSet<>(Arrays.asList(
            "mp3", "wav", "flac", "ogg", "aac", "wma"
    ));

    private static final Set<String> videoFormats = new HashSet<>(Arrays.asList(
            "mp4", "avi", "mov", "mkv", "flv", "wmv", "mpeg", "3gp"
    ));

    private static final Set<String> textFormats = new HashSet<>(Arrays.asList(
            "txt", "csv", "json", "xml", "html", "svg", "pine", "license"
    ));

    private static final Map<String, List<byte[]>> MAGIC_HEADERS = new HashMap<>();

    static {
        MAGIC_HEADERS.put("png", Collections.singletonList(new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}));
        MAGIC_HEADERS.put("jpg", Collections.singletonList(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}));
        MAGIC_HEADERS.put("jpeg", Collections.singletonList(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}));
        MAGIC_HEADERS.put("gif", Arrays.asList("GIF87a".getBytes(StandardCharsets.US_ASCII), "GIF89a".getBytes(StandardCharsets.US_ASCII)));
        MAGIC_HEADERS.put("pdf", Collections.singletonList("%PDF-".getBytes(StandardCharsets.US_ASCII)));
        MAGIC_HEADERS.put("zip", Arrays.asList(
                new byte[]{0x50, 0x4B, 0x03, 0x04},
                new byte[]{0x50, 0x4B, 0x05, 0x06},
                new byte[]{0x50, 0x4B, 0x07, 0x08}
        ));
        MAGIC_HEADERS.put("docx", MAGIC_HEADERS.get("zip"));
        MAGIC_HEADERS.put("xlsx", MAGIC_HEADERS.get("zip"));
        MAGIC_HEADERS.put("pptx", MAGIC_HEADERS.get("zip"));
        MAGIC_HEADERS.put("gz", Collections.singletonList(new byte[]{0x1F, (byte) 0x8B, 0x08}));
        MAGIC_HEADERS.put("rar", Arrays.asList(
                new byte[]{0x52, 0x61, 0x72, 0x21, 0x1A, 0x07, 0x00},
                new byte[]{0x52, 0x61, 0x72, 0x21, 0x1A, 0x07, 0x01, 0x00}
        ));
        MAGIC_HEADERS.put("bmp", Collections.singletonList(new byte[]{0x42, 0x4D}));
        MAGIC_HEADERS.put("doc", Collections.singletonList(new byte[]{(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0, (byte) 0xA1, (byte) 0xB1, 0x1A, (byte) 0xE1}));
        MAGIC_HEADERS.put("xls", MAGIC_HEADERS.get("doc"));
        MAGIC_HEADERS.put("ppt", MAGIC_HEADERS.get("doc"));
        MAGIC_HEADERS.put("mp3", Arrays.asList(
                "ID3".getBytes(StandardCharsets.US_ASCII),
                new byte[]{(byte) 0xFF, (byte) 0xFB},
                new byte[]{(byte) 0xFF, (byte) 0xF3},
                new byte[]{(byte) 0xFF, (byte) 0xF2}
        ));
        MAGIC_HEADERS.put("wav", Collections.singletonList("RIFF".getBytes(StandardCharsets.US_ASCII)));
        MAGIC_HEADERS.put("avi", Collections.singletonList("RIFF".getBytes(StandardCharsets.US_ASCII)));
        MAGIC_HEADERS.put("mp4", Collections.singletonList("ftyp".getBytes(StandardCharsets.US_ASCII)));
        MAGIC_HEADERS.put("mov", Collections.singletonList("ftyp".getBytes(StandardCharsets.US_ASCII)));
    }

    /**
     * 判断给定的文件路径是否属于音频文件
     * @param filePath 文件路径
     * @return 如果是音频文件则返回 true，否则返回 false
     */
    public static boolean isAudioFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        return audioFormats.contains(fileExtension.toLowerCase());
    }

    /**
     * 判断给定的文件路径是否属于视频文件
     * @param filePath 文件路径
     * @return 如果是视频文件则返回 true，否则返回 false
     */
    public static boolean isVideoFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        return videoFormats.contains(fileExtension.toLowerCase());
    }

    public static boolean checkFileContent(String fileName, InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        String ext = getFileExtension(fileName).toLowerCase();
        if (ext.isEmpty()) {
            return true;
        }
        byte[] header = readHeader(inputStream);
        if (header.length == 0) {
            return true;
        }
        if (textFormats.contains(ext)) {
            return isLikelyTextContent(header);
        }
        if ("wav".equals(ext)) {
            return matchesAt(header, "RIFF".getBytes(StandardCharsets.US_ASCII), 0)
                    && matchesAt(header, "WAVE".getBytes(StandardCharsets.US_ASCII), 8);
        }
        if ("avi".equals(ext)) {
            return matchesAt(header, "RIFF".getBytes(StandardCharsets.US_ASCII), 0)
                    && matchesAt(header, "AVI ".getBytes(StandardCharsets.US_ASCII), 8);
        }
        if ("mp4".equals(ext) || "mov".equals(ext)) {
            return matchesAt(header, "ftyp".getBytes(StandardCharsets.US_ASCII), 4);
        }
        List<byte[]> magicList = MAGIC_HEADERS.get(ext);
        if (magicList == null || magicList.isEmpty()) {
            // 未内置签名类型暂不阻断，避免影响既有业务文件上传
            return true;
        }
        for (byte[] magic : magicList) {
            if (startsWith(header, magic)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] readHeader(InputStream inputStream) {
        try {
            if (!inputStream.markSupported()) {
                return new byte[0];
            }
            inputStream.mark(FILE_HEAD_READ_LENGTH);
            byte[] head = new byte[FILE_HEAD_READ_LENGTH];
            int len = inputStream.read(head);
            inputStream.reset();
            if (len <= 0) {
                return new byte[0];
            }
            return Arrays.copyOf(head, len);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    private static boolean startsWith(byte[] data, byte[] prefix) {
        return matchesAt(data, prefix, 0);
    }

    private static boolean matchesAt(byte[] data, byte[] pattern, int offset) {
        if (data == null || pattern == null || offset < 0 || data.length < pattern.length + offset) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (data[offset + i] != pattern[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLikelyTextContent(byte[] data) {
        int printable = 0;
        for (byte b : data) {
            int v = b & 0xFF;
            if (v == 0) {
                return false;
            }
            if (v == 9 || v == 10 || v == 13 || (v >= 32 && v < 127) || v >= 160) {
                printable++;
            }
        }
        return printable * 1.0 / data.length >= 0.85;
    }

    /**
     * 获取文件路径中的文件扩展名
     * @param filePath 文件路径
     * @return 文件扩展名，如果无法获取则返回空字符串
     */
    private static String getFileExtension(String filePath) {
        if (filePath == null) {
            return "";
        }
        String fileName;
        try {
            Path path = Paths.get(filePath);
            Path name = path.getFileName();
            fileName = name == null ? "" : name.toString();
        } catch (Exception ignored) {
            fileName = new File(filePath).getName();
        }
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
