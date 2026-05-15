package com.kingsware.kdev.sys.service.impl;

import com.kingsware.kdev.core.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SysFileServiceImplTest {

    @Test
    void compressStaticZipRejectsPathsOutsideResDirectory() throws Exception {
        SysFileServiceImpl service = new SysFileServiceImpl(null);
        Path outsideFile = Files.createTempFile("outside-static-", ".txt");
        Files.write(outsideFile, "payload".getBytes(StandardCharsets.UTF_8));

        try {
            assertThrows(BusinessException.class, () -> service.compressStaticZip(outsideFile.toString(), null));
        } finally {
            Files.deleteIfExists(outsideFile);
        }
    }

    @Test
    void compressStaticZipStillSupportsValidResFiles() throws Exception {
        SysFileServiceImpl service = new SysFileServiceImpl(null);
        Path staticDir = Paths.get("res", "test-static");
        Files.createDirectories(staticDir);
        Path staticFile = staticDir.resolve("sample.txt");
        Files.write(staticFile, "payload".getBytes(StandardCharsets.UTF_8));

        String zipPath = null;
        try {
            zipPath = service.compressStaticZip(staticFile.toString().replace('\\', '/'), null);
            assertTrue(Files.exists(Paths.get(zipPath)));
        } finally {
            if (zipPath != null) {
                Files.deleteIfExists(Paths.get(zipPath));
            }
            deleteRecursively(Paths.get("upload", "package", "pinezip"));
            deleteRecursively(Paths.get("res", "test-static"));
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }
        Files.walk(path)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        throw new RuntimeException("cleanup failed: " + UUID.randomUUID(), e);
                    }
                });
    }
}
