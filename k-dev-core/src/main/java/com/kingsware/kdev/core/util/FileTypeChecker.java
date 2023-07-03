package com.kingsware.kdev.core.util;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileTypeChecker {

    private static final Set<String> audioFormats = new HashSet<>(Arrays.asList(
            "mp3", "wav", "flac", "ogg", "aac", "wma"
    ));

    private static final Set<String> videoFormats = new HashSet<>(Arrays.asList(
            "mp4", "avi", "mov", "mkv", "flv", "wmv", "mpeg", "3gp"
    ));

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

    /**
     * 获取文件路径中的文件扩展名
     * @param filePath 文件路径
     * @return 文件扩展名，如果无法获取则返回空字符串
     */
    private static String getFileExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}

