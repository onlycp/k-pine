package com.kingsware.kdev.core.util;

import java.io.File;
import java.io.IOException;

/**
 * 路径安全工具
 */
public final class PathSecurityUtils {

    private PathSecurityUtils() {
    }

    public static File canonicalFile(String configuredPath, String key) throws IOException {
        if (configuredPath == null || configuredPath.indexOf('\0') >= 0) {
            throw new IOException("invalid path config: " + key);
        }
        return new File(configuredPath).getCanonicalFile();
    }

    public static File canonicalFile(File file, String key) throws IOException {
        if (file == null) {
            throw new IOException("invalid path config: " + key);
        }
        return canonicalFile(file.getPath(), key);
    }

    public static boolean isInsideRoot(File candidate, File rootDir) {
        if (candidate == null || rootDir == null) {
            return false;
        }
        return candidate.toPath().startsWith(rootDir.toPath());
    }

    public static File resolveUnderRoot(File rootDir, String relativePath, String key) throws IOException {
        File canonicalRoot = canonicalFile(rootDir, key + ".root");
        if (relativePath == null || relativePath.indexOf('\0') >= 0) {
            throw new IOException("invalid path: " + key);
        }
        String normalized = relativePath.trim().replace('\\', '/');
        while (normalized.startsWith("/")) {
            normalized = normalized.substring(1);
        }
        File candidate = new File(canonicalRoot, normalized).getCanonicalFile();
        if (!isInsideRoot(candidate, canonicalRoot)) {
            throw new IOException("path outside root: " + key);
        }
        return candidate;
    }
}
